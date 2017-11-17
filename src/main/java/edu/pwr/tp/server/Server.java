package edu.pwr.tp.server;

import org.json.JSONObject;

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

    public static void main(String[] args) {
    }
}
