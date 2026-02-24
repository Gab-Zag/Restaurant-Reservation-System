package com.gab.rrs.repository;

import com.gab.rrs.entities.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
}
