package com.bytenest.InvetarioApi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @Column(unique = true)
    private String sku;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private Integer quantidadeTotal;

    @JsonManagedReference
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<EntradaEstoqueModel> entradas = new HashSet<>();

    @ManyToMany(mappedBy = "pecasUtilizadas")
    @JsonIgnore
    private List<OrdemServicoModel> ordensDeServico;

}
