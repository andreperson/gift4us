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

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioOrcamentoDataAlt")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar dataAlt;

	
	
	@ManyToOne
	private ProdutoModel produto;

	
	
	@ManyToMany
	private List<AnuncianteModel> listaDeAnunciante = new ArrayList<AnuncianteModel>();



	public OrcamentoModel(){
		
	}

	public OrcamentoModel(Long id, Integer quantidade, Calendar dataIncl, Calendar dataAlt, ProdutoModel produto, List<AnuncianteModel> listaDeAnunciante) {
		this.id = id;
		this.quantidade = quantidade;
		this.dataIncl = dataIncl;
		this.dataAlt = dataAlt;
		this.produto = produto;
		this.listaDeAnunciante = listaDeAnunciante;

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

	public Calendar getDataAlt() {
		return dataAlt;
	}

	public void setDataAlt(Calendar dataAlt) {
		this.dataAlt = dataAlt;
	}

	public ProdutoModel getProduto() {
		return produto;
	}

	public void setProduto(ProdutoModel produto) {
		this.produto = produto;
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