package com.awin.coffeebreak.entity;

import com.awin.coffeebreak.dto.Type;

import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.*;

@Entity
@Table(name = "coffee_break_preference")
public class CoffeeBreakPreference {

    @Id
    Integer id;

    @Column
    Type type;

    @Column
    String subType;

    @ManyToOne
    StaffMember requestedBy;

    @Column
    Instant requestedDate;

    @ElementCollection
    @CollectionTable(name = "order_details_mapping",
            joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "item_name")
    @Column(name = "details")
    Map<String, String> details = new HashMap<>();


    public Type getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(final String subType) {
        this.subType = subType;
    }

    public StaffMember getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(final StaffMember requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Instant getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(final Instant requestedDate) {
        this.requestedDate = requestedDate;
    }

/*    public void setDetails(final Map<String, String> details) {
        this.details = details;
    }

    public Map<String, String> getDetails() {
        return details;
    }*/

    public String getAsJson() {
        return "{" +
              "\"id\":" + id +
              ", \"type\":\"" + type + '"' +
              ", \"subType\":\"" + subType + '"' +
              ", \"requestedBy\":\"" + requestedBy + '"' +
              ", \"requestedDate\":\"" + requestedDate + '"' +
              ", \"details\":\"" + details + '"' +
              '}';
    }

    public String getAsXml() {
        return "<preference type=\""+type+"\" subtype=\""+subType+"\">" +
              "<requestedBy>"+requestedBy+"</requestedBy>" +
              "<details>"+details+"</details>" +
              "</preference>";
    }

    public String getAsListElement() {
        final String detailsString = details.keySet().stream()
              .map(e -> e + " : " + details.get(e))
              .collect(Collectors.joining(","));

        return "<li>" + requestedBy.getName() + " would like a " + subType + ". (" + detailsString + ")</li>";
    }
}
