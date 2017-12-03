package edu.pwr.tp.server;

public class ServerManager {
    public static void main(String[] args) {
        Server server = Server.getServer();
        server.run();
    }
}
