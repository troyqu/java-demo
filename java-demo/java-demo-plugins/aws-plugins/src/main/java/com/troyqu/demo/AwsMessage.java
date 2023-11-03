package com.troyqu.demo;

//public class AcsMessage{
public class AwsMessage implements Message {

    Task taskHandler;

    public AwsMessage() {
        taskHandler = new AwsTaskHandler();
    }

    @Override
    public String send(String s) {
        return this.taskHandler.handler(s);
    }
}
