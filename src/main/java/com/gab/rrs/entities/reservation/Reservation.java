package com.gab.rrs.entities.reservation;

import com.gab.rrs.entities.tables.Tables;
import com.gab.rrs.entities.users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "reservation")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private Tables tables;

    private LocalDate reservation_data;

    @Enumerated(EnumType.STRING)
    private ReservationType status;
}
