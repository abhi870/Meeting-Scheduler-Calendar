package com.freightfox.meeting.scheduler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreeSlotInput {
    private UUID user1;
    private UUID user2;
}
