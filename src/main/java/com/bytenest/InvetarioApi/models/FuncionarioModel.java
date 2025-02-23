package com.bytenest.InvetarioApi.models;

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
@Table(name = "FUNCIONARIOS")
public class FuncionarioModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idFunc;

    private String nome;

    @Column(unique = true, nullable = false)
    private String cpf;

    private String cargo;
    private String email;
    private String telefone;
    private Boolean estaAtivo;

}
