package edu.pwr.tp.server.messageBuilder;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MessageBuilderTest {

    @Test
    public void testJSONMessageBuilder() {
        IMessageBuilder msgBuild = new JSONMessageBuilder();

        String type = "command";
        int id = 7;
        int x = 13;
        int y = 77;

        String msg = msgBuild.put("type", type).put("id", id).put("x", x).put("y", y).get();
        JSONObject data = new JSONObject(msg);

        assertTrue(type.equals(data.getString("type")));
        assertEquals(data.getInt("id"), id);
        assertEquals(data.getInt("x"), x);
        assertEquals(data.getInt("y"), y);
    }
}
