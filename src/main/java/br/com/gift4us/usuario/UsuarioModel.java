package br.com.gift4us.usuario;

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
import br.com.gift4us.grupo.GrupoModel;
import br.com.gift4us.anunciante.AnuncianteModel;


@Entity
@Table(name = "USUARIO")
public class UsuarioModel implements Serializable , UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(length = 19)
	private Long id;

	@Column(length = 30)
	private String login;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioUsuarioNome")
	@Column(length = 255)
	private String nome;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioUsuarioApelido")
	@Column(length = 255)
	private String apelido;
	
	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioUsuarioEmail")
	@Column(length = 255)
	private String email;
	
	@Column(length = 255)
	private String senha;

	@Column(length = 10)
	private String dddcelular;

	@Column(length = 50)
	private String celular;

	@Column(length = 10)
	private String dddtelefone;

	@Column(length = 50)
	private String telefone;

	@Column(length = 255)
	private String about;
	
	@ManyToMany
	private List<GrupoModel> listaDeGrupo = new ArrayList<GrupoModel>();
	
	@ManyToOne
	private AnuncianteModel anunciante;

	public UsuarioModel(){
		
	}

	public UsuarioModel(Long id, String login, String nome, String senha, String email, String apelido, List<GrupoModel> listaDeGrupo, AnuncianteModel anunciante, String dddcelular, String celular, String dddtelefone, String telefone, String about) {
		this.id = id;
		this.login = login;
		this.nome = nome;
		this.apelido = apelido;
		this.senha = senha;
		this.email = email;
		this.listaDeGrupo = listaDeGrupo;
		this.dddcelular = dddcelular;
		this.celular = celular;
		this.dddtelefone= dddtelefone;
		this.telefone = telefone;
		this.about = about;
		this.anunciante = anunciante;
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
	
	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = new BCryptPasswordEncoder().encode(senha);
	}
	
	public void setSenhaNoCrypt(String senha) {
		this.senha = senha;
	}

	public List<GrupoModel> getListaDeGrupo() {
		return listaDeGrupo;
	}

	public void setListaDeGrupo(List<GrupoModel> listaDeGrupo) {
		this.listaDeGrupo = listaDeGrupo;
	}

	public AnuncianteModel getAnunciante() {
		return anunciante;
	}

	public void setAnunciante(AnuncianteModel anunciante) {
		this.anunciante = anunciante;
	}

	public String getDddcelular() {
		return dddcelular;
	}

	public void setDddcelular(String dddcelular) {
		this.dddcelular = dddcelular;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getDddtelefone() {
		return dddtelefone;
	}

	public void setDddtelefone(String dddtelefone) {
		this.dddtelefone = dddtelefone;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return listaDeGrupo;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}



	@Override
	public String toString() {
		return id + " - " + login;
	}
}