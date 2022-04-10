package br.com.gift4us.orcamento;

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
import br.com.gift4us.anunciante.AnuncianteModel;


@Entity
@Table(name = "ORCAMENTO")
public class OrcamentoModel implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(length = 19)
	private Long id;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioOrcamentoQuantidade")
	@Column(length = 2)
	private Integer quantidade;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioOrcamentoDataIncl")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar dataIncl;
	
	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioOrcamentoNome")
	@Column(length = 100)
	private String nome;
	
	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioOrcamentoEmail")
	@Column(length = 100)
	private String email;
	
	@Column(length = 2)
	private String ddd;
	
	@Column(length = 10)
	private String celular;

	@Column(length = 1)
	private Integer status;

	@ManyToOne
	private ProdutoModel produto;

	@ManyToOne
	private AnuncianteModel anunciante;
	
	@ManyToMany
	private List<AnuncianteModel> listaDeAnunciante = new ArrayList<AnuncianteModel>();

	public OrcamentoModel(){
	}

	public OrcamentoModel(Long id, Integer quantidade, Calendar dataIncl, ProdutoModel produto, List<AnuncianteModel> listaDeAnunciante, AnuncianteModel anunciante, String nome, String email, String ddd, String celular) {
		this.id = id;
		this.quantidade = quantidade;
		this.dataIncl = dataIncl;
		this.produto = produto;
		this.listaDeAnunciante = listaDeAnunciante;
		this.anunciante = anunciante;
		this.nome = nome;
		this.email = email;
		this.ddd = ddd;
		this.celular = celular;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Calendar getDataIncl() {
		return dataIncl;
	}

	public void setDataIncl(Calendar dataIncl) {
		this.dataIncl = dataIncl;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public ProdutoModel getProduto() {
		return produto;
	}

	public void setProduto(ProdutoModel produto) {
		this.produto = produto;
	}
	
	public AnuncianteModel getAnunciante() {
		return anunciante;
	}

	public void setAnunciante(AnuncianteModel anunciante) {
		this.anunciante = anunciante;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<AnuncianteModel> getListaDeAnunciante() {
		return listaDeAnunciante;
	}

	public void setListaDeAnunciante(List<AnuncianteModel> listaDeAnunciante) {
		this.listaDeAnunciante = listaDeAnunciante;
	}

	@Override
	public String toString() {
		return id + " - " + quantidade;
	}
}