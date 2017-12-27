package br.com.rodolfocastanho.controle2M.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "tb_ordem")
public class Ordem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 20)
    private String numero;

    //@NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Calendar dataEntrada;

    @NotBlank
    @Column(length = 200)
    private String cliente;

    @Column(length = 20)
    private String statusOrdem;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Calendar dataProvisionamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statusProvisionamento")
    private StatusProvisionamento statusProvisionamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavelProvisionamento")
    private Usuario responsavelProvisionamento;

    @Column(length = 500)
    private String observacaoProvisionamento;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Calendar dataComissionamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statusComissionamento")
    private StatusComissionamento statusComissionamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavelComissionamento")
    private Usuario responsavelComissionamento;

    @Column(length = 500)
    private String observacaoComissionamento;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioId")
    private Usuario responsavel;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoServico")
    private TipoServicoOrdem tipoServico;

    @Column
    private int pendenciaBAL;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "segmento")
    private Segmento segmento;

    @Column
    private int e1 = 1;

    @Transient
    private int diasProvisionamento;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Calendar getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Calendar dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getStatusOrdem() {
        return statusOrdem;
    }

    public void setStatusOrdem(String statusOrdem) {
        this.statusOrdem = statusOrdem;
    }

    public Calendar getDataProvisionamento() {
        return dataProvisionamento;
    }

    public void setDataProvisionamento(Calendar dataProvisionamento) {
        this.dataProvisionamento = dataProvisionamento;
    }

    public StatusProvisionamento getStatusProvisionamento() {
        return statusProvisionamento;
    }

    public void setStatusProvisionamento(StatusProvisionamento statusProvisionamento) {
        this.statusProvisionamento = statusProvisionamento;
    }

    public Usuario getResponsavelProvisionamento() {
        return responsavelProvisionamento;
    }

    public void setResponsavelProvisionamento(Usuario responsavelProvisionamento) {
        this.responsavelProvisionamento = responsavelProvisionamento;
    }

    public String getObservacaoProvisionamento() {
        return observacaoProvisionamento;
    }

    public void setObservacaoProvisionamento(String observacaoProvisionamento) {
        this.observacaoProvisionamento = observacaoProvisionamento;
    }

    public Calendar getDataComissionamento() {
        return dataComissionamento;
    }

    public void setDataComissionamento(Calendar dataComissionamento) {
        this.dataComissionamento = dataComissionamento;
    }

    public StatusComissionamento getStatusComissionamento() {
        return statusComissionamento;
    }

    public void setStatusComissionamento(StatusComissionamento statusComissionamento) {
        this.statusComissionamento = statusComissionamento;
    }

    public Usuario getResponsavelComissionamento() {
        return responsavelComissionamento;
    }

    public void setResponsavelComissionamento(Usuario responsavelComissionamento) {
        this.responsavelComissionamento = responsavelComissionamento;
    }

    public String getObservacaoComissionamento() {
        return observacaoComissionamento;
    }

    public void setObservacaoComissionamento(String observacaoComissionamento) {
        this.observacaoComissionamento = observacaoComissionamento;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

    public TipoServicoOrdem getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(TipoServicoOrdem tipoServico) {
        this.tipoServico = tipoServico;
    }

    public int getPendenciaBAL() {
        return pendenciaBAL;
    }

    public void setPendenciaBAL(int pendenciaBAL) {
        this.pendenciaBAL = pendenciaBAL;
    }

    public Segmento getSegmento() {
        return segmento;
    }

    public void setSegmento(Segmento segmento) {
        this.segmento = segmento;
    }

    public int getE1() {
        return e1;
    }

    public void setE1(int e1) {
        this.e1 = e1;
    }
}
