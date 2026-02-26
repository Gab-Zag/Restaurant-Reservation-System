package com.gab.rrs.services;

import com.gab.rrs.dtos.reservation.ReservationTableDTO;
import com.gab.rrs.entities.reservation.Reservation;
import com.gab.rrs.entities.reservation.ReservationType;
import com.gab.rrs.exceptions.InvalidIdException;
import com.gab.rrs.exceptions.InvalidReservationCancelException;
import com.gab.rrs.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private final Validator validator;
    private ReservationRepository reservationRepository;
    private TablesService tablesService;
    private UsersService usersService;

    public ReservationService(ReservationRepository reservationRepository, UsersService usersService, TablesService tablesService, Validator validator){
        this.reservationRepository = reservationRepository;
        this.usersService = usersService;
        this.tablesService = tablesService;
        this.validator = validator;
    }

    public String reservationTable(ReservationTableDTO dto) {

        checkTableIsReserverd(dto.tables_id());

        //Reservation reservation = new Reservation();

        //reservation.setStatus(ReservationType.active);
        //reservation.setTables(tablesService.checkTable(dto.tables_id()));
        //reservation.setUsers(usersService.checkUser(dto.user_id()));

        //reservationRepository.save(reservation);

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

    public void checkTableIsReserverd(Integer id){

        ArrayList table = reservationRepository.findByTables_Id(id);

        Reservation reservation;

        for(int i = 0; i < table.size(); i++){
            if(reservation.getStatus(table.get(i)) != ReservationType.canceled){
            }
        }
    }
}
