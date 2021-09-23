package br.com.gift4us.atividade;

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


@Entity
@Table(name = "ATIVIDADE")
public class AtividadeModel implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(length = 19)
	private Long id;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioAtividadeNome")
	@Column(length = 100)
	private String nome;

	
	
	@ManyToMany
	private List<ProdutoModel> listaDeProduto = new ArrayList<ProdutoModel>();

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioAtividadeDataIncl")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar dataIncl;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioAtividadeDataAlt")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar dataAlt;



	public AtividadeModel(){
		
	}

	public AtividadeModel(Long id, String nome, List<ProdutoModel> listaDeProduto, Calendar dataIncl, Calendar dataAlt) {
		this.id = id;
		this.nome = nome;
		this.listaDeProduto = listaDeProduto;
		this.dataIncl = dataIncl;
		this.dataAlt = dataAlt;

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

	public List<ProdutoModel> getListaDeProduto() {
		return listaDeProduto;
	}

	public void setListaDeProduto(List<ProdutoModel> listaDeProduto) {
		this.listaDeProduto = listaDeProduto;
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







	@Override
	public String toString() {
		return id + " - " + nome;
	}
}