package com.gab.rrs.controllers;

import com.gab.rrs.services.TablesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TablesController {
    private TablesService tablesService;


    @GetMapping("/mesas")
    public ResponseEntity<Void> allTable(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/mesas")
    public ResponseEntity<Void> addTable(){
        return ResponseEntity.ok().build();
    }

    @PutMapping("/mesa/{id}")
    public ResponseEntity<Void> updateTable(@PathVariable Integer id){
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/mesas/{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable Integer id){
        return ResponseEntity.ok().build();
    }
}
