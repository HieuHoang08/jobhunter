package com.hh.Job.domain.response;

import com.hh.Job.util.constant.GenderEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ResCreateUserDTO {

    private long id;
    private String name;
    private String email;
    private String address;
    private GenderEnum gender;
    private int age;
    private Instant createdAt;
}
