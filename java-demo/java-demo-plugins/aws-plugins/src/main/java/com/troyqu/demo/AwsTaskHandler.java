package com.troyqu.demo;

public class AwsTaskHandler implements Task {
    @Override
    public String handler(String s) {
        return mockDBAction(s);
    }

    String mockDBAction(String condition) {
        String msg = "Aws Handler Can not Handler Task: condition=" + condition;
        if (condition.toLowerCase().equals("s3") || condition.toLowerCase().equals("cloudwatch")) {
            msg = "Aws Handler Aws Task: condition=" + condition;
        }
        return msg;
    }
}
