package br.com.gift4us.campanha;

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
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import br.com.gift4us.produto.ProdutoModel;
import br.com.gift4us.status.StatusEnum;


@Entity
@Table(name = "CAMPANHA")
public class CampanhaModel implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(length = 19)
	private Long id;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioCampanhaNome")
	@Column(length = 100)
	private String nome;
	
	
	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioCampanhaOrdem")
	private Integer ordem;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar dataIncl;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar dataAlt;

	@Column(length = 1)
	private StatusEnum status;

	public CampanhaModel(){
		
	}

	public CampanhaModel(Long id, String nome, Integer ordem, Calendar dataIncl, Calendar dataAlt, StatusEnum status) {
		this.id = id;
		this.nome = nome;
		this.ordem = ordem;
		this.dataIncl = dataIncl;
		this.dataAlt = dataAlt;
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

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public Calendar getDataIncl() {
		return dataIncl;
	}

	public void setDataIncl(Calendar dataIncl) {
		this.dataIncl = dataIncl;
	}

	public Calendar getDataAlt() {
		return dataAlt;
	}

	public void setDataAlt(Calendar dataAlt) {
		this.dataAlt = dataAlt;
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