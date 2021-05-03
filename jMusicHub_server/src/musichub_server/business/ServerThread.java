package musichub_server.business;

import java.io.*;
import java.net.*;
import org.w3c.dom.*;


/**
 * This thread is responsible to handle client connection.
 */
public class ServerThread extends Thread {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private XMLHandler demo;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            //create the streams that will handle the objects coming through the sockets
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());

            String text = (String)input.readObject();  //read the object received through the stream and deserialize it
            String song = (String)input.readObject();  //read the object received through the stream and deserialize it
            //System.out.println("Server received a command:" + song);
            System.out.println("Server received a command: " + text + song);

            //Xml demo = new Xml();
            demo = new XMLHandler();
            String songContent = demo.searchSongInXMLFile(song);

            //String songContent = "something.wav";
            output.writeObject(songContent);		//serialize and write the Student object to the stream



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
}