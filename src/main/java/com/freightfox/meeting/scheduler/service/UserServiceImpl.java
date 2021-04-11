package com.freightfox.meeting.scheduler.service;

import com.freightfox.meeting.scheduler.entity.User;
import com.freightfox.meeting.scheduler.model.AppUser;
import com.freightfox.meeting.scheduler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public AppUser createUser(AppUser user){
        User userEntity = new User();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userRepository.save(userEntity);
        return user;
    }
}
