package edu.pwr.tp.server.messageBuilder;

public interface IMessageBuilder {
    IMessageBuilder put(String name, Object o);
    String get();
}
