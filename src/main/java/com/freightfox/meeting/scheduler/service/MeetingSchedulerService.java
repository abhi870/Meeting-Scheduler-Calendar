package com.freightfox.meeting.scheduler.service;

import com.freightfox.meeting.scheduler.model.FreeSlotInput;
import com.freightfox.meeting.scheduler.model.MeetingModel;
import com.freightfox.meeting.scheduler.model.MeetingSlot;
import com.freightfox.meeting.scheduler.model.ReturnDetails;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface MeetingSchedulerService {
    ReturnDetails scheduleMeeting(MeetingModel meetingModel) throws Exception;

    List<MeetingSlot> getAvailableSlots(FreeSlotInput input);

    List<UUID> checkConflict(MeetingModel meetingModel, Timestamp startTime, Timestamp endTime) throws Exception;

    }
