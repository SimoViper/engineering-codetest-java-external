package com.awin.coffeebreak.service;

import com.awin.coffeebreak.dto.Preferences;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.H4;
import com.hp.gagawa.java.elements.Label;
import com.hp.gagawa.java.elements.P;
import com.hp.gagawa.java.elements.Table;
import com.hp.gagawa.java.elements.Text;
import com.hp.gagawa.java.elements.Tr;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResponseConversionService {

    private static final String DELIMITER = " : ";
    private static final String SUB_TYPE = " Sub Type: ";
    private static final String DETAILS = " Details: ";
    private static final String ORDERS = "Orders: ";
    private static final String REQUESTED_BY = "RequestedBy: ";
    private static final String FONT_WEIGHT_BOLD = "font-weight: bold; ";
    private static final String TYPE = " Type: ";

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

    public String getHtmlForResponse(Preferences preferences){
        Div mainDiv = new Div();
        preferences.getPreference().forEach(preference -> {
            Div preferenceDiv = new Div();
            preferenceDiv.appendChild(new P()
                    .appendChild(new Label().appendText(REQUESTED_BY).setStyle(FONT_WEIGHT_BOLD))
                    .appendChild(new Text(preference.getRequestedBy()))
                    .appendChild(new H4().appendText(ORDERS)));
            preference.getOrders().stream().forEach(order -> {
                Div orderDiv = new Div();
                orderDiv.appendChild(new Table().appendChild(new Tr().appendText(TYPE + order.getType()))
                        .appendChild(new Tr().appendText(SUB_TYPE + order.getSubType()))
                        .appendChild(new Tr().appendText(DETAILS + getDetailsString(order))));

                preferenceDiv.appendChild(orderDiv);
            });
            mainDiv.appendChild(preferenceDiv);
        });

        return mainDiv.write();
    }

    private String getDetailsString(com.awin.coffeebreak.dto.Order order) {
        return order.getDetails().keySet().stream()
                .map(e -> e + DELIMITER + order.getDetails().get(e))
                .collect(Collectors.joining(","));
    }
}
