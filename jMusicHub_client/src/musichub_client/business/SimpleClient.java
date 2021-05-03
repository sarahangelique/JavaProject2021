package musichub_client.business;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class SimpleClient {

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket socket;

    public static final String DIR = System.getProperty("user.dir");
    public static final String path = DIR + "\\..\\jMusicHub_server\\files\\wav\\";

    public void connection(String ip, String command){
        // album titles, ordered by date
        Scanner scan = new Scanner(System.in);
        int port = 6666;
        try  {
            //create the socket; it is defined by an remote IP address (the address of the server) and a port number
            socket = new Socket(ip, port);

            //create the streams that will handle the objects coming and going through the sockets
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            String textToSend = new String();

            switch (command.charAt(0)){
                case 't':
                    textToSend = "Display album titles, ordered by date. ";
                    output.writeObject(textToSend);
                    output.writeObject(command);
                break;
                case 'u':
                    textToSend = "Display udiobooks, ordered by author. ";
                    output.writeObject(textToSend);
                    output.writeObject(command);
                break;
                default:
                break;
            }

            String fromServer = (String) input.readObject();
            System.out.println(fromServer);

        } catch  (UnknownHostException uhe) {
            uhe.printStackTrace();
        }
        catch  (IOException ioe) {
            ioe.printStackTrace();
        }
        catch  (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        finally {
            try {
                input.close();
                output.close();
                socket.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public void connection(String ip, String title, String command) {
        // songs of an album, sorted by genre
        Scanner scan = new Scanner(System.in);
        int port = 6666;
        try  {
            //create the socket; it is defined by an remote IP address (the address of the server) and a port number
            socket = new Socket(ip, port);

            //create the streams that will handle the objects coming and going through the sockets
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            String textToSend = new String();
            String fromServer = new String();

            switch (command.charAt(0)){
                case 'g':
                    textToSend = "Display songs of the album " +title+ " sorted by genre. ";
                    output.writeObject(textToSend);
                    output.writeObject(command);
                    output.writeObject(title);
                    fromServer = (String) input.readObject();
                    System.out.println(fromServer);
                break;
                case 'd':
                    textToSend = "Display songs of the album " +title+ ". ";
                    output.writeObject(textToSend);
                    output.writeObject(command);
                    output.writeObject(title);
                    fromServer = (String) input.readObject();
                    System.out.println(fromServer);
                break;
                case 'c':
                    textToSend = "Play the song " +title+ ". ";
                    output.writeObject(textToSend);
                    output.writeObject(command);
                    output.writeObject(title);
                    fromServer = (String) input.readObject();
                    play(path, fromServer);
                break;
                default:
                break;
            }

        } catch  (UnknownHostException uhe) {
            uhe.printStackTrace();
        }
        catch  (IOException ioe) {
            ioe.printStackTrace();
        }
        catch  (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        finally {
            try {
                input.close();
                output.close();
                socket.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public void play(String path, String audioContent){
        System.out.println("Play music... " + audioContent);
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path + audioContent).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            Thread.sleep(20000);
        } catch (Exception ex){
            System.out.println("Error with playing sound.");
        }
    }

}
