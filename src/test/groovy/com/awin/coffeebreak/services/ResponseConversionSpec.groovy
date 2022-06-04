package com.awin.coffeebreak.services

import com.awin.coffeebreak.dto.Order
import com.awin.coffeebreak.dto.Preference
import com.awin.coffeebreak.dto.Preferences
import com.awin.coffeebreak.service.ResponseConversionService
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class ResponseConversionSpec extends Specification {

    def "testXmlResponse"() {
        given:
        def preferences = Preferences.builder()
                .preference(Collections.singletonList(Preference.builder()
                        .requestedBy("Staff Member")
                        .build()))
                .build();

        def responseConversionService = new ResponseConversionService(new ObjectMapper());

        when:
        def xml = responseConversionService.getXmlForResponse(preferences);

        then:
        assertThat(xml).contains("<requestedBy>Staff Member</requestedBy>");
    }

    def "testJsonResponse"() {
        given:
        def preferences = Preferences.builder()
                .preference(Collections.singletonList(Preference.builder()
                        .requestedBy("Staff Member")
                        .build()))
                .build();

        def responseConversionService = new ResponseConversionService(new ObjectMapper());

        when:
        def json = responseConversionService.getJsonForResponse(preferences);

        then:
        assertThat(json).contains("\"requestedBy\":\"Staff Member\"");
    }

    def "testHtmlResponse"() {
        given:
        def preferences = Preferences.builder()
                .preference(Collections.singletonList(Preference.builder()
                        .requestedBy("Staff Member")
                        .orders(new ArrayList<Order>())
                        .build()))
                .build();

        def responseConversionService = new ResponseConversionService(new ObjectMapper());

        when:
        def html = responseConversionService.getHtmlForResponse(preferences);

        then:
        assertThat(html).contains("label style=\"font-weight: bold; \">RequestedBy: </label>Staff Member");
    }
}
