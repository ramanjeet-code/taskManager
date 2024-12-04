package com.taskManager.utility;

import lombok.Data;

@Data
public class JwtResponse {
    private String email;
    private String jwtToken;


}
