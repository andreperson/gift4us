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
import java.util.Calendar;
import javax.validation.constraints.NotNull;
import br.com.gift4us.categoria.CategoriaModel;
import br.com.gift4us.faixadepreco.FaixaDePrecoModel;
import br.com.gift4us.subcategoria.SubCategoriaModel;
import br.com.gift4us.anunciante.AnuncianteModel;
import br.com.gift4us.status.StatusModel;


public class ProdutoShow {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String codigo;
	private String titulo;
	private String brevedescricao;
	private String descricaocompleta;
	private String tag;
	private Integer estoque;
	private Integer qtdademin;
	private Double preco;
	private String imagem;
	private String urlanunciante;
	private Calendar dataIncl;
	private Calendar dataAlt;

	@ManyToOne
	private CategoriaModel categoria;
	
	@ManyToOne
	private SubCategoriaModel subcategoria;
	
	@ManyToOne
	private FaixaDePrecoModel faixadepreco;
	
	@ManyToOne
	private AnuncianteModel anunciante;

	@ManyToOne
	private StatusModel status;

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

	public FaixaDePrecoModel getFaixaDePreco() {
		return faixadepreco;
	}

	public void setFaixaDePreco(FaixaDePrecoModel faixadepreco) {
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