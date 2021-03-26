package com.mattgray.socialnetworkkata;

import com.mattgray.socialnetworkkata.timeline.TimelineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CommandProcessorShould {

    CommandProcessor commandProcessor;
    TimelineService mockTimelineService;
    FollowerService mockFollowerService;

    @BeforeEach
    void setUp() {
        mockTimelineService = mock(TimelineService.class);
        mockFollowerService = mock(FollowerService.class);
        commandProcessor = new CommandProcessor(mockTimelineService, mockFollowerService);
    }

    @Test
    void delegatePostCommandsToTimelineService() {

        LocalDateTime now = LocalDateTime.now();

        commandProcessor.process(TestCommands.ALICE_EXAMPLE_POST_COMMAND, now);

        verify(mockTimelineService).addPost(TestCommands.ALICE_EXAMPLE_POST_COMMAND, now);
    }
}