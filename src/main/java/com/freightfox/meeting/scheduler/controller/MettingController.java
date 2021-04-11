package com.freightfox.meeting.scheduler.controller;

import com.freightfox.meeting.scheduler.model.MeetingModel;
import com.freightfox.meeting.scheduler.model.ReturnDetails;
import com.freightfox.meeting.scheduler.service.MeetingSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/meeting")
public class MettingController {

    @Autowired
    private MeetingSchedulerService meetingSchedulerService;

    @PostMapping
    public ReturnDetails scheduleMeeting(@RequestBody MeetingModel meetingModelToBeScheduled) {
        return meetingSchedulerService.scheduleMeeting(meetingModelToBeScheduled);
    }

}
