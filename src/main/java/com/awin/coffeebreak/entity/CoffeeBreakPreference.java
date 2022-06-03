package com.awin.coffeebreak.entity;

import com.awin.coffeebreak.dto.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

@Data
@Entity
@Table(name = "coffee_break_preference")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CoffeeBreakPreference {

    @Id
    private Integer id;

    @Column
    private Type type;

    @Column
    private String subType;

    @ManyToOne
    @JoinColumn(name="requested_by_id", referencedColumnName="id")
    private StaffMember requestedBy;

    @Column
    private Instant requestedDate;

    @ElementCollection
    @CollectionTable(name = "preference_details_mapping",
            joinColumns = {@JoinColumn(name = "preference_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    private Map<String, String> details = new HashMap<>();
}
