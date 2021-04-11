package com.freightfox.meeting.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@SpringBootApplication
@EnableJpaRepositories
public class MeetingSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetingSchedulerApplication.class, args);
		Instant s = LocalDateTime.of(2021, 4, 3, 5, 0).toInstant(ZoneOffset.UTC);
		Instant e = LocalDateTime.of(2021, 4, 3, 5, 30).toInstant(ZoneOffset.UTC);
		System.out.println(s.compareTo(e));
	}

}
