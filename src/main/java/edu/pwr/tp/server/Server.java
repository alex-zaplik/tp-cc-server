package edu.pwr.tp.server;

public class Server {

    private static volatile Server instance;

    public static Server getServer() {
        if (instance == null) {
            synchronized (Server.class) {
                if (instance == null) {
                    instance = new Server();
                }
            }
        }

        return instance;
    }

    private void init() {

    }

    private void run() {

    }

    public static void main(String[] args) {
        Server server = getServer();
        server.init();
        server.run();
    }
}
