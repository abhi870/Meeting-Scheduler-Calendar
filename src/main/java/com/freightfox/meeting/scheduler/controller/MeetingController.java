package com.freightfox.meeting.scheduler.controller;

import com.freightfox.meeting.scheduler.constants.ApplicationConstants;
import com.freightfox.meeting.scheduler.model.FreeSlotInput;
import com.freightfox.meeting.scheduler.model.MeetingModel;
import com.freightfox.meeting.scheduler.model.MeetingSlot;
import com.freightfox.meeting.scheduler.model.ReturnDetails;
import com.freightfox.meeting.scheduler.service.MeetingSchedulerService;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
    private MeetingSchedulerService meetingSchedulerService;

    /*
    fist will check for conflicts then
    schedule meeting, with list of invitees, can schedule meeting for all the users in the list
    returns : conflict in meeting users and status
    */
    @PostMapping
    public ReturnDetails scheduleMeeting(@RequestBody MeetingModel meetingModelToBeScheduled) throws Exception {
        return meetingSchedulerService.scheduleMeeting(meetingModelToBeScheduled);
    }

    /*
    returns: availability slots of 2 users
     */
    @PostMapping("/availability")
    public List<MeetingSlot> getFreeSlots(@RequestBody FreeSlotInput input) {
        return meetingSchedulerService.getAvailableSlots(input);
    }

    /*
    returns: conflict in meeting users list
     */
    @PostMapping("/validate")
    public ReturnDetails checkUsersWithConflict(@RequestBody MeetingModel meetingModel) throws Exception {
        List<UUID> conflictedUsers = meetingSchedulerService.checkConflict(
                meetingModel,
                Timestamp.valueOf(LocalDateTime.ofInstant(meetingModel.getStartTime(), ZoneOffset.UTC)),
                Timestamp.valueOf(LocalDateTime.ofInstant(meetingModel.getEndTime(), ZoneOffset.UTC)));
        return new ReturnDetails(conflictedUsers, ApplicationConstants.CONFLICTED_USERS);
    }
}
