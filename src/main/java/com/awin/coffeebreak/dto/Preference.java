package com.awin.coffeebreak.dto;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Preference {
    private Type type;
    private String subType;
    private String requestedBy;
    private Map<String, String> details;

}
