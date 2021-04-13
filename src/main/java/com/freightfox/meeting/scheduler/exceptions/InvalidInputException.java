package com.freightfox.meeting.scheduler.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvalidInputException extends Exception{
    String errorMessage = null;
    public InvalidInputException(String msg){
        super(msg);
        errorMessage = msg;
    }
}
