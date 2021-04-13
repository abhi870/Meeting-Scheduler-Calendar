package com.freightfox.meeting.scheduler.repository;

import com.freightfox.meeting.scheduler.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MeetingsRepository extends JpaRepository<Meeting, UUID> {

    List<Meeting> findByMeetingIdIn(List<UUID> ids);

}
