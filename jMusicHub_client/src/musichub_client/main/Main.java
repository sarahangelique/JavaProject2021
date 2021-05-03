package musichub_client.main;

import musichub_client.business.*;
import java.util.*;


public class Main{
    public static void main (String[] args) {
        SimpleClient c1 = new SimpleClient();
        //c1.connect("localhost");

        System.out.println("WELCOME TO THE ESIEA MUSIC HUB APPLICATION ! ! !\n");
        System.out.println("Here are the available commands: \n");
        printAvailableCommands();

        Scanner scan = new Scanner(System.in);
        String choice = scan.nextLine();

        String albumTitle = null;
        String songTitle = null;

        if (choice.length() == 0) System.exit(0);

        while (choice.charAt(0)!= 'q') 	{
            switch (choice.charAt(0)) 	{
                case 'h':
                    printAvailableCommands();
                    choice = scan.nextLine();
                break;

                case 't':
                    //album titles, ordered by date
                    System.out.println("Album ordered by date: ");
                    c1.connection("localhost", "t");
                    printAvailableCommands();
                    choice = scan.nextLine();
                break;

                case 'g':
                    //songs of an album, sorted by genre
                    System.out.println("Songs of an album sorted by genre will be displayed; enter the album name, available albums are:");
                    c1.connection("localhost", "t");
                    albumTitle = scan.nextLine();
                    c1.connection("localhost", albumTitle, "g");
                    printAvailableCommands();
                    choice = scan.nextLine();
                break;

                case 'd':
                    //songs of an album
                    System.out.println("Songs of an album will be displayed; enter the album name, available albums are:");
                    c1.connection("localhost", "t");
                    albumTitle = scan.nextLine();
                    c1.connection("localhost", albumTitle, "d");
                    printAvailableCommands();
                    choice = scan.nextLine();
                break;

                case 'u':
                    //audiobooks ordered by author
                    System.out.println("Audiobooks ordered by author: ");
                    c1.connection("localhost", "u");
                    printAvailableCommands();
                    choice = scan.nextLine();
                break;

                case 'c':
                    // play a song
                    System.out.println("Please enter the title of the song you want to listen: ");
                    songTitle = scan.nextLine();
                    c1.connection("localhost", songTitle, "c");
                    printAvailableCommands();
                    choice = scan.nextLine();
                break;

                default:
                break;
            }
        }
        scan.close();
    }

    private static void printAvailableCommands() {
        System.out.println("t: display the album titles, ordered by date");
        System.out.println("g: display songs of an album, ordered by genre");
        System.out.println("d: display songs of an album");
        System.out.println("u: display audiobooks ordered by author\n");
        System.out.println("c: play a song\n");
        System.out.println("q: quit program");
    }
}