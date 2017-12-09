package edu.pwr.tp.server;

public class ServerManager {

    /**
     * The server's main function
     */
    public static void main(String[] args) {
        System.out.println("Starting the server...");

        Server server = new Server();
        server.init();
    }
}
