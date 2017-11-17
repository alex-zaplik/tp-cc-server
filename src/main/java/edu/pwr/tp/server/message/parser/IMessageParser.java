package edu.pwr.tp.server.message.parser;

import java.util.Map;

public interface IMessageParser {
    Map<String, Object> parse(String msg);
}
