package com.freightfox.meeting.scheduler.constants;

import org.springframework.stereotype.Component;

@Component
public class ApplicationConstants {
    public static final long halfHourMeet = 1800000;
    public static final String CONFLICTED_USERS = "users with meeting conflict";

    public static final String FAILURE = "Failed";
    public static final String SUCCESS = "Success";
}
