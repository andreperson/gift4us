package br.com.gift4us.linhahome;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

import br.com.gift4us.categoria.CategoriaModel;
import br.com.gift4us.linha.LinhaModel;
import br.com.gift4us.produto.ProdutoModel;
import br.com.gift4us.status.StatusEnum;
import br.com.gift4us.subcategoria.SubCategoriaModel;


@Entity
@Table(name = "LINHAHOME")
public class LinhaHomeModel implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(length = 19)
	private Long id;

	private Long campanhaid;
	
	private Long linhaid;
	
	private Long categoriaid;
	
	private Long subcategoriaid;
	
	
	public LinhaHomeModel(){
		
	}

	public LinhaHomeModel(Long id, Long campanhaid, Long linhaid, Long categoriaid, long subcategoriaid) {
		this.id = id;
		this.campanhaid=campanhaid;
		this.linhaid = linhaid;
		this.categoriaid = categoriaid;
		this.subcategoriaid = subcategoriaid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLinhaid() {
		return linhaid;
	}

	public void setLinhaid(Long linhaid) {
		this.linhaid = linhaid;
	}

	public Long getCampanhaid() {
		return campanhaid;
	}

	public void setCampanhaid(Long campanhaid) {
		this.campanhaid = campanhaid;
	}

	public Long getCategoriaid() {
		return categoriaid;
	}

	public void setCategoriaid(Long categoriaid) {
		this.categoriaid = categoriaid;
	}

	public Long getSubcategoriaid() {
		return subcategoriaid;
	}

	public void setSubcategoriaid(Long subcategoriaid) {
		this.subcategoriaid = subcategoriaid;
	}

}