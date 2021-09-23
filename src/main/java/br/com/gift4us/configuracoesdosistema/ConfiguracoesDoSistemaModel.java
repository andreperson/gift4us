package br.com.gift4us.configuracoesdosistema;

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
@Table(name = "CONFIGURACOESDOSISTEMA")
public class ConfiguracoesDoSistemaModel implements Serializable  {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioConfiguracoesDoSistemaPropriedade")
	@Id
	@Column(length = 255)
	private String propriedade;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioConfiguracoesDoSistemaValor")
	@Column(length = 3990)
	private String valor;





	public String getPropriedade() {
		return propriedade;
	}

	public void setPropriedade(String propriedade) {
		this.propriedade = propriedade;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}







	@Override
	public String toString() {
		return propriedade + " - " + valor;
	}
}