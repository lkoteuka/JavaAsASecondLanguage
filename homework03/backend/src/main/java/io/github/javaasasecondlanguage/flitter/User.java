package io.github.javaasasecondlanguage.flitter;

import java.util.LinkedList;

public class User {
    private String name;
    private String token;
    LinkedList<String> subscribers = new LinkedList<String>();
    LinkedList<String> subscriptions = new LinkedList<String>();

    public User(String name, String token) {
        this.name = name;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }
}
