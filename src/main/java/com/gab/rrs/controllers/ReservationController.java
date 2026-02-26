package com.gab.rrs.controllers;

import com.gab.rrs.dtos.reservation.ReservationTableDTO;
import com.gab.rrs.services.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReservationController {
    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @PostMapping("/reservas")
    public ResponseEntity<String> reserva(@RequestBody ReservationTableDTO dto){
        String confirmed = reservationService.reservationTable(dto);
        return ResponseEntity.ok(confirmed);
    }

    @GetMapping("/reservas")
    public ResponseEntity getReserva(){
        return ResponseEntity.ok(reservationService.getAll());
    }

    @PatchMapping("/reservas/{id}/cancelar")
    public ResponseEntity<String> cancelReserva(@PathVariable Integer id){
        String confirmed = reservationService.cancel(id);
        return ResponseEntity.ok(confirmed);
    }
}
