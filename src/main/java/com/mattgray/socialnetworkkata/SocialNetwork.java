package com.mattgray.socialnetworkkata;

import com.mattgray.socialnetworkkata.timeline.TimelineRepositoryImpl;
import com.mattgray.socialnetworkkata.timeline.TimelineService;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Scanner;

public class SocialNetwork {

    private final CommandProcessor commandProcessor;
    private final Clock clock;

    public SocialNetwork(CommandProcessor commandProcessor, Clock clock) {
        this.commandProcessor = commandProcessor;
        this.clock = clock;
    }

    public void run() {

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            commandProcessor.process(scanner.nextLine(), LocalDateTime.now(clock));
        }
    }

    public static void main(String[] args) {

        SocialNetwork socialNetwork = new SocialNetwork(new CommandProcessor(new TimelineService(new TimelineRepositoryImpl()), new FollowerService()), Clock.systemDefaultZone());

        socialNetwork.run();
    }
}