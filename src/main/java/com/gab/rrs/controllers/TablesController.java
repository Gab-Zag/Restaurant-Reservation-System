package com.gab.rrs.controllers;

import com.gab.rrs.dtos.delete.DeleteTableDTO;
import com.gab.rrs.dtos.register.RegisterTablesDTO;
import com.gab.rrs.dtos.update.UpdateTablesDTO;
import com.gab.rrs.services.TablesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TablesController {
    private TablesService tablesService;

    public TablesController(TablesService tablesService){
        this.tablesService = tablesService;
    }

    @GetMapping("/mesas")
    public ResponseEntity allTable(){
        return ResponseEntity.ok(this.tablesService.allTables(null));
    }

    @PostMapping("/mesas")
    public ResponseEntity<String> addTable(@RequestBody RegisterTablesDTO dto){
        String confirmed = tablesService.registerTable(dto);
        return ResponseEntity.ok(confirmed);
    }

    @PatchMapping("/mesa/{id}")
    public ResponseEntity<String> updateTable(@PathVariable Integer id, @RequestBody UpdateTablesDTO dto){
        String confirmed = tablesService.updateTable(id,dto);
        return ResponseEntity.ok(confirmed);
    }

    @DeleteMapping("/mesas/{tableId}")
    public ResponseEntity<String> deleteTable(@PathVariable Integer tableId, @RequestBody DeleteTableDTO dto){
        String confirmed = tablesService.deleteTable(tableId, dto);
        return ResponseEntity.ok(confirmed);
    }
}
