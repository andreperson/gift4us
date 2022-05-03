package br.com.gift4us.cart;

public class Cart {

	private Long produtoid;
	private Long anuncianteid;
	private Integer qtde;
	
	public Long getProdutoId() {
		return produtoid;
	}
	public void setProdutoId(Long produtoid) {
		this.produtoid = produtoid;
	}

	public Long getAnuncianteId() {
		return anuncianteid;
	}
	public void setAnuncianteId(Long anuncianteid) {
		this.anuncianteid = anuncianteid;
	}
	
	public Integer getQtde() {
		return qtde;
	}
	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}

}