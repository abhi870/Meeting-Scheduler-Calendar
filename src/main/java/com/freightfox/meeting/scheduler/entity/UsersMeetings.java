package com.freightfox.meeting.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users_meetings")
public class UsersMeetings {
    @Id
    @Column(name = "composite_id")
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    private UUID compositeId;

    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "meeting_id")
    private UUID meetingId;
}
