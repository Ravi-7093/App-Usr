package com.rdev.userregistery.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rdev.userregistery.entity.RegistryUsers;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) /*Do not include null values */
@JsonIgnoreProperties(ignoreUnknown = false) /*Validate if any unknown property not available in Java class*/

//DTO for handling requests and responses
public class ReqResp {
    private int statusCode;
    private String message;
    private String email;
    private String error;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String  city;
    private String password;
    private String role;
    private String token;
    private RegistryUsers registryUsers;
    private List<RegistryUsers> registryUsersList;
}
