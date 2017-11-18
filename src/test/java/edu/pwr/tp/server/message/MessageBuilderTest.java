package edu.pwr.tp.server.message;

import edu.pwr.tp.server.message.builder.IMessageBuilder;
import edu.pwr.tp.server.message.builder.JSONMessageBuilder;
import edu.pwr.tp.server.message.parser.IMessageParser;
import edu.pwr.tp.server.message.parser.JSONMessageParser;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MessageBuilderTest {

    /**
     * Testing if the JSON String was made using correct data
     */
    @Test
    public void testJSONMessageBuilder() {
        IMessageBuilder msgBuild = new JSONMessageBuilder();

        String type = "command";
        int id = 7;
        int x = 13;
        int y = 77;

        String msg = msgBuild.put("type", type)
                             .put("id", id)
                             .put("x", x)
                             .put("y", y)
                             .get();
        JSONObject data = new JSONObject(msg);

        System.out.println(msg);

        assertTrue(type.equals(data.getString("type")));
        assertEquals(data.getInt("id"), id);
        assertEquals(data.getInt("x"), x);
        assertEquals(data.getInt("y"), y);
    }

    /**
     * Testing if the JSON parser correctly parses a valid JSON String
     */
    @Test
    public void testJSONMessageParser() {
        IMessageParser msgParse = new JSONMessageParser();
        Map<String, Object> objects = msgParse.parse("{\"x\":13,\"y\":77,\"id\":7,\"type\":\"command\"}");

        assertEquals(objects.size(), 4);

        assertEquals(objects.get("x"), 13);
        assertEquals(objects.get("y"), 77);
        assertEquals(objects.get("id"), 7);
        assertEquals(objects.get("type"), "command");
    }
}
