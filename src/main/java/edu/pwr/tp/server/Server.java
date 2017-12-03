package edu.pwr.tp.server;

import edu.pwr.tp.server.party.Party;
import edu.pwr.tp.server.user.ConnectedUser;
import edu.pwr.tp.server.user.User;

import java.io.IOError;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {

    private static volatile Server instance;

    private static int port;

    private List<Party> parties;
    private List<User> users;

    private Thread emptySocket;

    public Server() {
        parties = new ArrayList<>();
        users = new ArrayList<>();

        Runnable waitForUser = () -> {
            while (true) {
                try {
                    ServerSocket serverSocket = new ServerSocket(port);
                    Socket clientSocket = serverSocket.accept();

                    users.add(new ConnectedUser(false, clientSocket));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        emptySocket = new Thread(waitForUser);
    }

    public static void setPort(int port) {
        Server.port = port;
    }

    public static Server getInstance() {
        if (instance == null) {
            synchronized (Server.class) {
                if (instance == null) {
                    instance = new Server();
                }
            }
        }

        return instance;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();

            User user = new ConnectedUser(true, clientSocket);
            users.add(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        emptySocket.start();
    }
}
