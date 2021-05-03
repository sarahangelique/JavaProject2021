package musichub_server.business;

import java.io.*;
import java.net.*;


public class ServerConnection {

    /**
     *asks the permission for a connection from the server to the client
     * @param args
     */
    public static void main (String[] args) {
        AbstractServer as = new FirstServer();
        String ip = "localhost";
        as.connect(ip);

    }
}