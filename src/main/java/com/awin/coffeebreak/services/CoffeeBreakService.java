package com.awin.coffeebreak.services;

import com.awin.coffeebreak.dto.Preference;
import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import com.awin.coffeebreak.repository.CoffeeBreakPreferenceRepository;
import com.awin.coffeebreak.repository.StaffMemberRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CoffeeBreakService {

    private CoffeeBreakPreferenceRepository coffeeBreakPreferenceRepository;
    private StaffMemberRepository staffMemberRepository;
    private EmailNotifier emailNotifier;
    private SlackNotifier slackNotifier;


    public List<Preference> getTodayPreferences() {

        return convertEntities(coffeeBreakPreferenceRepository.getPreferencesForToday(
                Instant.now().truncatedTo(ChronoUnit.DAYS),
                Instant.now().truncatedTo(ChronoUnit.DAYS).plus(1, ChronoUnit.DAYS)));
    }

    public boolean notifyStaffMember(final StaffMember staffMember, final List<CoffeeBreakPreference> preferences) {

        if (Strings.isNotBlank(staffMember.getSlackIdentifier())) {

        }

        return true;
    }

    public void sendNotification(Integer staffMemberId){
        Optional<StaffMember> staffMember = this.staffMemberRepository.findById(staffMemberId);

        if(staffMember.isPresent()){
            if(Strings.isNotBlank(staffMember.get().getSlackIdentifier())){
                slackNotifier.sendSlackNotification(staffMember.get());
                return;
            }
            emailNotifier.sendEmail(staffMember.get());
        }
    }

    private List<Preference> convertEntities(List<CoffeeBreakPreference> coffeeBreakPreferences) {
        return coffeeBreakPreferences.stream().map(preference ->
                Preference.builder()
                        .type(preference.getType())
                        .subType(preference.getSubType())
                        .requestedBy(preference.getRequestedBy().getName())
                        .details(preference.getDetails())
                    .build()
        ).collect(Collectors.toList());
    }





}
