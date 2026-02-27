package com.gab.rrs.tabletest;

import com.gab.rrs.dtos.register.RegisterTablesDTO;
import com.gab.rrs.dtos.update.UpdateTablesDTO;
import com.gab.rrs.entities.tables.Tables;
import com.gab.rrs.entities.tables.TablesType;
import com.gab.rrs.exceptions.InvalidIdException;
import com.gab.rrs.exceptions.InvalidTableException;
import com.gab.rrs.repository.TablesRepository;
import com.gab.rrs.services.TablesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TablesServiceTest {

    @Mock
    private TablesRepository tablesRepository;

    @InjectMocks
    private TablesService tablesService;


    @Test
    void shouldReturnAllTables() {
        List<Tables> tables = List.of(
                new Tables("Mesa 1", 4, TablesType.available)
        );

        when(tablesRepository.findAll()).thenReturn(tables);

        List<Tables> result = tablesService.allTables();

        assertEquals(1, result.size());
        verify(tablesRepository).findAll();
    }

    @Test
    void shouldRegisterTable() {
        RegisterTablesDTO dto = new RegisterTablesDTO("Mesa 2", 6);

        String result = tablesService.registerTable(dto);

        assertEquals("Mesa registrada com sucesso.", result);
        verify(tablesRepository).save(any(Tables.class));
    }

    @Test
    void shouldUpdateTableCapacity() {
        Tables table = new Tables("Mesa 1", 4, TablesType.available);

        UpdateTablesDTO dto = new UpdateTablesDTO(8, null);

        when(tablesRepository.getReferenceById(1)).thenReturn(table);

        String result = tablesService.updateTable(1, dto);

        assertEquals(8, table.getCapacity());
        assertEquals("Mesa atualizada com sucesso.", result);
        verify(tablesRepository).save(table);
    }

    @Test
    void shouldDeleteTable() {
        when(tablesRepository.findById(1)).thenReturn(Optional.of(
                new Tables("Mesa 1", 4, TablesType.available)
        ));

        String result = tablesService.deleteTable(1);

        assertEquals("Mesa deletada com sucesso.", result);
        verify(tablesRepository).deleteById(1);
    }

    @Test
    void shouldThrowExceptionWhenDeletingInvalidId() {
        when(tablesRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> {
            tablesService.deleteTable(1);
        });
    }

    @Test
    void shouldReturnAvailableTable() {
        Tables table = new Tables("Mesa 1", 4, TablesType.available);

        when(tablesRepository.findById(1)).thenReturn(Optional.of(table));

        Tables result = tablesService.checkTable(1);

        assertEquals(TablesType.available, result.getStatus());
    }

    @Test
    void shouldThrowWhenTableUnavailable() {
        Tables table = new Tables("Mesa 1", 4, TablesType.reserved);

        when(tablesRepository.findById(1)).thenReturn(Optional.of(table));

        assertThrows(InvalidTableException.class, () -> {
            tablesService.checkTable(1);
        });
    }
}