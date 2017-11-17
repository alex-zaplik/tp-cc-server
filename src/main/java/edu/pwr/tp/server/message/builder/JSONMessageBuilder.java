package edu.pwr.tp.server.message.builder;

import org.json.JSONObject;

public class JSONMessageBuilder implements IMessageBuilder {

    private JSONObject data = new JSONObject();

    @Override
    public IMessageBuilder put(String name, Object o) {
        data.put(name, o);
        return this;
    }

    @Override
    public String get() {
        return data.toString();
    }
}
