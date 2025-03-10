package com.bytenest.InvetarioApi.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @Column(unique = true)
    private String cpf;

    private String cargo;

    @Column(unique = true, nullable = false)
    private String email;

    private String telefone;
    private Boolean estaAtivo;

    @OneToMany(mappedBy = "responsavel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<OrdemServicoModel> ordens = new HashSet<>();


}
