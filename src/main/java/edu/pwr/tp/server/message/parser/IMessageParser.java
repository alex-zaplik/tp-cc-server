package edu.pwr.tp.server.message.parser;

import java.util.Map;

public interface IMessageParser {

    /**
     * Returns a {@link Map}, where the key is the property name
     *
     * @param msg   {@link String} to be parsed
     * @return      {@link Map}, where the key is the property name
     */
    Map<String, Object> parse(String msg) throws NullPointerException;
}
