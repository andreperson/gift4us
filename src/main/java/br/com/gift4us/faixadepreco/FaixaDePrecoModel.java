package br.com.gift4us.faixadepreco;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.SequenceGenerator;
import javax.persistence.GenerationType;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import br.com.gift4us.status.StatusEnum;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "FAIXA_DE_PRECO")
public class FaixaDePrecoModel implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(length = 19)
	private Long id;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioCategoriaNome")
	@Column(length = 255)
	private String nome;
	
	private Double precode;
	
	private Double precoate;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar dataIncl;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar dataAlt;

	@Column(length = 1)
	private StatusEnum status;
	
	public FaixaDePrecoModel(){
		
	}

	public FaixaDePrecoModel(Long id, String nome, Double precode, Double precoate, Calendar dataincl, Calendar dataalt, StatusEnum status) {
		this.id = id;
		this.nome = nome;
		this.precode = precode;
		this.precoate = precoate;
		this.dataIncl = dataincl;
		this.dataAlt = dataalt;
		this.status = status;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPrecode() {
		return precode;
	}

	public void setPrecode(Double precode) {
		this.precode = precode;
	}

	public Double getPrecoate() {
		return precoate;
	}

	public void setPrecoate(Double precoate) {
		this.precoate = precoate;
	}

	public Calendar getDataIncl() {
		return dataIncl;
	}

	public void setDataIncl(Calendar dataincl) {
		this.dataIncl = dataincl;
	}

	public Calendar getDataAlt() {
		return dataAlt;
	}

	public void setDataAlt(Calendar dataalt) {
		this.dataAlt = dataalt;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum ativo) {
		this.status = ativo;
	}


	@Override
	public String toString() {
		return id + " - " + nome;
	}
}