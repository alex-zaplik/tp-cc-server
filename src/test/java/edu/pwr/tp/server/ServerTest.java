package edu.pwr.tp.server;

import edu.pwr.tp.server.user.ConnectedUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class ServerTest {

    private Server server;

    private ConnectedUser user;
    private PrintWriter out;
    private BufferedReader in;
    private Socket sock;

    @Before
    public void initUser() throws IOException {
        server = new Server();
        server.init();

        sock = new Socket("localhost", 4444);
        out = new PrintWriter(sock.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

        user = new ConnectedUser(sock, 0);
    }

    @Test
    public void testEmptyPartyList() throws IOException {
        assertEquals(in.readLine(), "{\"s_msg\":\"No parties available\"}");
    }

    @Test
    public void testCreateParty() throws IOException, InterruptedException {
        String name = "Test";
        int size = 10;

        assertEquals("{\"s_msg\":\"No parties available\"}", in.readLine());
        out.println("{\"i_action\":0,\"i_max\":" + size + ",\"s_name\":\"" + name + "\"}");

        for (int i = 0; i < 200000; i++) {
            // Waiting for the server to finish
            System.out.print("");
        }

        assertEquals(1, server.getParties().size());
    }

    @After
    public void cleanUp() throws IOException, InterruptedException {
        out.close();
        in.close();
        sock.close();
        server.stop();

        System.out.println();
    }
}
