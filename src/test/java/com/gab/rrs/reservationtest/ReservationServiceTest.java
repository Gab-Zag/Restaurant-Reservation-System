package com.gab.rrs.reservationtest;

import com.gab.rrs.dtos.reservation.ReservationTableDTO;
import com.gab.rrs.entities.reservation.Reservation;
import com.gab.rrs.entities.reservation.ReservationType;
import com.gab.rrs.entities.tables.Tables;
import com.gab.rrs.entities.tables.TablesType;
import com.gab.rrs.exceptions.InvalidIdException;
import com.gab.rrs.exceptions.InvalidReservationCancelException;
import com.gab.rrs.repository.ReservationRepository;
import com.gab.rrs.repository.TablesRepository;
import com.gab.rrs.services.ReservationService;
import com.gab.rrs.services.TablesService;
import com.gab.rrs.services.UsersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private TablesRepository tablesRepository;

    @Mock
    private TablesService tablesService;

    @Mock
    private UsersService usersService;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void shouldReserveTableSuccessfully() {

        Tables table = new Tables("Mesa 1", 4, TablesType.available);

        ReservationTableDTO dto = new ReservationTableDTO(1, 1);

        when(tablesRepository.findById(1)).thenReturn(Optional.of(table));
        when(usersService.checkUser(1)).thenReturn(null); // mock user se necessário
        when(tablesService.checkTable(1)).thenReturn(table);

        String result = reservationService.reservationTable(dto);

        assertEquals("Mesa Reservado com sucesso", result);
        assertEquals(TablesType.reserved, table.getStatus());

        verify(reservationRepository).save(any(Reservation.class));
        verify(tablesRepository).save(table);
    }

    @Test
    void shouldThrowWhenTableNotFound() {

        ReservationTableDTO dto = new ReservationTableDTO(1, 1);

        when(tablesRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> {
            reservationService.reservationTable(dto);
        });
    }

    @Test
    void shouldReturnAllReservations() {

        List<Reservation> list = List.of(mock(Reservation.class));

        when(reservationRepository.findAll()).thenReturn(list);

        List<Reservation> result = reservationService.getAll();

        assertEquals(1, result.size());
        verify(reservationRepository).findAll();
    }

    @Test
    void shouldCancelReservation() {

        Tables table = new Tables("Mesa 1", 4, TablesType.reserved);
        Reservation reservation = new Reservation(null, table, LocalDateTime.now(), ReservationType.active);

        when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));

        String result = reservationService.cancel(1);

        assertEquals("Reserva da mesa foi cancelada com sucesso", result);
        assertEquals(ReservationType.canceled, reservation.getStatus());
        assertEquals(TablesType.available, table.getStatus());

        verify(reservationRepository).save(reservation);
        verify(tablesRepository).save(table);
    }

    @Test
    void shouldThrowWhenAlreadyCanceled() {

        Tables table = new Tables("Mesa 1", 4, TablesType.available);
        Reservation reservation = new Reservation(null, table, LocalDateTime.now(), ReservationType.canceled);

        when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));

        assertThrows(InvalidReservationCancelException.class, () -> {
            reservationService.cancel(1);
        });
    }
}
