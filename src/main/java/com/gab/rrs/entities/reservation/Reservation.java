package com.gab.rrs.entities.reservation;

import com.gab.rrs.entities.tables.Tables;
import com.gab.rrs.entities.users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @Column(name = "reservation_data")
    private LocalDateTime reservation_data;

    @Enumerated(EnumType.STRING)
    private ReservationType status;

    public Reservation(Users users, Tables tables, LocalDateTime reservation_data, ReservationType status){
        this.users = users;
        this.tables = tables;
        this.reservation_data = reservation_data;
        this.status = status;
    }

}
