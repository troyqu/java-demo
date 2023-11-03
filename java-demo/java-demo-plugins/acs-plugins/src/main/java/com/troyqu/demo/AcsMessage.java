package com.troyqu.demo;

//public class AcsMessage{
public class AcsMessage implements Message {

    Task taskHandler;

    public AcsMessage() {
        taskHandler = new AcsTaskHandler();
    }

    @Override
    public String send(String s) {
        return this.taskHandler.handler(s);
    }
}
