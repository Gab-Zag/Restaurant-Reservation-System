package com.gab.rrs.services;

import com.gab.rrs.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }
}
