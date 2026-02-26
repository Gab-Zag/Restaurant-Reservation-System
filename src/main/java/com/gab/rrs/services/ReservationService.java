package com.gab.rrs.services;

import com.gab.rrs.dtos.reservation.ReservationTableDTO;
import com.gab.rrs.entities.reservation.Reservation;
import com.gab.rrs.entities.reservation.ReservationType;
import com.gab.rrs.entities.tables.Tables;
import com.gab.rrs.entities.tables.TablesType;
import com.gab.rrs.exceptions.InvalidIdException;
import com.gab.rrs.exceptions.InvalidReservationCancelException;
import com.gab.rrs.repository.ReservationRepository;
import com.gab.rrs.repository.TablesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;
    private TablesService tablesService;
    private TablesRepository tablesRepository;
    private UsersService usersService;

    public ReservationService(ReservationRepository reservationRepository, UsersService usersService, TablesService tablesService, TablesRepository tablesRepository){
        this.reservationRepository = reservationRepository;
        this.usersService = usersService;
        this.tablesService = tablesService;
        this.tablesRepository = tablesRepository;
    }

    public String reservationTable(ReservationTableDTO dto) {

        Tables table = tablesRepository.findById(dto.tables_id()).orElseThrow(() -> new InvalidIdException("Mesa nao encontrada"));
        Reservation reservation = new Reservation();

        reservation.setStatus(ReservationType.active);
        reservation.setTables(tablesService.checkTable(dto.tables_id()));
        reservation.setUsers(usersService.checkUser(dto.user_id()));
        table.setStatus(TablesType.reserved);

        reservationRepository.save(reservation);
        tablesRepository.save(table);

        return "Mesa Reservado com sucesso";
    }

    public List<Reservation> getAll(){
        return reservationRepository.findAll();
    }

    public String cancel(Integer id){
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new InvalidIdException("Nao possui reserva em nosso sistema"));

        if (reservation.getStatus() == ReservationType.canceled){
            throw new InvalidReservationCancelException("Reserva ja esta cancelada em nosso sistema");
        }
        reservation.setStatus(ReservationType.canceled);

        reservationRepository.save(reservation);

        return "Reserva da mesa foi cancelada com sucesso";
    }
}
