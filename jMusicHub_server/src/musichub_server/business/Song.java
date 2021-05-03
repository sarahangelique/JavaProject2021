package musichub_server.business;

import org.w3c.dom.*;


public class Song extends AudioElement {
	private Genre genre;
	/**
	 * constructor to create a song that has an UUID
	 * @param title
	 * @param artist
	 * @param length
	 * @param uid
	 * @param content
	 * @param genre
	 */
	public Song (String title, String artist, int length, String uid, String content, String genre) {
		super (title, artist, length, uid, content);
		this.setGenre(genre);
	}

	/**
	 * constructor to create a song that doesn't have an UUID
	 * @param title
	 * @param artist
	 * @param length
	 * @param content
	 * @param genre
	 */
	public Song (String title, String artist, int length, String content, String genre) {
		super (title, artist, length, content);
		this.setGenre(genre);
	}

	/**
	 * reads the xml file containing the genres
	 * @param xmlElement
	 * @throws Exception
	 */
	public Song (Element xmlElement) throws Exception {
		super(xmlElement);
		try {
			this.setGenre(xmlElement.getElementsByTagName("genre").item(0).getTextContent());
		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * the different genres
	 * @param genre
	 */
	public void setGenre (String genre) {	
		switch (genre.toLowerCase()) {
			case "jazz":
			default:
				this.genre = Genre.JAZZ;
				break;
			case "classic":
				this.genre = Genre.CLASSIC;
				break;
			case "hiphop":
				this.genre = Genre.HIPHOP;
				break;
			case "rock":
				this.genre = Genre.ROCK;
				break;
			case "pop":
				this.genre = Genre.POP;
				break;
			case "rap":
				this.genre = Genre.RAP;
				break;				
		}
	}
	/**
	 * returns the genre of the song
	 * @return
	 */
	public String getGenre () {
		return genre.getGenre();
	}

	/**
	 *displays the genre of the song to the user
	 * @return
	 */
	public String toString() {
		return super.toString() + ", Genre = " + getGenre() + "\n";
	}

	/**
	 * gets the content of the XML file that manages songs
	 * @param document
	 * @param parentElement
	 */
	public void createXMLElement(Document document, Element parentElement) {
		// song element
        Element song = document.createElement("song");

		super.createXMLElement(document, song);
		
		Element genreElement = document.createElement("genre");
        genreElement.appendChild(document.createTextNode(genre.getGenre()));
        song.appendChild(genreElement);
		
		parentElement.appendChild(song);
		return;
	}
}