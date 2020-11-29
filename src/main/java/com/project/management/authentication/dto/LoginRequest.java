package com.project.management.authentication.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
public class LoginRequest {

    @ApiModelProperty(value = "unique username",required = true)
    private String userName;

    @ApiModelProperty(value = "user password",required = true)
    private String password;
}
