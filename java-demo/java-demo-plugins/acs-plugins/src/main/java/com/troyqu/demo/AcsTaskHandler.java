package com.troyqu.demo;

public class AcsTaskHandler implements Task {
    @Override
    public String handler(String s) {
        return mockDBAction(s);
    }

    String mockDBAction(String condition) {
        String msg = "Acs Handler Can not Handler Task: condition=" + condition;
        if (condition.toLowerCase().equals("oss") || condition.toLowerCase().equals("ecs")) {
            msg = "Acs Handler Acs Task: condition=" + condition;
        }
        return msg;
    }
}
