package com.gab.rrs.services;

import com.gab.rrs.dtos.exceptions.InvalidEmailException;
import com.gab.rrs.dtos.login.LoginUserDTO;
import com.gab.rrs.dtos.register.RegisterUserDTO;
import com.gab.rrs.entities.users.Users;
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

    public void register(@RequestBody RegisterUserDTO dto){
        validatorEmail(dto.email());

        Users users = new Users();

        users.setEmail(dto.email());
        users.setName(dto.name());
        users.setPassword(dto.password());
        users.setRole(dto.role());

        usersRepository.save(users);
    }

    public String login (@RequestBody LoginUserDTO dto){
        return ("Login realizado com sucesso");
    }

    protected void validatorEmail(String email){

        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmailException("Email Invalidado");
        } else if (usersRepository.findByEmail(email).isPresent()) {
            throw new InvalidEmailException("Email ja cadastrado");
        }
    }
}
