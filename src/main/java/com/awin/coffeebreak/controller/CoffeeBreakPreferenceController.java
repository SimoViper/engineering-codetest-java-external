package com.awin.coffeebreak.controller;

import com.awin.coffeebreak.dto.Preferences;
import com.awin.coffeebreak.service.CoffeeBreakService;
import com.awin.coffeebreak.service.ResponseConversionService;

import com.fasterxml.jackson.core.JsonProcessingException;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void notifyStaffMember(@RequestParam("staffMemberId") int id) throws NotFoundException {
        coffeeBreakService.sendNotification(id);
    }


}
