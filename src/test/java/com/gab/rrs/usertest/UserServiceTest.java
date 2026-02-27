package com.gab.rrs.usertest;

import com.gab.rrs.dtos.login.LoginResponseDTO;
import com.gab.rrs.dtos.login.LoginUserDTO;
import com.gab.rrs.dtos.register.RegisterUserDTO;
import com.gab.rrs.entities.users.UserType;
import com.gab.rrs.entities.users.Users;
import com.gab.rrs.infra.security.TokenService;
import com.gab.rrs.repository.UsersRepository;
import com.gab.rrs.services.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UsersService usersService;

    private RegisterUserDTO registerDTO;
    private LoginUserDTO loginDTO;

    @BeforeEach
    void setup(){
        registerDTO = new RegisterUserDTO("Gab", "gab@email.com", "123456", UserType.CLIENT);
        loginDTO = new LoginUserDTO("gab@email.com", "123456");
    }

    @Test
    void shouldRegisterUserSuccessfully(){
        when(usersRepository.findByEmail(registerDTO.email())).thenReturn(null);

        String result = usersService.register(registerDTO);

        verify(usersRepository, times(1)).save(any(Users.class));
        assertEquals("Usuário Registrado com sucesso.", result);
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists(){
        when(usersRepository.findByEmail(registerDTO.email())).thenReturn(mock(org.springframework.security.core.userdetails.UserDetails.class));

        assertThrows(RuntimeException.class, () -> usersService.register(registerDTO));
    }

    @Test
    void shouldLoginSuccessfully(){
        Authentication authentication = mock(Authentication.class);
        Users user = mock(Users.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(tokenService.generateToken(user)).thenReturn("fake-token");

        LoginResponseDTO response = usersService.login(loginDTO);

        assertEquals("fake-token", response.token());
    }
}
