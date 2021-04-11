package com.freightfox.meeting.scheduler.service;

import com.freightfox.meeting.scheduler.model.AppUser;
import org.springframework.stereotype.Component;


public interface UserService {
    AppUser createUser(AppUser appUser);
}
