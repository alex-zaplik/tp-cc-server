package edu.pwr.tp.server;

import org.json.JSONObject;

public class Server {

    private static volatile Server instance;

    private String buildString(Pawn p) {
        JSONObject data = new JSONObject();
        data.put("id", p.id);
        data.put("x", p.x);
        data.put("y", p.y);

        return data.toString();
    }

    private Pawn parseString(String s) {
        JSONObject data = new JSONObject(s);

        int id = data.getInt("id");
        int x = data.getInt("x");
        int y = data.getInt("y");

        return new Pawn(id, x, y);
    }

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
        Pawn pOrg = new Pawn(1,2,3);
        String msg = getServer().buildString(pOrg);
        Pawn pNew = getServer().parseString(msg);

        System.out.println(pOrg);
        System.out.println(pNew);
    }
}
