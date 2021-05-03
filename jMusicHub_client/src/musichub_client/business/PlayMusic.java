package musichub_client.business;

import java.io.*;
//import java.net.*;
//import java.util.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PlayMusic {
    public void play(String path, String audioContent){
        System.out.println("Play music...");
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