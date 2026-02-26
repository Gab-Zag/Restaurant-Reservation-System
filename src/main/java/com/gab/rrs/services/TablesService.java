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
    private UsersService usersService;

    public TablesService(TablesRepository tablesRepository, UsersService usersService){
        this.tablesRepository = tablesRepository;
        this.usersService = usersService;
    }

    public List<Tables> allTables(Tables tables){
        return tablesRepository.findAll();
    }

    public String registerTable(RegisterTablesDTO dto){
        usersService.checkUser(dto.id());
        Tables table = new Tables();

        table.setCapacity(dto.capacity());
        table.setName(dto.name());
        table.setStatus(TablesType.available);

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

    public String deleteTable(Integer tableId, DeleteTableDTO dto){
        if(!tablesRepository.findById(tableId).isPresent()){
            throw new InvalidIdException("Mesa nao registrada em nosso sistema.");
        }else{
            usersService.checkUserAdm(dto.id());
            tablesRepository.deleteById(tableId);
            return "Mesa deletada com sucesso.";
        }
    }

    protected Tables checkTable(Integer id){
        Tables table = tablesRepository.findById(id).orElseThrow(() -> new InvalidIdException("Mesa nao existente em nosso sistema"));

        if(table.getStatus() == TablesType.inactive || table.getStatus() == TablesType.reserved){
            throw new InvalidTableException("Mesa indisponivel");
        }

        return table;
    }
}
