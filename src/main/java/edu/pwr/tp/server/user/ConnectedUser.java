package edu.pwr.tp.server.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectedUser extends User {

    private boolean isCreator;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ConnectedUser(boolean isCreator, Socket socket) throws IOException {
        this.isCreator = isCreator;

        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public boolean isCreator() {
        return isCreator;
    }

    public void send(String msg) {
        out.println(msg);
    }

    public String waitAndRecieve() throws IOException {
        String msg = null;

        while ((msg = in.readLine()) == null);

        return in.readLine();
    }
}
