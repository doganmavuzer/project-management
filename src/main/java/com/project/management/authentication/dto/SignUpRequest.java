package com.project.management.authentication.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
public class SignUpRequest {

    private String userName;

    private String fullName;

    private String email;

    private String password;

}
