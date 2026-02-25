package com.gab.rrs.services;

import com.gab.rrs.dtos.login.LoginUserDTO;
import com.gab.rrs.dtos.register.RegisterUserDTO;
import com.gab.rrs.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.regex.Pattern;

@Service
public class UsersService {
    private UsersRepository usersRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]+$");

    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    public String register(@RequestBody RegisterUserDTO dto){
        return ("Registro realizado com sucesso");
    }

    public String login (@RequestBody LoginUserDTO dto){
        return ("Login realizado com sucesso");
    }

    public String validatorEmail(String email){

        if (email == null || !EMAIL_PATTERN.matcher(email).matches()){

        }
        return email;
    }
}
