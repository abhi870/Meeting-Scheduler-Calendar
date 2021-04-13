package com.freightfox.meeting.scheduler.repository;

import com.freightfox.meeting.scheduler.entity.UsersMeetings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Queue;
import java.util.UUID;

@Repository
public interface UsersMeetingsRepository extends JpaRepository<UsersMeetings, UUID> {

    List<UsersMeetings> findByUserIdIn(List<UUID> users);

    List<UsersMeetings> findAllByUserId(UUID userId);
}
