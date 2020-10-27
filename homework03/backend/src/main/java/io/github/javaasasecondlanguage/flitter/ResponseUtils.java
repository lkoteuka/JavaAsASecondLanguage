package io.github.javaasasecondlanguage.flitter;

import java.util.LinkedList;


class RegisterUser {
    public String userName;
    public String userToken;

    public RegisterUser(String userName, String userToken) {
        this.userName = userName;
        this.userToken = userToken;
    }
}

class RegisterResponse {
    public RegisterUser data;
    public String errorMessage;

    public RegisterResponse(RegisterUser data, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
    }
}

class ErrorResponse {
    public RegisterUser data;
    public String errorMessage;

    public ErrorResponse(RegisterUser data, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
    }
}

class SuccessResponse {
    public String data;

    public SuccessResponse(String data) {
        this.data = data;
    }
}

class UserListResponse {
    public LinkedList<String> data;

    public UserListResponse(LinkedList<String> userNames) {
        this.data = userNames;
    }
}

class DiscoverFlit {
    public String userName;
    public String content;

    public DiscoverFlit(String userName, String content) {
        this.userName = userName;
        this.content = content;
    }
}

class DiscoverResponse {
    public LinkedList<DiscoverFlit> data;

    public DiscoverResponse(LinkedList<DiscoverFlit> data) {
        this.data = data;
    }
}

class UserFlit {
    public String userName;
    public String content;

    public UserFlit(String userNames, String content) {
        this.userName = userNames;
        this.content = content;
    }
}

class FlitListResponse {
    public LinkedList<UserFlit> data;

    public FlitListResponse(LinkedList<UserFlit> data) {
        this.data = data;
    }
}

class SubscribersResponse {
    public LinkedList<String> data;

    public SubscribersResponse(LinkedList<String> data) {
        this.data = data;
    }
}

class PublishersResponse {
    public LinkedList<String> data;

    public PublishersResponse(LinkedList<String> data) {
        this.data = data;
    }
}

class FlitAddResponse {
    public String data;

    public FlitAddResponse(String data) {
        this.data = data;
    }
}
