package musichub_server.business;

import java.io.*;
import org.w3c.dom.*;


public class AudioBook extends AudioElement {
	private Language language;
	private Category category;

	/**
	 * Constructor to create an audiobook with an uid.
	 *
	 * @param title Audiobook's title.
	 * @param artist Audiobook's artist.
	 * @param lengthInSeconds  Lenght in seconds of the audiobook
	 * @param uid  UUID of the audiobook.
	 * @param content  Name of the .wav file.
	 * @param language  Language of the audiobook.
	 * @param category  Category of the audiobook.
	 */
	public AudioBook (String title, String artist, int lengthInSeconds, String uid, String content, String language, String category) {
		super (title, artist, lengthInSeconds, uid, content);
		this.setLanguage(language);
		this.setCategory(category);
	}

	/**
	 * Constructor to create an audiobook that does not have an uid.
	 *
	 * @param title Audiobook's title.
	 * @param artist Audiobook's artist.
	 * @param lengthInSeconds  Lenght in seconds of the audiobook
	 * @param content  Name of the .wav file.
	 * @param language  Language of the audiobook.
	 * @param category  Category of the audiobook.
	 */
	public AudioBook (String title, String artist, int lengthInSeconds, String content, String language, String category) {
		super (title, artist, lengthInSeconds, content);
		this.setLanguage(language);
		this.setCategory(category);
	}

	/**
	 * Gets the content of the XML file that manages audiobooks.
	 *
	 * @param xmlElement  It allows to to go through each tag and retrieve its content.
	 * @throws Exception  Exception happens if the album or a song does not have an UUID.
	 */
	public AudioBook (Element xmlElement) throws Exception {
		super(xmlElement);
		try {
			this.setLanguage(xmlElement.getElementsByTagName("language").item(0).getTextContent());
			this.setCategory(xmlElement.getElementsByTagName("category").item(0).getTextContent());
		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * Returns language of the audiobook.
	 *
	 * @return
	 */
	public Language getLanguage() {
		return this.language;
	}

	/**
	 * Returns the category of the audiobook.
	 *
	 * @return
	 */
	public Category getCategory() {
		return this.category;
	}

	/**
	 * Let the user defines the language of the audiobook.
	 *
	 * @param language
	 */
	public void setLanguage (String language) {	
		switch (language.toLowerCase()) {
			case "english":
			default:
				this.language = Language.ENGLISH;
				break;
			case "french":
				this.language = Language.FRENCH;
				break;
			case "german":
				this.language = Language.GERMAN;
				break;
			case "spanish":
				this.language = Language.SPANISH;
				break;
			case "italian":
				this.language = Language.ITALIAN;
				break;
				
		}
	}

	/**
	 * Let the user define the category of the audiobook.
	 *
	 * @param category
	 */
	public void setCategory (String category) {	
		switch (category.toLowerCase()) {
			case "youth":
			default:
				this.category = Category.YOUTH;
				break;
			case "novel":
				this.category = Category.NOVEL;
				break;
			case "theater":
				this.category = Category.THEATER;
				break;
			case "documentary":
				this.category = Category.DOCUMENTARY;
				break;
			case "speech":
				this.category = Category.SPEECH;
				break;
		}
	}

	/**
	 * Converts into string to display on the screen.
	 *
	 * @return
	 */
	public String toString() {
		return super.toString() + ", Language = " + getLanguage() + ", Category = " + getCategory() + "\n";
	}

	/**
	 * Allows to create audiobook content in XML file.
	 * It will create a tag audiobook, language and category.
	 *
	 * @param document
	 * @param parentElement
	 */
	public void createXMLElement(Document document, Element parentElement) {
		// audiobook element
        Element audioBook = document.createElement("audiobook");

		super.createXMLElement(document, audioBook);
		
		Element languageElement = document.createElement("language");
        languageElement.appendChild(document.createTextNode(language.getLanguage()));
        audioBook.appendChild(languageElement);
		
		Element categoryElement = document.createElement("category");
        categoryElement.appendChild(document.createTextNode(category.getCategory()));
        audioBook.appendChild(categoryElement);
		
		parentElement.appendChild(audioBook);
		return;
	}
}