package com.gab.rrs.services;

import com.gab.rrs.dtos.login.LoginResponseDTO;
import com.gab.rrs.dtos.login.LoginUserDTO;
import com.gab.rrs.dtos.register.RegisterUserDTO;
import com.gab.rrs.entities.users.Users;
import com.gab.rrs.exceptions.InvalidIdException;
import com.gab.rrs.infra.security.TokenService;
import com.gab.rrs.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;


@Service
public class UsersService {

    private UsersRepository usersRepository;

    private AuthenticationManager authenticationManager;

    private TokenService tokenService;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]+$");

    public UsersService(UsersRepository usersRepository,TokenService tokenService, AuthenticationManager authenticationManager){
        this.usersRepository = usersRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    public String register(RegisterUserDTO dto){

        checkEmail(dto.email());

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        Users users = new Users(dto.name(),dto.email(),encryptedPassword,dto.role());

        usersRepository.save(users);

        return "Usuário Registrado com sucesso.";
    }

    public LoginResponseDTO login (LoginUserDTO dto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(),dto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Users) auth.getPrincipal());

        return new LoginResponseDTO(token);
    }

    protected Users checkUser(Integer id){
        return usersRepository.findById(id).orElseThrow(() -> new InvalidIdException("Usuário não encontrado."));
    }

    protected void checkEmail(String email){
        if(usersRepository.findByEmail(email) != null || email == null || !EMAIL_PATTERN.matcher(email).matches()){
            throw new RuntimeException("Email Invalido");
        }
    }
}
