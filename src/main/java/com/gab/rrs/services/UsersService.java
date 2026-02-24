package com.gab.rrs.services;

import com.gab.rrs.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }
}
