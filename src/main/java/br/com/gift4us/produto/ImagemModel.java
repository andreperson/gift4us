package br.com.gift4us.produto;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "IMAGEM")
public class ImagemModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(length = 19)
	private Long id;

	@Column(length = 255)
	private String imagem;

	@ManyToOne
	@JoinColumn(name = "produto_id")
	private ProdutoModel produto;

	public ImagemModel() {

	}

	public ImagemModel(Long id, String imagem, ProdutoModel produto) {
		this.id = id;
		this.imagem = imagem;
		this.produto = produto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public ProdutoModel getProduto() {
		return produto;
	}

	public void setProduto(ProdutoModel produto) {
		this.produto = produto;
	}
}