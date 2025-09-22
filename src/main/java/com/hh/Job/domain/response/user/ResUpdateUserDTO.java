package com.hh.Job.domain.response.user;


import com.hh.Job.domain.constant.GenderEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ResUpdateUserDTO {
    private long id;
    private String name;
    private GenderEnum gender;
    private String address;
    private int age;
    private Instant updatedAt;
    private CompanyUser companyUser;

    @Getter
    @Setter
    public static class CompanyUser {
        private long id;
        private String name;
    }
}
