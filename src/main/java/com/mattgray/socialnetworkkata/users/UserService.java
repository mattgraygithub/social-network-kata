package com.mattgray.socialnetworkkata.users;

import java.time.LocalDateTime;
import java.util.Arrays;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addPost(String command, LocalDateTime time) {
        userRepository.addPost(getUserName(command), getPost(command), time);
    }

    private String getUserName(String command) {
        return command.split(" ")[0];
    }

    private String getPost(String command) {
        String[] commandAsArray = command.split(" ");
        String[] post = Arrays.copyOfRange(commandAsArray, 2, commandAsArray.length);
        return String.join(" ", post);
    }
}