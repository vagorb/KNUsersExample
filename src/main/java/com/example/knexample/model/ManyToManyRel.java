package com.example.knexample.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ManyToManyRel {
    //Unidirectional Many-to-Many Associations tables: many_to_many_product ManyToManyRel!!!
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @JoinTable(name = "many_to_many_product",
                joinColumns = { @JoinColumn(name = "fk_many_to_many_rel") },
                inverseJoinColumns = { @JoinColumn(name = "fk_user") })
        private Set<User> products = new HashSet<User>();

}
