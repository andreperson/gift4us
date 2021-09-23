package br.com.gift4us.msbcvalidator;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import javax.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import java.util.Set;
import org.springframework.cache.annotation.CacheEvict;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Calendar;
import org.springframework.validation.ObjectError;
import com.google.gson.Gson;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Repository;

@Repository
public class MsbcValidatorDAO {

	private final String TABELA = MsbcValidatorModel.class.getSimpleName();

	@PersistenceContext
	private EntityManager manager;

	public MsbcValidatorDAO() {
	}

	@VisibleForTesting
	public MsbcValidatorDAO(EntityManager em) {
		this.manager = em;
	}

	
	@Transactional
	public void insere(MsbcValidatorModel msbcvalidator) {
		manager.persist(msbcvalidator);
		manager.flush();
		manager.detach(msbcvalidator);
	}

	@Transactional
	public void altera(MsbcValidatorModel msbcvalidator) {
		manager.merge(msbcvalidator);
		manager.flush();
		manager.detach(msbcvalidator);
	}

	@Transactional
	public void exclui(MsbcValidatorModel msbcvalidator) {
		manager.remove(manager.find(MsbcValidatorModel.class, msbcvalidator.getId()));
	}

	public List<MsbcValidatorModel> listaTudo() {
		String jpql = "SELECT m FROM " + TABELA + " m ORDER BY m.id";
		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);
		return query.getResultList();
	}

	public MsbcValidatorModel buscaPorId(Long id) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.id = :id ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("id", id);

		List<MsbcValidatorModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new MsbcValidatorModel();
		}else{
			return resultado.get(0);
		}
	}

	public MsbcValidatorModel buscaPorIdClonando(Long id) {
		MsbcValidatorModel encontrado = buscaPorId(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), MsbcValidatorModel.class);
	}

	public List<MsbcValidatorModel> buscaPorRequiredOnly(String requiredOnly) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.requiredOnly LIKE  :requiredOnly ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("requiredOnly", "%"+requiredOnly+"%");

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorMaxLengthOnly(String maxLengthOnly) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.maxLengthOnly LIKE  :maxLengthOnly ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("maxLengthOnly", "%"+maxLengthOnly+"%");

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorSomenteNumerosOnly(String somenteNumerosOnly) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.somenteNumerosOnly LIKE  :somenteNumerosOnly ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("somenteNumerosOnly", "%"+somenteNumerosOnly+"%");

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorRemoveAcentosOnly(String removeAcentosOnly) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.removeAcentosOnly LIKE  :removeAcentosOnly ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("removeAcentosOnly", "%"+removeAcentosOnly+"%");

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorTextDefault(String textDefault) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.textDefault LIKE  :textDefault ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("textDefault", "%"+textDefault+"%");

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorTextSomenteNumero(String textSomenteNumero) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.textSomenteNumero LIKE  :textSomenteNumero ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("textSomenteNumero", "%"+textSomenteNumero+"%");

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorTextRemoveAcentos(String textRemoveAcentos) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.textRemoveAcentos LIKE  :textRemoveAcentos ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("textRemoveAcentos", "%"+textRemoveAcentos+"%");

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorTextEmail(String textEmail) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.textEmail LIKE  :textEmail ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("textEmail", "%"+textEmail+"%");

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorCpfSomenteNumeros(String cpfSomenteNumeros) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.cpfSomenteNumeros LIKE  :cpfSomenteNumeros ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("cpfSomenteNumeros", "%"+cpfSomenteNumeros+"%");

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorCnpjSomenteNumeros(String cnpjSomenteNumeros) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.cnpjSomenteNumeros LIKE  :cnpjSomenteNumeros ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("cnpjSomenteNumeros", "%"+cnpjSomenteNumeros+"%");

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorTelDDD(String telDDD) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.telDDD LIKE  :telDDD ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("telDDD", "%"+telDDD+"%");

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorTelNumero(String telNumero) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.telNumero LIKE  :telNumero ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("telNumero", "%"+telNumero+"%");

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorCepSomenteNumeros(String cepSomenteNumeros) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.cepSomenteNumeros LIKE  :cepSomenteNumeros ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("cepSomenteNumeros", "%"+cepSomenteNumeros+"%");

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorDataTeste(Calendar dataTeste) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.dataTeste =  :dataTeste ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("dataTeste", dataTeste);

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorDataTime(Calendar dataTime) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.dataTime =  :dataTime ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("dataTime", dataTime);

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorDoubleTeste(Double doubleTeste) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.doubleTeste =  :doubleTeste ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("doubleTeste", doubleTeste);

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorIntegerTeste(Integer integerTeste) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.integerTeste =  :integerTeste ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("integerTeste", integerTeste);

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorLongTeste(Long longTeste) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.longTeste =  :longTeste ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("longTeste", longTeste);

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorTimeStampTeste(Calendar timeStampTeste) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.timeStampTeste =  :timeStampTeste ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("timeStampTeste", timeStampTeste);

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorTextAreaSimples(String textAreaSimples) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.textAreaSimples =  :textAreaSimples ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("textAreaSimples", textAreaSimples);

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorTextAreaHtml(String textAreaHtml) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.textAreaHtml =  :textAreaHtml ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("textAreaHtml", textAreaHtml);

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorBooleanTeste(Boolean booleanTeste) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.booleanTeste =  :booleanTeste ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("booleanTeste", booleanTeste);

		return query.getResultList();
	}

	public List<MsbcValidatorModel> buscaPorArquivoTeste(String arquivoTeste) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.arquivoTeste =  :arquivoTeste ";

		TypedQuery<MsbcValidatorModel> query = manager.createQuery(jpql, MsbcValidatorModel.class);

		query.setParameter("arquivoTeste", arquivoTeste);

		return query.getResultList();
	}

}