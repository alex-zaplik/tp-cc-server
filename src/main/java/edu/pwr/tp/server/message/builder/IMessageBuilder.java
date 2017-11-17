package edu.pwr.tp.server.message.builder;

public interface IMessageBuilder {
    IMessageBuilder put(String name, Object o);
    String get();
}
