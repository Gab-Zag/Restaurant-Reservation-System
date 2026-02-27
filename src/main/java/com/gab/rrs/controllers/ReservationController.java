package com.gab.rrs.controllers;

import com.gab.rrs.dtos.reservation.ReservationTableDTO;
import com.gab.rrs.services.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
public class ReservationController {
    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @PostMapping()
    public ResponseEntity<String> reserva(@RequestBody ReservationTableDTO dto){
        return ResponseEntity.ok(reservationService.reservationTable(dto));
    }

    @GetMapping()
    public ResponseEntity getReserva(){
        return ResponseEntity.ok(reservationService.getAll());
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<String> cancelReserva(@PathVariable Integer id){
        return ResponseEntity.ok(reservationService.cancel(id));
    }
}
