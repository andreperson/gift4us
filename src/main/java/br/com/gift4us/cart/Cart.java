package br.com.gift4us.cart;

public class Cart {

	private Long produtoid;
	private Integer qtde;
	
	public Long getProdutoId() {
		return produtoid;
	}
	public void setProdutoId(Long produtoid) {
		this.produtoid = produtoid;
	}
	public Integer getQtde() {
		return qtde;
	}
	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}

}