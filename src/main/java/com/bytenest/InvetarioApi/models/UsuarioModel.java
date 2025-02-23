package com.bytenest.InvetarioApi.models;

import com.bytenest.InvetarioApi.enums.CargoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USUARIOS")
public class UsuarioModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idUsuario;

    private String login;

    @Column(unique = true, nullable = false)
    private String email;
    private String senha;
    private Boolean estaAtivo;


}
