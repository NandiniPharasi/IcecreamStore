package com.demo.security.config.client;


import com.demo.security.config.dto.UsernameAuthoritiesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

// REST endpoint definitions used to connect to the security microservice.
 
@FeignClient(name="securityServer", value = "securityServer", url = "${apiGateway.authService.base}")
public interface SecurityServerRestClient {

    @PostMapping(value = "/check_token", produces = APPLICATION_JSON_VALUE)
    UsernameAuthoritiesDto checkToken(@RequestParam(value="token") String token, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authHeader);

}
