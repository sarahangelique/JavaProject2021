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

    public static final String WAV_FILE_PATH = "..\\..\\..\\..\\jMusicHub_server\\files\\wav\\";

    public void connect(String ip)
    {
        Scanner scan = new Scanner(System.in);
        int port = 6666;
        try  {
            //create the socket; it is defined by an remote IP address (the address of the server) and a port number
            socket = new Socket(ip, port);

            //create the streams that will handle the objects coming and going through the sockets
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            String textToSend = new String("Play the song: ");
            System.out.println("Enter the song title: ");
            String songToPlay = scan.nextLine();
            System.out.println("Command sent to the server: " + textToSend + songToPlay);
            output.writeObject(textToSend);		//serialize and write the String to the stream
            output.writeObject(songToPlay);		//serialize and write the String to the stream

            String audioContent = (String) input.readObject();	//deserialize and read the Student object from the stream
            System.out.println("Play music from database");
            try{
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(WAV_FILE_PATH + audioContent).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
                Thread.sleep(20000);
            } catch (Exception ex){
                System.out.println("Error with playing sound.");
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
}