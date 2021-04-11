package com.freightfox.meeting.scheduler.service;

import com.freightfox.meeting.scheduler.model.MeetingModel;
import com.freightfox.meeting.scheduler.model.ReturnDetails;

import java.util.List;
import java.util.UUID;

public interface MeetingSchedulerService {
    ReturnDetails scheduleMeeting(MeetingModel meetingModel);
}
