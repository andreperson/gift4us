package br.com.gift4us.produto;

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
import br.com.gift4us.categoria.CategoriaModel;
import br.com.gift4us.subcategoria.SubCategoriaModel;
import br.com.gift4us.anunciante.AnuncianteModel;
import br.com.gift4us.status.StatusModel;


@Entity
@Table(name = "PRODUTO")
public class ProdutoModel implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(length = 19)
	private Long id;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioProdutoCodigo")
	@Column(length = 50)
	private String codigo;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioProdutoTitulo")
	@Column(length = 255)
	private String titulo;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioProdutoBrevedescricao")
	@Column(length = 255)
	private String brevedescricao;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioProdutoDescricaocompleta")
	@Column(length = 3999)
	private String descricaocompleta;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioProdutoTag")
	@Column(length = 255)
	private String tag;

	@Column(length = 5)
	private Integer estoque;
	
	@Column(length = 5)
	private Integer qtdademin;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioProdutoPreco")
	@Column(precision=11, scale=0)
	private Double preco;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioProdutoFaixadepreco")
	@Column(length = 100)
	private String faixadepreco;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioProdutoImagem")
	@Column(length = 255)
	private String imagem;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioProdutoUrlanunciante")
	@Column(length = 100)
	private String urlanunciante;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar dataIncl;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar dataAlt;

	
	
	@ManyToMany
	private List<CategoriaModel> listaDeCategoria = new ArrayList<CategoriaModel>();

	
	
	@ManyToMany
	private List<SubCategoriaModel> listaDeSubCategoria = new ArrayList<SubCategoriaModel>();

	
	
	@ManyToOne
	private AnuncianteModel anunciante;

	
	
	@ManyToOne
	private StatusModel status;



	public ProdutoModel(){
		
	}

	public ProdutoModel(Long id, String codigo, String titulo, String brevedescricao, String descricaocompleta, String tag, Integer qtdademin, Double preco, String faixadepreco, String imagem, String urlanunciante, Calendar dataIncl, Calendar dataAlt, List<CategoriaModel> listaDeCategoria, List<SubCategoriaModel> listaDeSubCategoria, AnuncianteModel anunciante, StatusModel status) {
		this.id = id;
		this.codigo = codigo;
		this.titulo = titulo;
		this.brevedescricao = brevedescricao;
		this.descricaocompleta = descricaocompleta;
		this.tag = tag;
		this.qtdademin = qtdademin;
		this.preco = preco;
		this.faixadepreco = faixadepreco;
		this.imagem = imagem;
		this.urlanunciante = urlanunciante;
		this.dataIncl = dataIncl;
		this.dataAlt = dataAlt;
		this.listaDeCategoria = listaDeCategoria;
		this.listaDeSubCategoria = listaDeSubCategoria;
		this.anunciante = anunciante;
		this.status = status;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getBrevedescricao() {
		return brevedescricao;
	}

	public void setBrevedescricao(String brevedescricao) {
		this.brevedescricao = brevedescricao;
	}

	public String getDescricaocompleta() {
		return descricaocompleta;
	}

	public void setDescricaocompleta(String descricaocompleta) {
		this.descricaocompleta = descricaocompleta;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}
	
	public Integer getQtdademin() {
		return qtdademin;
	}

	public void setQtdademin(Integer qtdademin) {
		this.qtdademin = qtdademin;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public String getFaixadepreco() {
		return faixadepreco;
	}

	public void setFaixadepreco(String faixadepreco) {
		this.faixadepreco = faixadepreco;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getUrlanunciante() {
		return urlanunciante;
	}

	public void setUrlanunciante(String urlanunciante) {
		this.urlanunciante = urlanunciante;
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

	public List<CategoriaModel> getListaDeCategoria() {
		return listaDeCategoria;
	}

	public void setListaDeCategoria(List<CategoriaModel> listaDeCategoria) {
		this.listaDeCategoria = listaDeCategoria;
	}

	public List<SubCategoriaModel> getListaDeSubCategoria() {
		return listaDeSubCategoria;
	}

	public void setListaDeSubCategoria(List<SubCategoriaModel> listaDeSubCategoria) {
		this.listaDeSubCategoria = listaDeSubCategoria;
	}

	public AnuncianteModel getAnunciante() {
		return anunciante;
	}

	public void setAnunciante(AnuncianteModel anunciante) {
		this.anunciante = anunciante;
	}

	public StatusModel getStatus() {
		return status;
	}

	public void setStatus(StatusModel status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return id + " - " + codigo;
	}
}