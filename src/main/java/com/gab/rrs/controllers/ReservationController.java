package com.gab.rrs.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RelationService;

@RestController
public class ReservationController {
    private RelationService relationService;

    @PostMapping("/reservas")
    public ResponseEntity<Void> reserva(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/resevas")
    public ResponseEntity<Void> getReserva(){
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/reservas/{id}/cancelar")
    public ResponseEntity<Void> cancelReserva(@PathVariable Integer id){
        return ResponseEntity.ok().build();
    }
}
