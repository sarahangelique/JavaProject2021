package musichub_client.main;
import musichub_client.business.*;

public class Main{
    public static void main (String[] args) {
        SimpleClient c1 = new SimpleClient();
        c1.connect("localhost");

        System.out.println("WELCOME TO THE ESIEA MUSIC HUB APPLICATION ! ! !\n");
        System.out.println("Here are the available commands: \n");
        printAvailableCommands();

        Scanner scan = new Scanner(System.in);
        String choice = scan.nextLine();

        String albumTitle = null;

        if (choice.length() == 0) System.exit(0);

        while (choice.charAt(0)!= 'q') 	{
            switch (choice.charAt(0)) 	{
                case 'h':
                    printAvailableCommands();
                    choice = scan.nextLine();
                    break;
                case 't':
                    //album titles, ordered by date
                    System.out.println(theHub.getAlbumsTitlesSortedByDate());
                    printAvailableCommands();
                    choice = scan.nextLine();
                    break;
                case 'g':
                    //songs of an album, sorted by genre
                    System.out.println("Songs of an album sorted by genre will be displayed; enter the album name, available albums are:");
                    System.out.println(theHub.getAlbumsTitlesSortedByDate());

                    albumTitle = scan.nextLine();
                    try {
                        System.out.println(theHub.getAlbumSongsSortedByGenre(albumTitle));
                    } catch (NoAlbumFoundException ex) {
                        System.out.println("No album found with the requested title " + ex.getMessage());
                    }
                    printAvailableCommands();
                    choice = scan.nextLine();
                    break;
                case 'd':
                    //songs of an album
                    System.out.println("Songs of an album will be displayed; enter the album name, available albums are:");
                    System.out.println(theHub.getAlbumsTitlesSortedByDate());

                    albumTitle = scan.nextLine();
                    try {
                        System.out.println(theHub.getAlbumSongs(albumTitle));
                    } catch (NoAlbumFoundException ex) {
                        System.out.println("No album found with the requested title " + ex.getMessage());
                    }
                    printAvailableCommands();
                    choice = scan.nextLine();
                    break;
                case 'u':
                    //audiobooks ordered by author
                    System.out.println(theHub.getAudiobooksTitlesSortedByAuthor());
                    printAvailableCommands();
                    choice = scan.nextLine();
                    break;
                case 'c':
                    // play a song
                    System.out.println("Here is the list of available songs: ");
                    // A COMPLETER: AFFICHER LA LISTE
                    //
                    System.out.println("Please enter the title of the song you want to listen: ");
                    String title = scan.nextLine();
                    // A COMPLETER
                    //
                    printAvailableCommands();
                    choice = scan.nextLine();
                    break;
                case 'a':
                    // play the songs of an album
                    System.out.println("Here is the list of available albums: ");
                    // A COMPLETER: AFFICHER LA LISTE D'ALBUMS
                    //
                    System.out.println("Please enter the title of the album to play: ");
                    String aTitle = scan.nextLine();
                    // A COMPLETER
                    //
                    printAvailableCommands();
                    choice = scan.nextLine();
                    break;
/*
                case 'p':
                    //play the song of a playlis
                    System.out.println("Here is the list of available playlists: ");
                    // A COMPLETER: AFFICHER LA LISTE DE PLAYLISTE
                    //
                    System.out.println("Please enter the title of the playlist to play: ");
                    String aTitle = scan.nextLine();
                    // A COMPLETER
                    //
                    printAvailableCommands();
                    choice = scan.nextLine();
                    break;
                case 'n':
                    // create a new playlist from existing songs
                    System.out.println("Add an existing song or audiobook to a new playlist");
                    System.out.println("Existing playlists:");
                    Iterator<PlayList> itpl = theHub.playlists();
                    while (itpl.hasNext()) {
                        PlayList pl = itpl.next();
                        System.out.println(pl.getTitle());
                    }

                    System.out.println("Type the name of the playlist you wish to create:");
                    String playListTitle = scan.nextLine();
                    PlayList pl = new PlayList(playListTitle);
                    theHub.addPlaylist(pl);

                    System.out.println("Available elements: ");
                    Iterator<AudioElement> itael = theHub.elements();
                    while (itael.hasNext()) {
                        AudioElement ae = itael.next();
                        System.out.println(ae.getTitle());
                    }
                    while (choice.charAt(0)!= 'n') 	{
                        System.out.println("Type the name of the audio element you wish to add or 'n' to exit:");
                        String elementTitle = scan.nextLine();
                        try {
                            theHub.addElementToPlayList(elementTitle, playListTitle);
                        } catch (NoPlayListFoundException ex) {
                            System.out.println (ex.getMessage());
                        } catch (NoElementFoundException ex) {
                            System.out.println (ex.getMessage());
                        }

                        System.out.println("Type y to add a new one, n to end");
                        choice = scan.nextLine();
                    }
                    System.out.println("Playlist created!");
                    printAvailableCommands();
                    choice = scan.nextLine();
                    break;
                case '-':
                    //delete a playlist
                    System.out.println("Delete an existing playlist. Available playlists:");
                    Iterator<PlayList> itp = theHub.playlists();
                    while (itp.hasNext()) {
                        PlayList p = itp.next();
                        System.out.println(p.getTitle());
                    }
                    String plTitle = scan.nextLine();
                    try {
                        theHub.deletePlayList(plTitle);
                    }	catch (NoPlayListFoundException ex) {
                        System.out.println (ex.getMessage());
                    }
                    System.out.println("Playlist deleted!");
                    printAvailableCommands();
                    choice = scan.nextLine();
                    break;
                case 's':
                    //save playlists
                    theHub.saveElements();
                    theHub.saveAlbums();
                    theHub.savePlayLists();
                    System.out.println("Elements, albums and playlists saved!");
                    printAvailableCommands();
                    choice = scan.nextLine();
                    break;
 */
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

        System.out.println("c: play a song");
/*        System.out.println("a: play the songs of an album");
        System.out.println("p: play the song of a playlist\n");

        System.out.println("n: create a new playlist from existing songs");
        System.out.println("-: delete an existing playlist");
        System.out.println("s: save playlists\n");
*/
        System.out.println("q: quit program");
    }
}