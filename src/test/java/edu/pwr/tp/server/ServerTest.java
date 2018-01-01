package edu.pwr.tp.server;

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

    private PrintWriter out;
    private BufferedReader in;
    private Socket sock;

    private void createParty(String name, int size, int bots) throws IOException, InterruptedException {
        assertEquals("{\"s_msg\":\"No parties available\"}", in.readLine());
        out.println("{\"i_action\":0,\"i_max\":" + size + ",\"i_bots\":" + bots + ",\"s_name\":\"" + name + "\"}");

        // {"i_action":0,"i_max":10,"i_bots":5,"s_name":"name"}

        Thread.sleep(100);

        assertEquals("{\"s_joined\":\"" + name + "\"}", in.readLine());
    }

    private void joinParty(String name, int left, PrintWriter m_out, BufferedReader m_in) throws IOException, InterruptedException {
        String response = m_in.readLine();
        assertEquals("{\"i_left\":" + left + ",\"i_size\":1,\"i_max\":10,\"s_name\":\"" + name + "\"}", response);

        // {"i_action":1,"s_name":"Test"}
        m_out.println("{\"i_action\":1,\"s_name\":\"" + name + "\"}");
        assertEquals("{\"s_joined\":\"" + name + "\"}", m_in.readLine());
    }

    @Before
    public void initUser() throws IOException {
        server = new Server();
        server.init();

        sock = new Socket("localhost", 4444);
        out = new PrintWriter(sock.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    }

    @Test
    public void testEmptyPartyList() throws IOException {
        assertEquals(in.readLine(), "{\"s_msg\":\"No parties available\"}");
    }

    @Test
    public void testCreateParty() throws IOException, InterruptedException {
        createParty("Test", 10, 5);
    }

    @Test
    public void testTooManyBots() throws IOException, InterruptedException {
        createParty("Test", 6, 6);
        assertEquals("{\"s_disc\":\"Disconnected\"}", in.readLine());
    }

    @Test
    public void testJoinParty() throws IOException, InterruptedException {
        int size = 10;
        int bots = 5;

        String name = "Test";
        createParty(name, size, bots);

        Socket m_sock = new Socket("localhost", 4444);
        PrintWriter m_out = new PrintWriter(m_sock.getOutputStream(), true);
        BufferedReader m_in = new BufferedReader(new InputStreamReader(m_sock.getInputStream()));
        joinParty(name, size - 1 - bots, m_out, m_in);

        Thread.sleep(100);

        m_out.close();
        m_in.close();
        m_sock.close();
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
