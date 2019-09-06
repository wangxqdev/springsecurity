package com.tec.anji.www.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class UserNotExistsException extends RuntimeException {

    private String id;

    public UserNotExistsException(String id) {
        super("User not exists");
        this.id = id;
    }
}
