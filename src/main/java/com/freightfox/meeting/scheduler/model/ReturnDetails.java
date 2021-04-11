package com.freightfox.meeting.scheduler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnDetails {
    private List<UUID> conflictedUsers;
    private String status;
}
