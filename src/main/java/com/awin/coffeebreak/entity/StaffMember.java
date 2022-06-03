package com.awin.coffeebreak.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "staff_member")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StaffMember {

    @Id
    Integer id;

    @Column
    String name;

    @Column
    String email;

    @Column
    String slackIdentifier;

    @OneToMany(mappedBy="requestedBy")
    List<CoffeeBreakPreference> coffeeBreakPreferences;

}
