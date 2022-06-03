package com.awin.coffeebreak.services;


import com.awin.coffeebreak.dto.Preference;
import com.awin.coffeebreak.dto.Preferences;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResponseConversionService {

    private final ObjectMapper objectMapper;

    public String getJsonForResponse(final Preferences preferences) throws JsonProcessingException {
        return objectMapper.writeValueAsString(preferences);
    }

    public String getXmlForResponse(Preferences preferences) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Preferences.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(preferences, sw);
        return sw.toString();
    }

    public String getHtmlForResponse(final Preferences preferences) {
        String responseJson = "<ul>";

        for (final Preference preference : preferences.getPreference()) {
            responseJson += getAsListElement(preference);
        }

        return responseJson + "</ul>";
    }

    private String getAsListElement(Preference preference) {
        final String detailsString = preference.getDetails().keySet().stream()
                .map(e -> e + " : " + preference.getDetails().get(e))
                .collect(Collectors.joining(","));

        return "<li>" + preference.getRequestedBy() + " would like a " + preference.getSubType() + ". (" + detailsString + ")</li>";
    }
}
