package io.github.javaasasecondlanguage.flitter;

class RegisterRequest {
    public String userName;
}

class FlitAddRequest {
    public String userToken;
    public String content;
}

class SubscribeRequest {
    public String subscriberToken;
    public String publisherName;
}