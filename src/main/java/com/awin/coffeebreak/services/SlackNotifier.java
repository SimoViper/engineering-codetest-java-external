package com.awin.coffeebreak.services;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SlackNotifier {

    /**
     * Imagine that this method:
     * Sends a notification to the user on Slack that their coffee break refreshment today will be $preference
     * returns true of false status of notification sent
     */
    public void sendSlackNotification(final StaffMember staffMember) {

    }

}
