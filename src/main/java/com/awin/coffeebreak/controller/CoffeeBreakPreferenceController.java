package com.awin.coffeebreak.controller;

import com.awin.coffeebreak.dto.Preference;
import com.awin.coffeebreak.dto.Preferences;
import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import com.awin.coffeebreak.repository.StaffMemberRepository;
import com.awin.coffeebreak.services.CoffeeBreakService;
import com.awin.coffeebreak.services.ResponseConversionService;
import com.awin.coffeebreak.services.SlackNotifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;

@RestController
@AllArgsConstructor
public class CoffeeBreakPreferenceController {

    private ResponseConversionService responseConversionService;
    private CoffeeBreakService coffeeBreakService;


    /**
     * Publishes the list of preferences in the requested format
     */
    @GetMapping(path = "/today")
    public ResponseEntity<String> today(@RequestParam("format") String format) throws JsonProcessingException, JAXBException {

        String responseContent = "";
        String contentType = "text/html";

        Preferences preferences = Preferences.builder()
                .preference(coffeeBreakService.getTodayPreferences())
                .build();

        switch (format) {
            case "json":
                responseContent = responseConversionService.getJsonForResponse(preferences);
                contentType = "application/json";
                break;

            case "xml":
                responseContent = responseConversionService.getXmlForResponse(preferences);
                contentType = "text/xml";
                break;

            case "html":
                responseContent = responseConversionService.getHtmlForResponse(preferences);
        }

        return ResponseEntity.ok()
              .contentType(MediaType.valueOf(contentType))
              .body(responseContent);
    }

    @GetMapping("/notifyStaffMember")
    public ResponseEntity notifyStaffMember(@RequestParam("staffMemberId") int id) {
        coffeeBreakService.sendNotification(id);

        return ResponseEntity.ok().build();
    }


}
