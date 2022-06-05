package com.awin.coffeebreak.entity;

import com.awin.coffeebreak.dto.SubType;
import com.awin.coffeebreak.dto.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@Table(name = "order_data")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    private Integer id;
    @Column
    private Type type;
    @Column
    private SubType subType;

    @ManyToOne
    @JoinColumn(name="preference_id", referencedColumnName="id")
    private CoffeeBreakPreference preferenceId;

    @ElementCollection
    @CollectionTable(name = "order_details_mapping",
            joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    private Map<String, String> details = new HashMap<>();
}
