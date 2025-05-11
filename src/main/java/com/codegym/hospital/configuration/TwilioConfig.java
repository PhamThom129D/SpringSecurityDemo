package com.codegym.hospital.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;
import com.twilio.Twilio;


@Configuration
public class TwilioConfig {

    @Value("${twilio.account_sid}")
    private String accountSid;

    @Value("${twilio.auth_token}")
    private String authToken;

    @Value("${twilio.service_sid}")
    private String serviceSid;

    @PostConstruct
    public void init() {
        if (accountSid == null || authToken == null || serviceSid == null) {
            System.err.println("Error: Missing Twilio configuration values.");
        } else {
            System.out.println("Twilio config: " + accountSid + " | " + authToken + " | " + serviceSid);
            Twilio.init(accountSid, authToken);
        }
    }
}
