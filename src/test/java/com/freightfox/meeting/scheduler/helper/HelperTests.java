package com.freightfox.meeting.scheduler.helper;

import com.freightfox.meeting.scheduler.entity.Meeting;
import com.freightfox.meeting.scheduler.entity.UsersMeetings;
import com.freightfox.meeting.scheduler.model.MeetingModel;
import com.freightfox.meeting.scheduler.model.ReturnDetails;
import com.freightfox.meeting.scheduler.service.MeetingSchedulerService;
import com.freightfox.meeting.scheduler.service.MeetingSchedulerServiceImpl;
import com.freightfox.meeting.scheduler.util.helper.RepoHelper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class HelperTests {

    @Autowired
    private MeetingSchedulerServiceImpl meetingSchedulerService;

    @Test
    public void checkConflict1() {
        List<Meeting> allInviteeMeetings = new ArrayList<>();
        allInviteeMeetings.add(new Meeting(UUID.fromString("77ed081e-bc26-473c-a454-1e76634f1fdc"), Timestamp.from(Instant.parse("2021-04-11T14:00:00Z")), Timestamp.from(Instant.parse("2021-04-11T14:30:00Z"))));
        List<Meeting> conflictedMeeting = RepoHelper.checkConflict(allInviteeMeetings, Timestamp.from(Instant.parse("2021-04-11T14:00:00Z")), Timestamp.from(Instant.parse("2021-04-11T14:30:00Z")));
        assertEquals(UUID.fromString("77ed081e-bc26-473c-a454-1e76634f1fdc"), conflictedMeeting.get(0).getMeetingId());
    }

    @Test
    public void checkConflict2() {
        List<Meeting> allInviteeMeetings = new ArrayList<>();
        allInviteeMeetings.add(new Meeting(UUID.fromString("77ed081e-bc26-473c-a454-1e76634f1fdc"), Timestamp.from(Instant.parse("2021-04-11T14:00:00Z")), Timestamp.from(Instant.parse("2021-04-11T14:30:00Z"))));
        List<Meeting> conflictedMeeting = RepoHelper.checkConflict(allInviteeMeetings, Timestamp.from(Instant.parse("2021-04-11T14:29:00Z")), Timestamp.from(Instant.parse("2021-04-11T15:00:00Z")));
        assertEquals(UUID.fromString("77ed081e-bc26-473c-a454-1e76634f1fdc"), conflictedMeeting.get(0).getMeetingId());
    }

    @Test
    public void checkConflic3() {
        List<Meeting> allInviteeMeetings = new ArrayList<>();
        allInviteeMeetings.add(new Meeting(UUID.fromString("77ed081e-bc26-473c-a454-1e76634f1fdc"), Timestamp.from(Instant.parse("2021-04-11T14:00:00Z")), Timestamp.from(Instant.parse("2021-04-11T14:30:00Z"))));
        List<Meeting> conflictedMeeting = RepoHelper.checkConflict(allInviteeMeetings, Timestamp.from(Instant.parse("2021-04-11T13:45:00Z")), Timestamp.from(Instant.parse("2021-04-11T14:15:00Z")));
        assertEquals(UUID.fromString("77ed081e-bc26-473c-a454-1e76634f1fdc"), conflictedMeeting.get(0).getMeetingId());
    }

    @Test
    public void checkConflic4() {
        List<Meeting> allInviteeMeetings = new ArrayList<>();
        allInviteeMeetings.add(new Meeting(UUID.fromString("77ed081e-bc26-473c-a454-1e76634f1fdc"), Timestamp.from(Instant.parse("2021-04-11T14:00:00Z")), Timestamp.from(Instant.parse("2021-04-11T14:30:00Z"))));
        List<Meeting> conflictedMeeting = RepoHelper.checkConflict(allInviteeMeetings, Timestamp.from(Instant.parse("2021-04-11T13:45:00Z")), Timestamp.from(Instant.parse("2021-04-11T14:00:00Z")));
        assertEquals(0, conflictedMeeting.size());
    }

    @Test
    public void checkConflic5() {
        List<Meeting> allInviteeMeetings = new ArrayList<>();
        allInviteeMeetings.add(new Meeting(UUID.fromString("77ed081e-bc26-473c-a454-1e76634f1fdc"), Timestamp.from(Instant.parse("2021-04-11T14:00:00Z")), Timestamp.from(Instant.parse("2021-04-11T14:30:00Z"))));
        List<Meeting> conflictedMeeting = RepoHelper.checkConflict(allInviteeMeetings, Timestamp.from(Instant.parse("2021-04-11T14:30:00Z")), Timestamp.from(Instant.parse("2021-04-11T14:45:00Z")));
        assertEquals(0, conflictedMeeting.size());
    }

    @Test
    public void checkGetConflictInMeetingUsers1() {
        List<Meeting> conflictedMeetings = new ArrayList<>();
        conflictedMeetings.add(new Meeting(UUID.fromString("77ed081e-bc26-473c-a454-1e76634f1fdc"), Timestamp.from(Instant.parse("2021-04-11T14:00:00Z")), Timestamp.from(Instant.parse("2021-04-11T14:30:00Z"))));
        List<UsersMeetings> allUsersMeetings = new ArrayList<>();
        allUsersMeetings.add(new UsersMeetings(UUID.fromString("d561d169-272f-4861-92f9-01bcbe085ded"), UUID.fromString("1ec1bc2c-8419-4ced-a9e5-e86ff2ba5bfa"), UUID.fromString("77ed081e-bc26-473c-a454-1e76634f1fdc")));
        List<UUID> userIds = RepoHelper.getConflictInMeetingUsers(allUsersMeetings, conflictedMeetings);
        assertEquals(UUID.fromString("1ec1bc2c-8419-4ced-a9e5-e86ff2ba5bfa"), userIds.get(0));
    }

    @Test
    public void checkGetConflictInMeetingUsers2() {
        List<Meeting> conflictedMeetings = new ArrayList<>();
//        conflictedMeetings.add(new Meeting(UUID.fromString("77ed081e-bc26-473c-a454-1e76634f1fdc"), Timestamp.from(Instant.parse("2021-04-11T14:00:00Z")), Timestamp.from(Instant.parse("2021-04-11T14:30:00Z"))));
        List<UsersMeetings> allUsersMeetings = new ArrayList<>();
        allUsersMeetings.add(new UsersMeetings(UUID.fromString("d561d169-272f-4861-92f9-01bcbe085ded"), UUID.fromString("1ec1bc2c-8419-4ced-a9e5-e86ff2ba5bfa"), UUID.fromString("77ed081e-bc26-473c-a454-1e76634f1fdc")));
        List<UUID> userIds = RepoHelper.getConflictInMeetingUsers(allUsersMeetings, conflictedMeetings);
        assertEquals(0, userIds.size());
    }


}
