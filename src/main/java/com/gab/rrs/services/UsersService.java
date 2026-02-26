package com.gab.rrs.services;

import com.gab.rrs.entities.users.UserType;
import com.gab.rrs.exceptions.InvalidEmailException;
import com.gab.rrs.dtos.login.LoginUserDTO;
import com.gab.rrs.dtos.register.RegisterUserDTO;
import com.gab.rrs.entities.users.Users;
import com.gab.rrs.exceptions.InvalidIdException;
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
        validatorEmail(dto.email());

        Users users = new Users();

        users.setEmail(dto.email());
        users.setName(dto.name());
        users.setPassword(dto.password());
        users.setRole(dto.role());

        usersRepository.save(users);

        return "Usuário Registrado com sucesso.";
    }

    public String login (@RequestBody LoginUserDTO dto){
        return ("Login realizado com sucesso.");
    }

    protected void validatorEmail(String email){

        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmailException("Email Invalidado.");
        } else if (usersRepository.findByEmail(email).isPresent()) {
            throw new InvalidEmailException("Email ja cadastrado.");
        }
    }

    protected Users checkUser(Integer id){
        Users user = usersRepository.findById(id).orElseThrow(() -> new InvalidIdException("Usuário não encontrado."));

        return user;
    }
    protected void checkUserAdm(Integer id){
        Users user = usersRepository.findById(id).orElseThrow(() -> new InvalidIdException("Usuário não encontrado."));

        if(!user.getRole().equals(UserType.ADM)){
            throw new InvalidIdException("Este Usuário não possui direito de adm.");
        }
    }
}
