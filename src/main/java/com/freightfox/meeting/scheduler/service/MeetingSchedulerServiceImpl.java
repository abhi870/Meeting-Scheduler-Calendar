package com.freightfox.meeting.scheduler.service;

import com.freightfox.meeting.scheduler.constants.ApplicationConstants;
import com.freightfox.meeting.scheduler.entity.Meeting;
import com.freightfox.meeting.scheduler.entity.UsersMeetings;
import com.freightfox.meeting.scheduler.exceptions.InvalidInputException;
import com.freightfox.meeting.scheduler.model.FreeSlotInput;
import com.freightfox.meeting.scheduler.model.MeetingModel;
import com.freightfox.meeting.scheduler.model.MeetingSlot;
import com.freightfox.meeting.scheduler.model.ReturnDetails;
import com.freightfox.meeting.scheduler.repository.MeetingsRepository;
import com.freightfox.meeting.scheduler.repository.UserRepository;
import com.freightfox.meeting.scheduler.repository.UsersMeetingsRepository;
import com.freightfox.meeting.scheduler.util.helper.RepoHelper;
import com.freightfox.meeting.scheduler.util.validator.TimeSlotValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MeetingSchedulerServiceImpl implements MeetingSchedulerService {

    @Autowired
    private TimeSlotValidator validator;

    @Autowired
    private UsersMeetingsRepository usersMeetingsRepository;

    @Autowired
    private MeetingsRepository meetingsRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ReturnDetails scheduleMeeting(MeetingModel meetingModel) throws Exception {
        ReturnDetails returnDetails = new ReturnDetails();
        List<UUID> conflictedUsers = null;
        Timestamp startTime = Timestamp.valueOf(LocalDateTime.ofInstant(meetingModel.getStartTime(), ZoneOffset.UTC));
        Timestamp endTime = Timestamp.valueOf(LocalDateTime.ofInstant(meetingModel.getEndTime(), ZoneOffset.UTC));

        conflictedUsers = checkConflict(meetingModel, startTime, endTime);

        if (conflictedUsers.size() == 0) {
            returnDetails = saveMeetingDetails(meetingModel, conflictedUsers, startTime, endTime);
        } else {
            returnDetails.setConflictedUsers(conflictedUsers);
            returnDetails.setStatus(ApplicationConstants.FAILURE);
        }
        return returnDetails;
    }

    @Override
    public List<MeetingSlot> getAvailableSlots(FreeSlotInput input) {
        List<UUID> user1MeetingIds = usersMeetingsRepository.findAllByUserId(input.getUser1()).stream().map(e->e.getMeetingId()).collect(Collectors.toList());
       List<Meeting> user1Meetings  = meetingsRepository.findByMeetingIdIn(user1MeetingIds);

       List<UUID> user2MeetingIds =  usersMeetingsRepository.findAllByUserId(input.getUser2()).stream().map(e->e.getMeetingId()).collect(Collectors.toList());
       List<Meeting> user2Meetings = meetingsRepository.findByMeetingIdIn(user2MeetingIds);

       List<MeetingSlot> availableMeetingSlots = RepoHelper.getAvailableSlots(user1Meetings, user2Meetings);
       availableMeetingSlots.sort((x,y)->x.getStartTime().compareTo(y.getStartTime()));
       return availableMeetingSlots;
    }

    @Override
    public List<UUID> checkConflict(MeetingModel meetingModel, Timestamp startTime, Timestamp endTime) throws Exception {
        List<UUID> conflictedUsers = new ArrayList<>();
        if (validator.validate(meetingModel.getStartTime(), meetingModel.getEndTime())) {

            List<UsersMeetings> allUsersMeetings = usersMeetingsRepository.findByUserIdIn(meetingModel.getInvitee());
            List<UUID> meetingIds = RepoHelper.getAllMeetings(allUsersMeetings);
            List<Meeting> meetings = meetingsRepository.findByMeetingIdIn(meetingIds);
            List<Meeting> conflictedMeetings = RepoHelper.checkConflict(meetings, startTime, endTime);
            conflictedUsers = RepoHelper.getConflictInMeetingUsers(allUsersMeetings, conflictedMeetings);
        } else
            throw new InvalidInputException("Invalid Meeting slot");
        return conflictedUsers;
    }

    private ReturnDetails saveMeetingDetails(MeetingModel meetingModel, List<UUID> conflictedUsers, Timestamp startTime, Timestamp endTime) {
        ReturnDetails returnDetails = new ReturnDetails();
        boolean ifEveryUserExists = true;
        for (UUID userId : meetingModel.getInvitee()) {
            if (userRepository.findById(userId).isEmpty()) {
                ifEveryUserExists = false;
                break;
            }
        }
        if (ifEveryUserExists) {
            Meeting meeting = new Meeting();
            meeting.setStartTime(startTime);
            meeting.setEndTime(endTime);
            meetingsRepository.save(meeting);
            for (UUID userId : meetingModel.getInvitee()) {
                UsersMeetings usersMeetings = new UsersMeetings();
                usersMeetings.setMeetingId(meeting.getMeetingId());
                usersMeetings.setUserId(userId);
                usersMeetingsRepository.save(usersMeetings);
            }
            returnDetails.setStatus(ApplicationConstants.SUCCESS);
            returnDetails.setConflictedUsers(conflictedUsers);
        }
        return returnDetails;
    }


}

