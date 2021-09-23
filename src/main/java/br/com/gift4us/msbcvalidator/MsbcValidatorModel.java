package br.com.gift4us.msbcvalidator;

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
@Table(name = "MSBCVALIDATOR")
public class MsbcValidatorModel implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(length = 19)
	private Long id;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorRequiredOnly")
	@Column(length = 10)
	private String requiredOnly;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorMaxLengthOnly")
	@Column(length = 15)
	private String maxLengthOnly;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorSomenteNumerosOnly")
	@Column(length = 20)
	private String somenteNumerosOnly;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorRemoveAcentosOnly")
	@Column(length = 25)
	private String removeAcentosOnly;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTextDefault")
	@Column(length = 30)
	private String textDefault;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTextSomenteNumero")
	@Column(length = 35)
	private String textSomenteNumero;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTextRemoveAcentos")
	@Column(length = 40)
	private String textRemoveAcentos;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTextEmail")
	@Column(length = 45)
	private String textEmail;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorCpfSomenteNumeros")
	@Column(length = 50)
	private String cpfSomenteNumeros;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorCnpjSomenteNumeros")
	@Column(length = 55)
	private String cnpjSomenteNumeros;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTelDDD")
	@Column(length = 2)
	private String telDDD;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTelNumero")
	@Column(length = 255)
	private String telNumero;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorCepSomenteNumeros")
	@Column(length = 255)
	private String cepSomenteNumeros;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorDataTeste")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar dataTeste;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorDataTime")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Calendar dataTime;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorDoubleTeste")
	@Column(precision=20, scale=0)
	private Double doubleTeste;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorIntegerTeste")
	@Column(length = 5)
	private Integer integerTeste;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorLongTeste")
	@Column(length = 5)
	private Long longTeste;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTimeStampTeste")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar timeStampTeste = Calendar.getInstance();

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTextAreaSimples")
	@Column(length = 1000)
	private String textAreaSimples;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTextAreaHtml")
	@Column(length = 1000)
	private String textAreaHtml;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorBooleanTeste")
	@Column(length = 1)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean booleanTeste = false;

	@Column(length = 1)
	private String arquivoTeste;
	
	@Transient
	private MultipartFile arquivoTesteFile;



	public MsbcValidatorModel(){
		
	}

	public MsbcValidatorModel(Long id, String requiredOnly, String maxLengthOnly, String somenteNumerosOnly, String removeAcentosOnly, String textDefault, String textSomenteNumero, String textRemoveAcentos, String textEmail, String cpfSomenteNumeros, String cnpjSomenteNumeros, String telDDD, String telNumero, String cepSomenteNumeros, Calendar dataTeste, Calendar dataTime, Double doubleTeste, Integer integerTeste, Long longTeste, Calendar timeStampTeste, String textAreaSimples, String textAreaHtml, Boolean booleanTeste, String arquivoTeste, MultipartFile arquivoTesteFile) {
		this.id = id;
		this.requiredOnly = requiredOnly;
		this.maxLengthOnly = maxLengthOnly;
		this.somenteNumerosOnly = somenteNumerosOnly;
		this.removeAcentosOnly = removeAcentosOnly;
		this.textDefault = textDefault;
		this.textSomenteNumero = textSomenteNumero;
		this.textRemoveAcentos = textRemoveAcentos;
		this.textEmail = textEmail;
		this.cpfSomenteNumeros = cpfSomenteNumeros;
		this.cnpjSomenteNumeros = cnpjSomenteNumeros;
		this.telDDD = telDDD;
		this.telNumero = telNumero;
		this.cepSomenteNumeros = cepSomenteNumeros;
		this.dataTeste = dataTeste;
		this.dataTime = dataTime;
		this.doubleTeste = doubleTeste;
		this.integerTeste = integerTeste;
		this.longTeste = longTeste;
		this.timeStampTeste = timeStampTeste;
		this.textAreaSimples = textAreaSimples;
		this.textAreaHtml = textAreaHtml;
		this.booleanTeste = booleanTeste;
		this.arquivoTeste = arquivoTeste;
		this.arquivoTesteFile = arquivoTesteFile;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequiredOnly() {
		return requiredOnly;
	}

	public void setRequiredOnly(String requiredOnly) {
		this.requiredOnly = requiredOnly;
	}

	public String getMaxLengthOnly() {
		return maxLengthOnly;
	}

	public void setMaxLengthOnly(String maxLengthOnly) {
		this.maxLengthOnly = maxLengthOnly;
	}

	public String getSomenteNumerosOnly() {
		return somenteNumerosOnly;
	}

	public void setSomenteNumerosOnly(String somenteNumerosOnly) {
		this.somenteNumerosOnly = somenteNumerosOnly;
	}

	public String getRemoveAcentosOnly() {
		return removeAcentosOnly;
	}

	public void setRemoveAcentosOnly(String removeAcentosOnly) {
		this.removeAcentosOnly = removeAcentosOnly;
	}

	public String getTextDefault() {
		return textDefault;
	}

	public void setTextDefault(String textDefault) {
		this.textDefault = textDefault;
	}

	public String getTextSomenteNumero() {
		return textSomenteNumero;
	}

	public void setTextSomenteNumero(String textSomenteNumero) {
		this.textSomenteNumero = textSomenteNumero;
	}

	public String getTextRemoveAcentos() {
		return textRemoveAcentos;
	}

	public void setTextRemoveAcentos(String textRemoveAcentos) {
		this.textRemoveAcentos = textRemoveAcentos;
	}

	public String getTextEmail() {
		return textEmail;
	}

	public void setTextEmail(String textEmail) {
		this.textEmail = textEmail;
	}

	public String getCpfSomenteNumeros() {
		return cpfSomenteNumeros;
	}

	public void setCpfSomenteNumeros(String cpfSomenteNumeros) {
		this.cpfSomenteNumeros = cpfSomenteNumeros;
	}

	public String getCnpjSomenteNumeros() {
		return cnpjSomenteNumeros;
	}

	public void setCnpjSomenteNumeros(String cnpjSomenteNumeros) {
		this.cnpjSomenteNumeros = cnpjSomenteNumeros;
	}

	public String getTelDDD() {
		return telDDD;
	}

	public void setTelDDD(String telDDD) {
		this.telDDD = telDDD;
	}

	public String getTelNumero() {
		return telNumero;
	}

	public void setTelNumero(String telNumero) {
		this.telNumero = telNumero;
	}

	public String getCepSomenteNumeros() {
		return cepSomenteNumeros;
	}

	public void setCepSomenteNumeros(String cepSomenteNumeros) {
		this.cepSomenteNumeros = cepSomenteNumeros;
	}

	public Calendar getDataTeste() {
		return dataTeste;
	}

	public void setDataTeste(Calendar dataTeste) {
		this.dataTeste = dataTeste;
	}

	public Calendar getDataTime() {
		return dataTime;
	}

	public void setDataTime(Calendar dataTime) {
		this.dataTime = dataTime;
	}

	public Double getDoubleTeste() {
		return doubleTeste;
	}

	public void setDoubleTeste(Double doubleTeste) {
		this.doubleTeste = doubleTeste;
	}

	public Integer getIntegerTeste() {
		return integerTeste;
	}

	public void setIntegerTeste(Integer integerTeste) {
		this.integerTeste = integerTeste;
	}

	public Long getLongTeste() {
		return longTeste;
	}

	public void setLongTeste(Long longTeste) {
		this.longTeste = longTeste;
	}

	public Calendar getTimeStampTeste() {
		return timeStampTeste;
	}

	public void setTimeStampTeste(Calendar timeStampTeste) {
		this.timeStampTeste = timeStampTeste;
	}

	public String getTextAreaSimples() {
		return textAreaSimples;
	}

	public void setTextAreaSimples(String textAreaSimples) {
		this.textAreaSimples = textAreaSimples;
	}

	public String getTextAreaHtml() {
		return textAreaHtml;
	}

	public void setTextAreaHtml(String textAreaHtml) {
		this.textAreaHtml = textAreaHtml;
	}

	public Boolean getBooleanTeste() {
		return booleanTeste;
	}

	public void setBooleanTeste(Boolean booleanTeste) {
		this.booleanTeste = booleanTeste;
	}

	public String getArquivoTeste() {
		return arquivoTeste;
	}

	public void setArquivoTeste(String arquivoTeste) {
		this.arquivoTeste = arquivoTeste;
	}

	public MultipartFile getArquivoTesteFile() {
		return arquivoTesteFile;
	}

	public void setArquivoTesteFile(MultipartFile arquivoTesteFile) {
		this.arquivoTesteFile = arquivoTesteFile;
	}





	
	public boolean isValidoArquivoTesteFile() {
		if (this.arquivoTesteFile != null && this.arquivoTesteFile.getSize() > 0) {
			return true;
		} else return false;
	}
	
	public boolean isValidoArquivoTeste() {
		if (this.arquivoTeste != null && !"".equals(this.arquivoTeste)) {
			return true;
		} else return false;
	}
	

	@Override
	public String toString() {
		return id + " - " + requiredOnly;
	}
}