package edu.pwr.tp.server.message.parser;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JSONMessageParser implements IMessageParser {

    @Override
    public Map<String, Object> parse(String msg) {
        JSONObject data = new JSONObject(msg);
        Map<String, Object> objects = new HashMap<>();

        for (String key : data.keySet()) {
            objects.put(key, data.get(key));
        }

        return objects;
    }
}
