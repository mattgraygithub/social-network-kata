package com.mattgray.socialnetworkkata.posting;

import com.mattgray.socialnetworkkata.clock.ClockService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class TimelineServiceImpl implements TimelineService {

    private final ClockService clockService;

    public TimelineServiceImpl(ClockService clockService) {
        this.clockService = clockService;
    }

    @Override
    public void displayTimeLine(ArrayList<Post> posts, LocalDateTime timeOfReadCommand) {
        ArrayList<Post> usersTimeline = new ArrayList<>(posts);
        Collections.reverse(usersTimeline);
        for (Post post : usersTimeline) {
            System.out.println(post.getPost() + clockService.getTimeBetween(post.getTimeOfPost(), timeOfReadCommand));
        }
    }
}
