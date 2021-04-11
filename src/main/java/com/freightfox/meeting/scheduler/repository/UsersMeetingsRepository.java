package com.freightfox.meeting.scheduler.repository;

import com.freightfox.meeting.scheduler.entity.UsersMeetings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UsersMeetingsRepository extends JpaRepository<UsersMeetings, UUID> {

    List<UsersMeetings> findByUserIdIn(List<UUID> users);
}
