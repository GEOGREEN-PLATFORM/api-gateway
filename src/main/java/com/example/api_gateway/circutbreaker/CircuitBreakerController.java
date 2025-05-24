package com.example.api_gateway.circutbreaker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CircuitBreakerController {
    @RequestMapping(path = "/fallback", method = {RequestMethod.PUT, RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<ApiGatewayError> fallback() {
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        return new ResponseEntity<>(
                new ApiGatewayError("Service Unavailable", status.value()),
                status);
    }
}
