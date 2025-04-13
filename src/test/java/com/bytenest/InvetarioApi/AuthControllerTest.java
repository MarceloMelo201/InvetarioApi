package com.bytenest.InvetarioApi;

import com.bytenest.InvetarioApi.controllers.AuthController;
import com.bytenest.InvetarioApi.dtos.CriarUsuarioDto;
import com.bytenest.InvetarioApi.dtos.ResponseDto;
import com.bytenest.InvetarioApi.enums.Cargos;
import com.bytenest.InvetarioApi.models.UsuarioModel;
import com.bytenest.InvetarioApi.repositories.UsuarioRepository;
import com.bytenest.InvetarioApi.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class AuthControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AuthController authController;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TokenService tokenService;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void testRegistroSucesso() throws Exception {
        CriarUsuarioDto criarUsuarioDto = new CriarUsuarioDto(
                "admin@bytenest.com", "123456", Cargos.ROLE_ADMIN);

        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setEmail(criarUsuarioDto.email());
        usuarioModel.setSenha(passwordEncoder.encode(criarUsuarioDto.senha()));
        usuarioModel.setCargo(criarUsuarioDto.cargo());

        // Mockando o comportamento do repositório para retornar um usuário vazio
        when(usuarioRepository.findByEmail(criarUsuarioDto.email())).thenReturn(Optional.empty());

        // Mockando o comportamento do TokenService
        when(tokenService.gerarToken(any(UsuarioModel.class))).thenReturn("fake-token");

        // Realizando a requisição POST para o endpoint /auth/register
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"admin@bytenest.com\", \"senha\":\"123456\", \"cargo\":\"ROLE_ADMIN\"}"))
                .andExpect(status().isOk()) // Espera que a resposta seja 200 OK
                .andExpect(jsonPath("$.email").value("admin@bytenest.com"))
                .andExpect(jsonPath("$.token").value("fake-token"));
    }

    @Test
    public void testRegistroUsuarioExistente() throws Exception {
        CriarUsuarioDto criarUsuarioDto = new CriarUsuarioDto(
                "admin@bytenest.com", "123456", Cargos.ROLE_ADMIN);

        // Mockando o comportamento do repositório para retornar um usuário existente
        UsuarioModel usuarioExistente = new UsuarioModel();
        usuarioExistente.setEmail(criarUsuarioDto.email());
        usuarioExistente.setSenha(passwordEncoder.encode(criarUsuarioDto.senha()));
        usuarioExistente.setCargo(criarUsuarioDto.cargo());

        when(usuarioRepository.findByEmail(criarUsuarioDto.email())).thenReturn(Optional.of(usuarioExistente));

        // Realizando a requisição POST para o endpoint /auth/register
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"admin@bytenest.com\", \"senha\":\"123456\", \"cargo\":\"ROLE_ADMIN\"}"))
                .andExpect(status().isBadRequest()); // Espera que a resposta seja 400 Bad Request
    }
}
