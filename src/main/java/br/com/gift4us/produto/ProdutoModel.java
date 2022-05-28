package br.com.gift4us.produto;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import javax.validation.constraints.NotNull;
import br.com.gift4us.categoria.CategoriaModel;
import br.com.gift4us.faixadepreco.FaixaDePrecoModel;
import br.com.gift4us.linha.LinhaModel;
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
	
	@Column(length = 5)
	@NotNull(message = "Desconto")
	private Integer desconto;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioProdutoPreco")
	@Column(precision=11, scale=0)
	private Double preco;

	@Column(length = 255)
	private String imagem;

	@Column(length = 100)
	private String urlanunciante;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar dataIncl;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar dataAlt;

	@ManyToOne
	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioProdutoUrlanunciante")
	@JoinColumn(name = "categoria_id")
	private CategoriaModel categoria;
	
	@ManyToOne
	@JoinColumn(name = "subcategoria_id")
	private SubCategoriaModel subcategoria;
	
	@ManyToOne
	@JoinColumn(name = "faixadepreco_id")
	private FaixaDePrecoModel faixadepreco;
	
	@ManyToOne
	@JoinColumn(name = "anunciante_id")
	private AnuncianteModel anunciante;

	@ManyToOne
	@JoinColumn(name = "status_id")
	private StatusModel status;

	@ManyToOne
	@JoinColumn(name = "linha_id")
	private LinhaModel linha;
	
	public ProdutoModel(){
		
	}

	public ProdutoModel(Long id, String codigo, String titulo, String brevedescricao, String descricaocompleta, String tag, Integer qtdademin, Double preco, String imagem, String urlanunciante, Calendar dataIncl, Calendar dataAlt, CategoriaModel categoria, SubCategoriaModel subcategoria, AnuncianteModel anunciante, StatusModel status, FaixaDePrecoModel faixadepreco, Integer desconto, LinhaModel linha) {
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
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.anunciante = anunciante;
		this.status = status;
		this.desconto = desconto;
		this.linha = linha;
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
	
	public Integer getDesconto() {
		return desconto;
	}

	public void setDesconto(Integer desconto) {
		this.desconto = desconto;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public FaixaDePrecoModel getFaixaDePreco() {
		return faixadepreco;
	}

	public void setFaixaDePreco(FaixaDePrecoModel faixadepreco) {
		this.faixadepreco = faixadepreco;
	}
	
	public LinhaModel getLinha() {
		return linha;
	}

	public void setLinha(LinhaModel linha) {
		this.linha = linha;
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

	public CategoriaModel getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaModel categoria) {
		this.categoria = categoria;
	}

	public SubCategoriaModel getSubCategoria() {
		return subcategoria;
	}

	public void setSubCategoria(SubCategoriaModel subcategoria) {
		this.subcategoria = subcategoria;
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