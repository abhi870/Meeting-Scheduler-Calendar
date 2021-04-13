package com.freightfox.meeting.scheduler.controller;

import com.freightfox.meeting.scheduler.model.AppUser;
import com.freightfox.meeting.scheduler.model.FreeSlotInput;
import com.freightfox.meeting.scheduler.service.UserService;
import com.freightfox.meeting.scheduler.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private UserService userService;

    @PostMapping
    public AppUser createUser(@RequestBody AppUser user){
        return userService.createUser(user);
    }


}
