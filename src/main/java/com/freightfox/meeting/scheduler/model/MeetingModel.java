package com.freightfox.meeting.scheduler.model;

import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeetingModel {
    private Instant startTime;
    private Instant endTime;
    private List<UUID> invitee;
}
