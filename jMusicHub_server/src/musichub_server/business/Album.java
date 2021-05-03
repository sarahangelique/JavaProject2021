package musichub_server.business;

import java.util.*;
import org.w3c.dom.*;
import java.text.*;

/**
 *
 */
public class Album {
	private String title;
	private String artist;
	private int lengthInSeconds;
	private UUID uuid;
	private Date date;
	private ArrayList<UUID> songsUIDs;

	/**
	 * Constructor to create an Album with an UUID.
	 *
	 * @param title  The album's title.
	 * @param artist  The album's artist.
	 * @param lengthInSeconds  Length in seconds of the album.
	 * @param id  ID of the album.
	 * @param date  Released date of the album.
	 * @param songsUIDs  ID of the songs within the album.
	 */
	public Album (String title, String artist, int lengthInSeconds, String id, String date, ArrayList<UUID> songsUIDs) {
		this.title = title;
		this.artist = artist;
		this.lengthInSeconds = lengthInSeconds;
		this.uuid = UUID.fromString(id);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			this.date = sdf.parse(date);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		this.songsUIDs = songsUIDs;
	}

	/**
	 * Second constructor to create an album that does not have an UUID.
	 * @param title  The album's title.
	 * @param artist  The album's artist.
	 * @param lengthInSeconds  Length in seconds of the album.
	 * @param date  Released date of the album.
	 */
	public Album (String title, String artist, int lengthInSeconds, String date) {
		this.title = title;
		this.artist = artist;
		this.lengthInSeconds = lengthInSeconds;
		this.uuid = UUID.randomUUID();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			this.date = sdf.parse(date);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		this.songsUIDs = new ArrayList<UUID>();
	}

	/**
	 * Gets all the tags in the XML file related to the album
	 * If the tag UUID is empty, it creates a new one
	 *
	 * @param xmlElement  It allows to to go through each tag and retrieve its content.
	 * @throws Exception  Exception happens if the album or a song does not have an UUID
	 */
	public Album (Element xmlElement) throws Exception {
		try {
			this.title = xmlElement.getElementsByTagName("title").item(0).getTextContent();
			this.lengthInSeconds = Integer.parseInt(xmlElement.getElementsByTagName("lengthInSeconds").item(0).getTextContent());
			String uuid = null;
			try {
				uuid = xmlElement.getElementsByTagName("UUID").item(0).getTextContent();
			}
			catch (Exception ex) {
				System.out.println ("Empty album UUID, will create a new one");
			}
			if ((uuid == null)  || (uuid.isEmpty()))
				this.uuid = UUID.randomUUID();
			else this.uuid = UUID.fromString(uuid);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			this.date = sdf.parse(xmlElement.getElementsByTagName("date").item(0).getTextContent()); 
			//parse list of songs:
			Node songsElement = xmlElement.getElementsByTagName("songs").item(0);
			NodeList songUUIDNodes = songsElement.getChildNodes();
			if (songUUIDNodes == null) return;
		
			this.songsUIDs = new ArrayList<UUID>();
			
			for (int i = 0; i < songUUIDNodes.getLength(); i++) {
				if (songUUIDNodes.item(i).getNodeType() == Node.ELEMENT_NODE)   {
					Element songElement = (Element) songUUIDNodes.item(i);
					if (songElement.getNodeName().equals("UUID")) 	{
						try {
							this.addSong(UUID.fromString(songElement.getTextContent()));
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				} 
			}
		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * Adds a song to an album.
	 *
	 * @param song  UUID of a song of the album
	 */
	public void addSong (UUID song)
	{
		songsUIDs.add(song);
	}

	/**
	 * Returns the UUID of all songs of an album.
	 *
	 * @return
	 */
	public List<UUID> getSongs() {
		return songsUIDs;
	}

	/**
	 * Returns a list of ID of songs in a random order (shuffle functionality).
	 *
	 * @return
	 */
	public ArrayList<UUID> getSongsRandomly() {
		ArrayList<UUID> shuffledSongs = songsUIDs;
		Collections.shuffle(shuffledSongs);
		return shuffledSongs;
	}

	/**
	 * Returns the title of a song.
	 *
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the date of an album
	 *
	 * @return
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Creates an albums in a XML file
	 *
	 * @param document  Represents the entire XML file that manages albums.
	 * @param parentElement  Represents the elements within the XML files.
	 */
	public void createXMLElement(Document document, Element parentElement) {
		Element albumElement = document.createElement("album");
		parentElement.appendChild(albumElement);
		
		Element nameElement = document.createElement("title");
        nameElement.appendChild(document.createTextNode(title));
        albumElement.appendChild(nameElement);
		
		Element artistElement = document.createElement("artist");
        artistElement.appendChild(document.createTextNode(artist));
        albumElement.appendChild(artistElement);
		
		Element lengthElement = document.createElement("lengthInSeconds");
        lengthElement.appendChild(document.createTextNode(Integer.valueOf(lengthInSeconds).toString()));
        albumElement.appendChild(lengthElement);
		
		Element UUIDElement = document.createElement("UUID");
        UUIDElement.appendChild(document.createTextNode(uuid.toString()));
        albumElement.appendChild(UUIDElement);
		
		Element dateElement = document.createElement("date");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateElement.appendChild(document.createTextNode(sdf.format(date)));
        albumElement.appendChild(dateElement);
		
		Element songsElement = document.createElement("songs");
		for (Iterator<UUID> songUUIDIter = this.songsUIDs.listIterator(); songUUIDIter.hasNext();) {
			UUID currentUUID = songUUIDIter.next();
			
			Element songUUIDElement = document.createElement("UUID");
			songUUIDElement.appendChild(document.createTextNode(currentUUID.toString()));
			songsElement.appendChild(songUUIDElement);
		}
		albumElement.appendChild(songsElement);

	}
}