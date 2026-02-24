package com.gab.rrs.entities.tables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tables")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tables {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private TablesType status;
}
