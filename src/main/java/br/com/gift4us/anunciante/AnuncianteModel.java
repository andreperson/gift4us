package br.com.gift4us.anunciante;

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
import br.com.gift4us.anunciantetipo.AnuncianteTipoModel;


@Entity
@Table(name = "ANUNCIANTE")
public class AnuncianteModel implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(length = 19)
	private Long id;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioAnuncianteRazaosocial")
	@Column(length = 255)
	private String razaosocial;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioAnuncianteFantasia")
	@Column(length = 255)
	private String fantasia;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioAnuncianteEmail")
	@Column(length = 255)
	private String email;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioAnuncianteDdd")
	@Column(length = 5)
	private String ddd;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioAnuncianteTelefone")
	@Column(length = 50)
	private String telefone;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar dataIncl;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar dataAlt;

	
	
	@ManyToOne
	private AnuncianteTipoModel anuncianteTipo;



	public AnuncianteModel(){
		
	}

	public AnuncianteModel(Long id, String razaosocial, String fantasia, String email, String ddd, String telefone, Calendar dataIncl, Calendar dataAlt, AnuncianteTipoModel anuncianteTipo) {
		this.id = id;
		this.razaosocial = razaosocial;
		this.fantasia = fantasia;
		this.email = email;
		this.ddd = ddd;
		this.telefone = telefone;
		this.dataIncl = dataIncl;
		this.dataAlt = dataAlt;
		this.anuncianteTipo = anuncianteTipo;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazaosocial() {
		return razaosocial;
	}

	public void setRazaosocial(String razaosocial) {
		this.razaosocial = razaosocial;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Calendar getDataIncl() {
		return dataIncl;
	}

	public void setDataIncl(Calendar dataincl) {
		this.dataIncl = dataincl;
	}

	public Calendar getDataAlt() {
		return dataAlt;
	}

	public void setDataAlt(Calendar dataalt) {
		this.dataAlt = dataalt;
	}

	public AnuncianteTipoModel getAnuncianteTipo() {
		return anuncianteTipo;
	}

	public void setAnuncianteTipo(AnuncianteTipoModel anuncianteTipo) {
		this.anuncianteTipo = anuncianteTipo;
	}







	@Override
	public String toString() {
		return id + " - " + razaosocial;
	}
}