package br.com.gift4us.historicodosistema;

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


@Entity
@Table(name = "HISTORICODOSISTEMA")
public class HistoricoDoSistemaModel implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(length = 19)
	private Long id;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioHistoricoDoSistemaLogin")
	@Column(length = 50)
	private String login;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioHistoricoDoSistemaNome")
	@Column(length = 100)
	private String nome;

	@NotNull(message = "ValidacaoErroDataObrigatoriaHistoricoDoSistemaDatahora")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datahora = Calendar.getInstance();

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioHistoricoDoSistemaLocal")
	@Column(length = 100)
	private String local;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioHistoricoDoSistemaAcao")
	@Column(length = 10)
	private String acao;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioHistoricoDoSistemaDados")
	@Column(length = 3999)
	private String dados;



	public HistoricoDoSistemaModel(){
		
	}

	public HistoricoDoSistemaModel(Long id, String login, String nome, Calendar datahora, String local, String acao, String dados) {
		this.id = id;
		this.login = login;
		this.nome = nome;
		this.datahora = datahora;
		this.local = local;
		this.acao = acao;
		this.dados = dados;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Calendar getDatahora() {
		return datahora;
	}

	public void setDatahora(Calendar datahora) {
		this.datahora = datahora;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public String getDados() {
		return dados;
	}

	public void setDados(String dados) {
		this.dados = dados;
	}







	@Override
	public String toString() {
		return id + " - " + login;
	}
}