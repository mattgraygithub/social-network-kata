package com.mattgray.socialnetworkkata.users;

import com.mattgray.socialnetworkkata.followees.FolloweeRepository;
import com.mattgray.socialnetworkkata.followees.InMemoryFolloweeRepository;
import com.mattgray.socialnetworkkata.posts.InMemoryPostRepository;
import com.mattgray.socialnetworkkata.posts.Post;
import com.mattgray.socialnetworkkata.posts.PostRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryUserRepository implements UserRepository {

    private final List<User> users;

    public InMemoryUserRepository(List<User> users) {
        this.users = users;
    }

    @Override
    public void addPost(String userName, String post, LocalDateTime time) {
        if (userExists(userName)) {
            addPostToExistingUser(userName, post, time);
        } else {
            addNewUserAndPost(userName, post, time);
        }
    }

    @Override
    public PostRepository getTimelineFor(String userName) {
        return users.get(getUserIndexOf(userName)).getTimeline();
    }

    @Override
    public void addFollowee(String userName, String followeeUserName) {
        if (userExists(userName)) {
            addFolloweeToExistingUser(userName, followeeUserName);
        } else {
            addNewUserAndFollowee(userName, followeeUserName);
        }
    }

    @Override
    public FolloweeRepository getFollowedUsersFor(String userName) {
        throw new UnsupportedOperationException();
    }

    private boolean userExists(String userName) {
        return users.stream().map(User::getUserName).anyMatch(userName::equals);
    }

    private int getUserIndexOf(String userName) {
        return users.indexOf(users.stream().filter(i -> userName.equals(i.getUserName())).findAny().orElse(null));
    }

    private void addPostToExistingUser(String userName, String post, LocalDateTime time) {
        PostRepository postRepository = getTimelineFor(userName);
        postRepository.addPost(post, time);
        FolloweeRepository followeeRepository = users.get(getUserIndexOf(userName)).getFolloweeRepository();
        updateUser(userName, postRepository, followeeRepository);
    }

    private void addNewUserAndPost(String userName, String post, LocalDateTime time) {
        PostRepository postRepository = new InMemoryPostRepository(new ArrayList<>(Collections.singletonList(new Post(post, time))));
        addUser(userName, postRepository, new InMemoryFolloweeRepository(new ArrayList<>()));
    }

    private void addFolloweeToExistingUser(String userName, String followeeUserName) {
        PostRepository postRepository = getTimelineFor(userName);
        FolloweeRepository followeeRepository = users.get(getUserIndexOf(userName)).getFolloweeRepository();
        followeeRepository.addFollowee(followeeUserName);
        updateUser(userName, postRepository, followeeRepository);
    }

    private void addNewUserAndFollowee(String userName, String followeeUserName) {
        FolloweeRepository followeeRepository = new InMemoryFolloweeRepository(new ArrayList<>(Collections.singletonList(followeeUserName)));
        users.add(new User(userName, new InMemoryPostRepository(new ArrayList<>()), followeeRepository));
    }

    private void updateUser(String userName, PostRepository postRepository, FolloweeRepository followeeRepository) {
        users.set(getUserIndexOf(userName), new User(userName, postRepository, followeeRepository));
    }

    private void addUser(String userName, PostRepository postRepository, FolloweeRepository followeeRepository) {
        users.add(new User(userName, postRepository, followeeRepository));
    }
}
