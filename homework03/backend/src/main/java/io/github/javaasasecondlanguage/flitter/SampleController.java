package io.github.javaasasecondlanguage.flitter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;


@RestController
//@RequestMapping("/api")
public class SampleController {
    ConcurrentHashMap<String, User> users = new ConcurrentHashMap<String, User>();
    ConcurrentLinkedDeque<Flit> flits = new ConcurrentLinkedDeque<>();

    @GetMapping("/greeting")
    String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        System.out.println("Invoked greeting: " + name);
        return "Hello " + name;
    }

    @DeleteMapping("/clear")
    ResponseEntity<?> clearAll() {
        users = new ConcurrentHashMap<String, User>();
        flits = new ConcurrentLinkedDeque<>();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/register")
    ResponseEntity<?> register(@RequestBody String bodyJson) {
        try {
            RegisterRequest req = new ObjectMapper().readValue(bodyJson, RegisterRequest.class);
            String userName = req.userName;
            if (users.containsKey(userName)) {
                RegisterResponse response = new RegisterResponse(null, "This name is already taken");
                String responseJson = new ObjectMapper().writeValueAsString(response);
                return ResponseEntity.badRequest().body(responseJson);
            } else {
                String token = UUID.randomUUID().toString();
                User newUser = new User(userName, token);
                users.put(userName, newUser);
                RegisterUser userResponse = new RegisterUser(newUser.getName(), newUser.getToken());
                RegisterResponse response = new RegisterResponse(userResponse, null);
                String responseJson = new ObjectMapper().writeValueAsString(response);
                return ResponseEntity.ok(responseJson);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/list")
    ResponseEntity<?> getAllUsers() {
        try {
            LinkedList<String> names = new LinkedList<String>(users.keySet());
            UserListResponse response = new UserListResponse(names);
            String responseJson = new ObjectMapper().writeValueAsString(response);
            return ResponseEntity.ok().body(responseJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/flit/add")
    ResponseEntity<?> addFlit(@RequestBody String bodyJson) {
        try {
            FlitAddRequest req = new ObjectMapper().readValue(bodyJson, FlitAddRequest.class);
            String userToken = req.userToken;
            String content = req.content;
            System.out.println("content: " + content);

            User checkExist = null;
            for (var user : users.entrySet()) {
                if (user.getValue().getToken().equals(userToken)) {
                    checkExist = user.getValue();
                    Flit flit = new Flit(user.getValue().getName(), user.getValue().getToken(), content);
                    flits.addLast(flit);
                }
            }
            if (checkExist == null) {
                ErrorResponse response = new ErrorResponse(null, "User not found");
                String responseJson = new ObjectMapper().writeValueAsString(response);
                return ResponseEntity.badRequest().body(responseJson);
            }
            FlitAddResponse response = new FlitAddResponse("ok");
            String responseJson = new ObjectMapper().writeValueAsString(response);
            return ResponseEntity.ok(responseJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/flit/discover")
    ResponseEntity<?> lastFlits() {
        try {
            LinkedList<DiscoverFlit> lastFlits = new LinkedList<DiscoverFlit>();
            int N = 10;
            ArrayList<Flit> flitsArr = new ArrayList(flits);
            for (int ind = flitsArr.size() - 10; ind < flitsArr.size(); ind++, N--) {
                if (ind < 0) {
                    continue;
                }
                lastFlits.add(new DiscoverFlit(flitsArr.get(ind).name, flitsArr.get(ind).content));
                if (N <= 0) {
                    break;
                }
            }
            DiscoverResponse response = new DiscoverResponse(lastFlits);
            String responseJson = new ObjectMapper().writeValueAsString(response);
            return ResponseEntity.ok().body(responseJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/flit/list/{username}")
    ResponseEntity<?> getFlitsUser(@PathVariable(value = "username") String username) {
        try {
            if (!users.containsKey(username)) {
                ErrorResponse response = new ErrorResponse(null, "User not found");
                String responseJson = new ObjectMapper().writeValueAsString(response);
                return ResponseEntity.badRequest().body(responseJson);
            }
            LinkedList<UserFlit> userFlits = new LinkedList<UserFlit>();
            for (var flit : flits) {
                if (flit.name.equals(username)) {
                    userFlits.add(new UserFlit(flit.name, flit.content));
                }
            }
            FlitListResponse response = new FlitListResponse(userFlits);
            String responseJson = new ObjectMapper().writeValueAsString(response);
            return ResponseEntity.ok().body(responseJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/flit/list/feed/{usertoken}")
    ResponseEntity<?> getFeedUser(@PathVariable(value = "usertoken") String usertoken) {
        try {
            LinkedList<String> subs = null;
            for (var user : users.entrySet()) {
                if (user.getValue().getToken().equals(usertoken)) {
                    subs = user.getValue().subscriptions;
                }
            }

            if (subs == null) {
                ErrorResponse response = new ErrorResponse(null, "User not found");
                String responseJson = new ObjectMapper().writeValueAsString(response);
                return ResponseEntity.badRequest().body(responseJson);
            }
            LinkedList<UserFlit> feedFlits = new LinkedList<>();
            for (var flit : flits) {
                for (var user : subs) {
                    if (user.equals(flit.name)) {
                        feedFlits.add(new UserFlit(flit.name, flit.content));
                    }
                }
            }
            FlitListResponse response = new FlitListResponse(feedFlits);
            String responseJson = new ObjectMapper().writeValueAsString(response);
            return ResponseEntity.ok().body(responseJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/subscribe")
    ResponseEntity<?> subscribe(@RequestBody String bodyJson) {
        try {
            SubscribeRequest req = new ObjectMapper().readValue(bodyJson, SubscribeRequest.class);
            String subscriberToken = req.subscriberToken;
            String publisherName = req.publisherName;
            for (var user : users.entrySet()) {
                if (user.getValue().getToken().equals(subscriberToken)) {
                    if (users.containsKey(publisherName)) {
                        user.getValue().subscriptions.add(publisherName);
                        users.get(publisherName).subscribers.add(user.getValue().getName());
                        SuccessResponse response = new SuccessResponse("ok");
                        String responseJson = new ObjectMapper().writeValueAsString(response);
                        return ResponseEntity.ok(responseJson);
                    }
                }
            }
            ErrorResponse response = new ErrorResponse(null, "User not found");
            String responseJson = new ObjectMapper().writeValueAsString(response);
            return ResponseEntity.badRequest().body(responseJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/unsubscribe")
    ResponseEntity<?> unsubscribe(@RequestBody String bodyJson) {
        try {
            SubscribeRequest req = new ObjectMapper().readValue(bodyJson, SubscribeRequest.class);
            String subscriberToken = req.subscriberToken;
            String publisherName = req.publisherName;
            for (var user : users.entrySet()) {
                if (user.getValue().getToken().equals(subscriberToken)) {
                    if (users.containsKey(publisherName)) {
                        if (user.getValue().subscriptions.contains(publisherName)) {
                            user.getValue().subscriptions.remove(publisherName);
                            users.get(publisherName).subscribers.remove(user.getValue().getName());
                        }
                        SuccessResponse response = new SuccessResponse("ok");
                        String responseJson = new ObjectMapper().writeValueAsString(response);
                        return ResponseEntity.ok(responseJson);
                    }
                }
            }
            ErrorResponse response = new ErrorResponse(null, "User not found");
            String responseJson = new ObjectMapper().writeValueAsString(response);
            return ResponseEntity.badRequest().body(responseJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/subscribers/list/{usertoken}")
    ResponseEntity<?> subscribersList(@PathVariable(value = "usertoken") String usertoken) {
        try {
            for (var user : users.entrySet()) {
                if (user.getValue().getToken().equals(usertoken)) {
                    SubscribersResponse subscribers = new SubscribersResponse(user.getValue().subscribers);
                    String responseJson = new ObjectMapper().writeValueAsString(subscribers);
                    return ResponseEntity.ok(responseJson);
                }
            }
            ErrorResponse response = new ErrorResponse(null, "User not found");
            String responseJson = new ObjectMapper().writeValueAsString(response);
            return ResponseEntity.badRequest().body(responseJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/publishers/list/{userToken}")
    ResponseEntity<?> publishersList(@PathVariable(value = "userToken") String userToken) {
        try {
            for (var user : users.entrySet()) {
                if (user.getValue().getToken().equals(userToken)) {
                    PublishersResponse publishers = new PublishersResponse(user.getValue().subscriptions);
                    String responseJson = new ObjectMapper().writeValueAsString(publishers);
                    return ResponseEntity.ok(responseJson);
                }
            }
            ErrorResponse response = new ErrorResponse(null, "User not found");
            String responseJson = new ObjectMapper().writeValueAsString(response);
            return ResponseEntity.badRequest().body(responseJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
