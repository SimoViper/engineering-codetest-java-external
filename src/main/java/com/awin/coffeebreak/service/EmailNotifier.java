package com.awin.coffeebreak.service;

import com.awin.coffeebreak.entity.StaffMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailNotifier {

    private static final String EMAIL_NOTIFICATION_SENT = "An email has been sent";

    public void sendEmail(final StaffMember staffMember) {
        //TODO implementation and error handling for Email notifications.
        log.info(EMAIL_NOTIFICATION_SENT);

    }
}
