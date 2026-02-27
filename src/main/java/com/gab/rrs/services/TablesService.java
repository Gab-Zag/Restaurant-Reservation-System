package com.gab.rrs.services;

import com.gab.rrs.dtos.delete.DeleteTableDTO;
import com.gab.rrs.dtos.update.UpdateTablesDTO;
import com.gab.rrs.entities.tables.TablesType;
import com.gab.rrs.exceptions.InvalidIdException;
import com.gab.rrs.dtos.register.RegisterTablesDTO;
import com.gab.rrs.entities.tables.Tables;
import com.gab.rrs.exceptions.InvalidTableException;
import com.gab.rrs.repository.TablesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TablesService {

    private TablesRepository tablesRepository;

    public TablesService(TablesRepository tablesRepository){
        this.tablesRepository = tablesRepository;
    }

    public List<Tables> allTables(){
        return tablesRepository.findAll();
    }

    public String registerTable(RegisterTablesDTO dto){
        Tables table = new Tables(dto.name(),dto.capacity(),TablesType.available);

        tablesRepository.save(table);

        return "Mesa registrada com sucesso.";
    }

    public String updateTable(Integer id, UpdateTablesDTO dto){
        Tables table = tablesRepository.getReferenceById(id);

        if(dto.capacity() != null){
            table.setCapacity(dto.capacity());
        }else if (dto.status() != null){
            table.setStatus(dto.status());
        }

        tablesRepository.save(table);

        return "Mesa atualizada com sucesso.";
    }

    public String deleteTable(Integer tableId){
        if(!tablesRepository.findById(tableId).isPresent()){
            throw new InvalidIdException("Mesa nao registrada em nosso sistema.");
        }else{
            tablesRepository.deleteById(tableId);
            return "Mesa deletada com sucesso.";
        }
    }

    public Tables checkTable(Integer id){
        Tables table = tablesRepository.findById(id).orElseThrow(() -> new InvalidIdException("Mesa nao existente em nosso sistema"));

        if(table.getStatus() != TablesType.available){
            throw new InvalidTableException("Mesa indisponivel");
        }

        return table;
    }
}
