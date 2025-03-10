package com.bytenest.InvetarioApi.models;

import com.bytenest.InvetarioApi.enums.StatusOrdemServico;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ORDENS_DE_SERVICO")
public class OrdemServicoModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idOrdem;

    @Column(unique = true)
    private String codigoOrdem;

    private LocalDate dataAbertura;

    private LocalDate dataConclusao;

    @Column(columnDefinition = "TEXT")
    private String descricaoProblema;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOrdemServico status;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonBackReference
    private ClienteModel cliente;

    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    @JsonBackReference
    private FuncionarioModel responsavel;

    @ManyToMany
    @JoinTable(name = "ordem_servico_pecas",
            joinColumns = @JoinColumn(name = "ordem_servico_id"),
            inverseJoinColumns = @JoinColumn(name = "peca_id"))
    private List<PecaModel> pecasUtilizadas;

    @Column(precision = 10, scale = 2)
    private BigDecimal valorPecas;

    @Column(precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    public OrdemServicoModel setDataConclusao() {
        this.dataConclusao = dataAbertura.plusDays(10);
        return this;
    }

    public OrdemServicoModel setDataAbertura(String dataAbertura) {
        this.dataAbertura = LocalDate.parse(dataAbertura);
        return this;
    }
}
