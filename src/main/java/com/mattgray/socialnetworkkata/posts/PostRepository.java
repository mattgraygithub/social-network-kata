package com.mattgray.socialnetworkkata.posts;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface PostRepository {
    void addPost(String post, LocalDateTime time);

    ArrayList<Post> getPosts();
}