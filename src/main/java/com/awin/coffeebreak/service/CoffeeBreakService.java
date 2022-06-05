package com.awin.coffeebreak.service;

import com.awin.coffeebreak.dto.ErrorResponse;
import com.awin.coffeebreak.dto.Format;
import com.awin.coffeebreak.dto.Order;
import com.awin.coffeebreak.dto.Preference;
import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import com.awin.coffeebreak.errorhandling.ValidationErrorException;
import com.awin.coffeebreak.repository.CoffeeBreakPreferenceRepository;
import com.awin.coffeebreak.repository.StaffMemberRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.awin.coffeebreak.dto.ErrorCode.VALIDATION_ERROR;

@Slf4j
@Service
@AllArgsConstructor
public class CoffeeBreakService {

    private static final String USER_NOT_FOUND = "User Not Found";
    public static final String INVALID_FORMAT_MESSAGE = "Invalid format; accepted formats are: json, xml and html";

    private CoffeeBreakPreferenceRepository coffeeBreakPreferenceRepository;
    private StaffMemberRepository staffMemberRepository;
    private EmailNotifier emailNotifier;
    private SlackNotifier slackNotifier;


    public List<Preference> getTodayPreferences() {

        return convertEntities(coffeeBreakPreferenceRepository.getPreferencesForToday(
                Instant.now().truncatedTo(ChronoUnit.DAYS),
                Instant.now().truncatedTo(ChronoUnit.DAYS).plus(1, ChronoUnit.DAYS)));
    }

    //no need for this method to return anything since errors are managed through exceptions handling
    public void notifyStaffMember(Integer staffMemberId) throws NotFoundException {
        Optional<StaffMember> staffMember = this.staffMemberRepository.findById(staffMemberId);

        if (staffMember.isEmpty()) {
            throw new NotFoundException(USER_NOT_FOUND);
        }

        //If the Slack Id is present the notification is sent using the messenger
        if (Strings.isNotBlank(staffMember.get().getSlackIdentifier())) {
            slackNotifier.sendSlackNotification(staffMember.get());
            return;
        }
        //otherwise an email will be sent (email is mandatory)
        emailNotifier.sendEmail(staffMember.get());

    }

    public void validateRequestFormat(String format) throws ValidationErrorException {
        if (!validateFormat(format)) {
            log.error(INVALID_FORMAT_MESSAGE);
            throw new ValidationErrorException(ErrorResponse.builder()
                    .errorCode(VALIDATION_ERROR)
                    .errorMessage(INVALID_FORMAT_MESSAGE)
                    .build());
        }
    }

    private boolean validateFormat(String format) {
        for (Format value : Format.values()) {
            if (value.getAllowedFormats().equals(format)) {
                return true;
            }
        }
        return false;
    }

    private List<Preference> convertEntities(List<CoffeeBreakPreference> coffeeBreakPreferences) {
        return coffeeBreakPreferences.stream().map(preference ->
                Preference.builder()
                        .requestedBy(preference.getRequestedBy().getName())
                        .orders(preference.getOrders().stream().map(order ->
                                Order.builder()
                                        .details(order.getDetails())
                                        .type(order.getType())
                                        .subType(order.getSubType())
                                        .build()
                        ).collect(Collectors.toList()))
                        .build()
        ).collect(Collectors.toList());
    }


}
