package com.awin.coffeebreak.service;

import com.awin.coffeebreak.entity.StaffMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SlackNotifier {

    private static final String SLACK_NOTIFICATION_SENT = "A Slack message has been sent";

    /**
     * Imagine that this method:
     * Sends a notification to the user on Slack that their coffee break refreshment today will be $preference
     * returns true of false status of notification sent

     I changed the method to void because I intend to use the controller advice class and exceptions
     to manage errors. Preferences aren't sent
     as parameter because they're contained in the StaffMember object.
     */
    public void sendSlackNotification(final StaffMember staffMember) {
        //TODO implementation and error handling for Slack notifications
        log.info(SLACK_NOTIFICATION_SENT);
    }

}
