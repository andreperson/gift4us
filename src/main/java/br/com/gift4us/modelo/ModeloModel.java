package br.com.gift4us.modelo;

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
@Table(name = "MODELO")
public class ModeloModel implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(length = 19)
	private Long id;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioModeloDescricao")
	@Column(length = 255)
	private String descricao;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioModeloTitulo")
	@Column(length = 255)
	private String titulo;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioModeloCorTitulo")
	@Column(length = 7)
	private String corTitulo;

	@Column(length = 255)
	private String imagemUnica;
	
	@Transient
	private MultipartFile imagemUnicaFile;

	@Column(length = 255)
	private String imagemCabecalho;
	
	@Transient
	private MultipartFile imagemCabecalhoFile;

	@Column(length = 255)
	private String imagemRodape;
	
	@Transient
	private MultipartFile imagemRodapeFile;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioModeloAtivo")
	@Column(length = 1)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean ativo = false;



	public ModeloModel(){
		
	}

	public ModeloModel(Long id, String descricao, String titulo, String corTitulo, String imagemUnica, MultipartFile imagemUnicaFile, String imagemCabecalho, MultipartFile imagemCabecalhoFile, String imagemRodape, MultipartFile imagemRodapeFile, Boolean ativo) {
		this.id = id;
		this.descricao = descricao;
		this.titulo = titulo;
		this.corTitulo = corTitulo;
		this.imagemUnica = imagemUnica;
		this.imagemUnicaFile = imagemUnicaFile;
		this.imagemCabecalho = imagemCabecalho;
		this.imagemCabecalhoFile = imagemCabecalhoFile;
		this.imagemRodape = imagemRodape;
		this.imagemRodapeFile = imagemRodapeFile;
		this.ativo = ativo;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCorTitulo() {
		return corTitulo;
	}

	public void setCorTitulo(String corTitulo) {
		this.corTitulo = corTitulo;
	}

	public String getImagemUnica() {
		return imagemUnica;
	}

	public void setImagemUnica(String imagemUnica) {
		this.imagemUnica = imagemUnica;
	}

	public MultipartFile getImagemUnicaFile() {
		return imagemUnicaFile;
	}

	public void setImagemUnicaFile(MultipartFile imagemUnicaFile) {
		this.imagemUnicaFile = imagemUnicaFile;
	}

	public String getImagemCabecalho() {
		return imagemCabecalho;
	}

	public void setImagemCabecalho(String imagemCabecalho) {
		this.imagemCabecalho = imagemCabecalho;
	}

	public MultipartFile getImagemCabecalhoFile() {
		return imagemCabecalhoFile;
	}

	public void setImagemCabecalhoFile(MultipartFile imagemCabecalhoFile) {
		this.imagemCabecalhoFile = imagemCabecalhoFile;
	}

	public String getImagemRodape() {
		return imagemRodape;
	}

	public void setImagemRodape(String imagemRodape) {
		this.imagemRodape = imagemRodape;
	}

	public MultipartFile getImagemRodapeFile() {
		return imagemRodapeFile;
	}

	public void setImagemRodapeFile(MultipartFile imagemRodapeFile) {
		this.imagemRodapeFile = imagemRodapeFile;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}





	
	public boolean isValidoImagemUnicaFile() {
		if (this.imagemUnicaFile != null && this.imagemUnicaFile.getSize() > 0) {
			return true;
		} else return false;
	}
	
	public boolean isValidoImagemUnica() {
		if (this.imagemUnica != null && !"".equals(this.imagemUnica)) {
			return true;
		} else return false;
	}
		
	public boolean isValidoImagemCabecalhoFile() {
		if (this.imagemCabecalhoFile != null && this.imagemCabecalhoFile.getSize() > 0) {
			return true;
		} else return false;
	}
	
	public boolean isValidoImagemCabecalho() {
		if (this.imagemCabecalho != null && !"".equals(this.imagemCabecalho)) {
			return true;
		} else return false;
	}
		
	public boolean isValidoImagemRodapeFile() {
		if (this.imagemRodapeFile != null && this.imagemRodapeFile.getSize() > 0) {
			return true;
		} else return false;
	}
	
	public boolean isValidoImagemRodape() {
		if (this.imagemRodape != null && !"".equals(this.imagemRodape)) {
			return true;
		} else return false;
	}
	

	@Override
	public String toString() {
		return id + " - " + descricao;
	}
}