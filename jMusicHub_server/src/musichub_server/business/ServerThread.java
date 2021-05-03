package musichub_server.business;

import java.io.*;
import java.net.*;
import java.util.*;
import musichub_server.util.*;
import org.w3c.dom.*;


/**
 * This thread is responsible to handle client connection.
 */
public class ServerThread extends Thread {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private XMLHandler xmlHandler;

    /**
     *
     */
    public static final String DIR = System.getProperty("user.dir");
    public static final String path = DIR + "\\files\\xml\\elements.xml";

    /**
     *
     * @param socket
     */
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    /**
     *
     */
    public void run() {
        MusicHub theHub = new MusicHub ();

        try {
            //create the streams that will handle the objects coming through the sockets
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());

            String textFromClient = (String)input.readObject();
            String command = (String)input.readObject();
            System.out.println("Server received the command: " + command);
            System.out.println(textFromClient);
            String title = new String();

            switch (command.charAt(0)){
                case 't':
                    commandT(theHub);
                break;
                case 'g':
                    title = (String)input.readObject();
                    commandG(theHub, title);
                break;
                case 'd':
                    title = (String)input.readObject();
                    commandD(theHub, title);
                break;
                case 'u':
                    commandU(theHub);
                break;
                case 'c':
                    title = (String)input.readObject();
                    commandC(theHub, title);
                break;
                default:
                break;
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();

        } catch (ClassNotFoundException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    /**
     *
     * @param theHub
     */
    public void commandT(MusicHub theHub) {
        try{
            System.out.println(theHub.getAlbumsTitlesSortedByDate());
            String toClient = theHub.getAlbumsTitlesSortedByDate();
            output.writeObject(toClient);
        }catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param theHub
     * @param albumTitle
     */
    public void commandG(MusicHub theHub, String albumTitle) {
        try{
            try {
                System.out.println(theHub.getAlbumSongsSortedByGenre(albumTitle));
                String toClient = theHub.getAlbumSongsSortedByGenre_Title(albumTitle);
                output.writeObject(toClient);
            } catch (NoAlbumFoundException ex) {
                System.out.println("No album found with the requested title " + ex.getMessage());
            }
        }catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param theHub
     * @param albumTitle
     */
    public void commandD(MusicHub theHub, String albumTitle) {
        try{
            try {
                System.out.println(theHub.getAlbumSongs(albumTitle));
                String toClient = theHub.getAlbumSongs_Title(albumTitle);
                output.writeObject(toClient);
            } catch (NoAlbumFoundException ex) {
                System.out.println("No album found with the requested title " + ex.getMessage());
            }
        }catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param theHub
     */
    public void commandU(MusicHub theHub) {
        try{
            System.out.println(theHub.getAudiobooksTitlesSortedByAuthor());
            String toClient = theHub.getAudiobooksTitlesSortedByAuthor();
            output.writeObject(toClient);
        }catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param theHub
     * @param songTitle
     */
    public void commandC(MusicHub theHub, String songTitle) {
        try{
            xmlHandler = new XMLHandler();
            String songContent = xmlHandler.searchSongInXMLFile(songTitle, path);
            System.out.println(songTitle + " is playing.");
            output.writeObject(songContent);
        }catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}