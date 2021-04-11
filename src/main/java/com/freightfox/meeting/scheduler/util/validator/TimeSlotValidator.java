package com.freightfox.meeting.scheduler.util.validator;

import com.freightfox.meeting.scheduler.constants.ApplicationConstants;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component()
public class TimeSlotValidator{

    public boolean validate(Instant s, Instant e) {
        long end = e.toEpochMilli();
        long start = s.toEpochMilli();
        long milis = e.minusMillis(s.toEpochMilli()).toEpochMilli();
        return e.minusMillis(s.toEpochMilli()).toEpochMilli()== ApplicationConstants.halfHourMeet ?true:false;
    }
}
