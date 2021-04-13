package com.freightfox.meeting.scheduler.util.helper;

import com.freightfox.meeting.scheduler.entity.Meeting;
import com.freightfox.meeting.scheduler.entity.UsersMeetings;
import com.freightfox.meeting.scheduler.model.MeetingModel;
import com.freightfox.meeting.scheduler.model.MeetingSlot;
import com.freightfox.meeting.scheduler.repository.UsersMeetingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;
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
                    e.getStartTime().compareTo(startTime) == 0
                            && e.getEndTime().compareTo(endTime) == 0
            )
            ) {
                conflictedMeetings.add(e);
            }
        });
        return conflictedMeetings;
    }

    public static List<UUID> getAllMeetings(List<UsersMeetings> usersMeetings) {
        List<UUID> meetings = new ArrayList<>();
        Set<UUID> meetingsSet = new HashSet<>();
        usersMeetings.forEach(e -> meetingsSet.add(e.getMeetingId()));
        meetings = new ArrayList<>(meetingsSet);
        return meetings;
    }


    public static List<UUID> getConflictInMeetingUsers(List<UsersMeetings> allUsersMeetings, List<Meeting> conflictedMeetings) {
        HashMap<UUID, Integer> conflictedMeetingsMap = new HashMap<>();
        conflictedMeetings.forEach(e -> conflictedMeetingsMap.put(e.getMeetingId(), 1));
        List<UsersMeetings> usersWithConflict = allUsersMeetings.stream().filter(e -> conflictedMeetingsMap.containsKey(e.getMeetingId()) ? true : false)
                .collect(Collectors.toList());
        List<UUID> conflictedUserIds = usersWithConflict.stream().map(e -> e.getUserId()).collect(Collectors.toList());
        return conflictedUserIds;
    }


    public static List<MeetingSlot> getAvailableSlots(List<Meeting> user1Meetings, List<Meeting> user2Meetings) {
        List<MeetingSlot> meetingSlots = new ArrayList<>();
        Map<String, Boolean> timeMap = new HashMap();
        timeMap.put("00:30", false);
        timeMap.put("01:00", false);
        timeMap.put("01:30", false);
        timeMap.put("02:00", false);
        timeMap.put("02:30", false);
        timeMap.put("03:00", false);
        timeMap.put("03:30", false);
        timeMap.put("04:00", false);
        timeMap.put("04:30", false);
        timeMap.put("05:00", false);
        timeMap.put("05:30", false);
        timeMap.put("06:00", false);
        timeMap.put("06:30", false);
        timeMap.put("07:00", false);
        timeMap.put("07:30", false);
        timeMap.put("08:00", false);
        timeMap.put("08:30", false);
        timeMap.put("09:00", false);
        timeMap.put("09:30", false);
        timeMap.put("10:00", false);
        timeMap.put("10:30", false);
        timeMap.put("11:00", false);
        timeMap.put("11:30", false);
        timeMap.put("12:00", false);
        timeMap.put("12:30", false);
        timeMap.put("13:00", false);
        timeMap.put("13:30", false);
        timeMap.put("14:00", false);
        timeMap.put("14:30", false);
        timeMap.put("15:00", false);
        timeMap.put("15:30", false);
        timeMap.put("16:00", false);
        timeMap.put("16:30", false);
        timeMap.put("17:00", false);
        timeMap.put("17:30", false);
        timeMap.put("18:00", false);
        timeMap.put("18:30", false);
        timeMap.put("19:00", false);
        timeMap.put("19:30", false);
        timeMap.put("20:00", false);
        timeMap.put("20:30", false);
        timeMap.put("20:00", false);
        timeMap.put("21:30", false);
        timeMap.put("22:00", false);
        timeMap.put("22:30", false);
        timeMap.put("23:00", false);
        timeMap.put("23:30", false);
        timeMap.put("00:00", false);
        user1Meetings.forEach(e -> {
            String partition = e.getStartTime().toString().split(" ")[1];
            String[] time = partition.split(":");
            String key = time[0] + ":" + time[1];
            timeMap.put(key, true);
        });
        user2Meetings.forEach(e -> {
            String partition = e.getStartTime().toString().split(" ")[1];
            String[] time = partition.split(":");
            String key = time[0] + ":" + time[1];
            timeMap.put(key, true);
        });
        meetingSlots = RepoHelper.getAvailable(timeMap);
        return meetingSlots;
    }

    private static List<MeetingSlot> getAvailable(Map<String, Boolean> timeMap) {
        List<MeetingSlot> meetingSlots = new ArrayList<>();
        timeMap.keySet().forEach(e -> {
            boolean booked = timeMap.get(e);
            MeetingSlot meetingSlot = new MeetingSlot();
            if (!booked) {
                String hour = e.split(":")[0];
                String min = e.split(":")[1];
                if (Integer.parseInt(hour) == 23 && Integer.parseInt(min) == 59) {
                    meetingSlot.setStartTime("00:00");
                    meetingSlot.setEndTime("00:29");
                } else {
                    if (Integer.parseInt(min) == 30) {
                        min = "00";
                        hour = new String(Integer.toString(Integer.parseInt(hour) + 1));
                        meetingSlot.setStartTime(e);
                        meetingSlot.setEndTime(hour + ":" + min);
                    } else {
                        min = "30";
                        meetingSlot.setStartTime(e);
                        meetingSlot.setEndTime(hour + ":" + min);
                    }
                }
                meetingSlots.add(meetingSlot);
            }
        });
        return meetingSlots;
    }


}
