package com.gab.rrs.controllers;

import com.gab.rrs.dtos.delete.DeleteTableDTO;
import com.gab.rrs.dtos.register.RegisterTablesDTO;
import com.gab.rrs.dtos.update.UpdateTablesDTO;
import com.gab.rrs.services.TablesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mesas")
public class TablesController {

    private TablesService tablesService;

    public TablesController(TablesService tablesService){
        this.tablesService = tablesService;
    }

    @GetMapping()
    public ResponseEntity allTable(){
        return ResponseEntity.ok(tablesService.allTables());
    }

    @PostMapping()
    public ResponseEntity<String> addTable(@RequestBody RegisterTablesDTO dto){
        return ResponseEntity.ok(tablesService.registerTable(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateTable(@PathVariable Integer id, @RequestBody UpdateTablesDTO dto){
        return ResponseEntity.ok(tablesService.updateTable(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTable(@PathVariable Integer id){
        return ResponseEntity.ok(tablesService.deleteTable(id));
    }
}
