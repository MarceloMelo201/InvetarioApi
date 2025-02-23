package com.bytenest.InvetarioApi.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PECAS")
public class PecaModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idPeca;
    private String nome;
    private BigDecimal valor;

    @Column(nullable = false, unique = true)
    private String sku;

    private String descricao;
    private Integer quantidade;

}
