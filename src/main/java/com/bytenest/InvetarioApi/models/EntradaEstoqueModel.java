package com.bytenest.InvetarioApi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ENTRADAS")
public class EntradaEstoqueModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idEntrada;

    private LocalDateTime dataEntrada;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "codigo_peca", referencedColumnName = "sku")
    private PecaModel produto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorUnitario;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(unique = true)
    private String notaFiscal;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

}
