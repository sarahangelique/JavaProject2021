package musichub_server.util;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;

import org.w3c.dom.*;
import java.io.IOException;
import java.io.File;


public class XMLHandler {
	TransformerFactory transformerFactory;
	Transformer transformer;
	DocumentBuilderFactory documentFactory;
	DocumentBuilder documentBuilder;

	public XMLHandler() {
		try {
			transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
			documentFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentFactory.newDocumentBuilder();
		} catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
	}
	
	public void createXMLFile(Document document, String filePath) {
		try {
		// create the xml file
        //transform the DOM Object to an XML File
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(filePath));

		// If you use
		// StreamResult result = new StreamResult(System.out);
		// the output will be pushed to the standard output ...
		// You can use that for debugging 

		transformer.transform(domSource, streamResult);
		
		} catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
	}
	
	public Document createXMLDocument() {
		return documentBuilder.newDocument();
	}		
	
	public NodeList parseXMLFile (String filePath) {
		NodeList elementNodes = null;
		try {
			Document document= documentBuilder.parse(new File(filePath));
			Element root = document.getDocumentElement();
			
			elementNodes = root.getChildNodes();	
		}
		catch (SAXException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return elementNodes;
	}

	public String searchSongInXMLFile (String songToPlay, String XML_INPUT_FILE) {
		NodeList nodes = this.parseXMLFile(XML_INPUT_FILE);
		if (nodes == null) return "null";

		System.out.println("The song I want to listen is: " + songToPlay);

		for (int i = 0; i<nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element currentElement = (Element) nodes.item(i);
				if (currentElement.getNodeName().equals("song")) {
					if(currentElement.getElementsByTagName("title").item(0).getTextContent().equals(songToPlay)){
						try {
							String content = currentElement.getElementsByTagName("content").item(0).getTextContent();
							System.out.println(content);
							return content;
						} catch (Exception ex) {
							System.out.println("Something is wrong with the XML song element");
						}
					}
				}
			}
		}
		return "Not such song in the data base!";
	}

}