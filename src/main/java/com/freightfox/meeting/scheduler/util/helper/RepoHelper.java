package com.freightfox.meeting.scheduler.util.helper;

import com.freightfox.meeting.scheduler.entity.Meeting;
import com.freightfox.meeting.scheduler.entity.UsersMeetings;
import com.freightfox.meeting.scheduler.model.MeetingModel;
import com.freightfox.meeting.scheduler.repository.UsersMeetingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class RepoHelper {

    public static List<Meeting> checkConflict(List<Meeting> usersMeetings, Timestamp startTime, Timestamp endTime) {
        List<Meeting> conflictedMeetings = new ArrayList<>();
        usersMeetings.stream().forEach(e -> {
            Boolean ans = true;
            if ((
                    e.getStartTime().compareTo(startTime) == -1
                            && e.getEndTime().compareTo(startTime) == 1
            ) || (
                    e.getStartTime().compareTo(endTime) == -1
                            && e.getEndTime().compareTo(endTime) == 1
            ) || (
                    e.getStartTime().compareTo(startTime)==0
                    && e.getEndTime().compareTo(endTime)==0
                    )
            ) {
                conflictedMeetings.add(e);
            }
        });
        return conflictedMeetings;
    }

    public static List<UUID> getAllMeetings(List<UsersMeetings> usersMeetings) {
        List<UUID> meetings = new ArrayList<>();
        usersMeetings.forEach(e -> meetings.add(e.getMeetingId()));
        return meetings;
    }


    public static List<UUID> getConflictInMeetingUsers(List<UsersMeetings> allUsersMeetings, List<Meeting> conflictedMeetings) {
        HashMap<UUID, Integer> conflictedMeetingsMap = new HashMap<>();
        conflictedMeetings.forEach(e->conflictedMeetingsMap.put(e.getMeetingId(),1));
        List<UsersMeetings> usersWithConflict = allUsersMeetings.stream().filter(e->conflictedMeetingsMap.containsKey(e.getMeetingId())?true:false)
                .collect(Collectors.toList());
        List<UUID> conflictedUserIds = usersWithConflict.stream().map(e->e.getUserId()).collect(Collectors.toList());
        return conflictedUserIds;
    }


}
