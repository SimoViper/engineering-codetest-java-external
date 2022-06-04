package com.awin.coffeebreak.entity;

import com.awin.coffeebreak.dto.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @OneToMany(mappedBy = "preferenceId")
    private List<Order> orders;

    @ManyToOne
    @JoinColumn(name="requested_by_id", referencedColumnName="id")
    private StaffMember requestedBy;

    @Column
    private Instant requestedDate;

}
