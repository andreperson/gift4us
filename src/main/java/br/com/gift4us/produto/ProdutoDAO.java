package br.com.gift4us.produto;

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

import br.com.gift4us.anunciante.AnuncianteModel;
import br.com.gift4us.categoria.CategoriaModel;
import br.com.gift4us.linha.LinhaModel;

import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Repository;

@Repository
public class ProdutoDAO {

	private final String TABELA = ProdutoModel.class.getSimpleName();

	@PersistenceContext
	private EntityManager manager;

	public ProdutoDAO() {
	}

	@VisibleForTesting
	public ProdutoDAO(EntityManager em) {
		this.manager = em;
	}

	@Transactional
	public Long saveorupdate(ProdutoModel produto) {
		manager.persist(produto);
		manager.flush();
		manager.detach(produto);
		return produto.getId();
	}
	
	
	@Transactional
	public void insere(ProdutoModel produto) {
		manager.persist(produto);
		manager.flush();
		manager.detach(produto);
	}

	@Transactional
	public void altera(ProdutoModel produto) {
		manager.merge(produto);
		manager.flush();
		manager.detach(produto);
	}

	@Transactional
	public void exclui(ProdutoModel produto) {
		manager.remove(manager.find(ProdutoModel.class, produto.getId()));
	}

	public List<ProdutoModel> listaTudo() {
		String jpql = "SELECT p FROM " + TABELA + " p ORDER BY p.id desc";
		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);
		return query.getResultList();
	}
	
	public List<ProdutoModel> listaNovidades(Integer quantidade) {
		String jpql = "SELECT p FROM " + TABELA + " p ORDER BY p.id desc";
		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);
		query.setFirstResult(0);
		query.setMaxResults(quantidade);
		return query.getResultList();
	}
	
	public List<ProdutoModel> listaProdutosDoCarrinho(List<Long> ids) {
		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.id IN :ids order by p.anunciante";
		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);
		query.setParameter("ids", ids);
		List<ProdutoModel> lst = query.getResultList();
		
		return lst;
	}
	
	public List<ProdutoModel> listaProdutosDoCarrinhoPorAnunciante(List<Long> ids, AnuncianteModel anunciante) {
		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.id IN :ids and p.anunciante = :anunciante order by p.anunciante";
		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);
		query.setParameter("ids", ids);
		query.setParameter("anunciante", anunciante);
		List<ProdutoModel> lst = query.getResultList();
		
		return lst;
	}
	
	
	public List<ProdutoShow> buscaProdutosParaCarrinho(String ids) {

		String sql = "SELECT id, titulo, imagem, preco FROM Produto WHERE id in (" + ids + ")";
		List<Object> produtos = manager.createNativeQuery(sql).getResultList();
		List<ProdutoShow> lstProdutos = new ArrayList<ProdutoShow>();		
		lstProdutos = ConvertModelToShow(produtos);
		return lstProdutos;
	}
	
	
	
	public List<ProdutoShow> ConvertModelToShow(List<Object> lstProdutos) {
		List<ProdutoShow> lstShow = new ArrayList<ProdutoShow>();
		ProdutoShow obj = new ProdutoShow();
		
		
		
		 for (Object prd : lstProdutos) {
			obj = new ProdutoShow();
			//obj.setBrevedescricao(prd[0]);
//			obj.setCategoria(model.getCategoria());
//			obj.setCodigo(model.getCodigo());
//			obj.setDataAlt(model.getDataAlt());
//			obj.setDataIncl(model.getDataIncl());
//			obj.setDescricaocompleta(model.getDescricaocompleta());
//			obj.setEstoque(model.getEstoque());
//			obj.setFaixaDePreco(model.getFaixaDePreco());
//			obj.setId(model.getId());
//			obj.setImagem(model.getImagem());
//			obj.setPreco(model.getPreco());
//			obj.setQtdademin(model.getQtdademin());
//			obj.setStatus(model.getStatus());
//			obj.setSubCategoria(model.getSubCategoria());
//			obj.setTag(model.getTag());
//			obj.setTitulo(model.getTitulo());
//			obj.setUrlanunciante(model.getUrlanunciante());
			lstShow.add(obj);
			
		}
		
		return lstShow;
	}
	
	public List<ProdutoModel> buscaProdutoByLinha(LinhaModel linha) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.linha = :linha ";

		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);

		query.setParameter("linha", linha);

		return query.getResultList();
	}
	
	public List<ProdutoModel> buscaProdutoByAnunciante(AnuncianteModel anunciante) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.anunciante = :anunciante order by dataincl desc";

		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);

		query.setParameter("anunciante", anunciante);

		return query.getResultList();
	}
	
	public List<ProdutoModel> buscaProdutoByAnuncianteNoMes(AnuncianteModel anunciante, Integer month) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.anunciante = :anunciante and month(dataincl) =:month order by dataincl desc";

		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);

		query.setParameter("anunciante", anunciante);
		query.setParameter("month", month);

		return query.getResultList();
	}
	
	
	public ProdutoModel buscaPorId(Long id) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.id = :id ";

		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);

		query.setParameter("id", id);

		List<ProdutoModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new ProdutoModel();
		}else{
			return resultado.get(0);
		}
	}

	public ProdutoModel buscaPorIdClonando(Long id) {
		ProdutoModel encontrado = buscaPorId(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), ProdutoModel.class);
	}

	
	
	
	public List<ProdutoModel> buscaPorCodigo(String codigo) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.codigo LIKE  :codigo ";

		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);

		query.setParameter("codigo", "%"+codigo+"%");

		return query.getResultList();
	}
	
	public List<ProdutoModel> buscaPorCategoria(CategoriaModel categoria) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.categoria = :categoria ";

		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);

		query.setParameter("categoria", categoria);

		return query.getResultList();
	}
	
	
	public List<ProdutoModel> buscaPorLinha(LinhaModel linha) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.linha = :linha ";

		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);

		query.setParameter("linha", linha);

		return query.getResultList();
	}
	

	public List<ProdutoModel> buscaPorTitulo(String titulo) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.titulo LIKE  :titulo ";

		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);

		query.setParameter("titulo", "%"+titulo+"%");

		return query.getResultList();
	}

	public List<ProdutoModel> buscaPorBrevedescricao(String brevedescricao) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.brevedescricao LIKE  :brevedescricao ";

		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);

		query.setParameter("brevedescricao", "%"+brevedescricao+"%");

		return query.getResultList();
	}

	public List<ProdutoModel> buscaPorDescricaocompleta(String descricaocompleta) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.descricaocompleta =  :descricaocompleta ";

		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);

		query.setParameter("descricaocompleta", descricaocompleta);

		return query.getResultList();
	}

	public List<ProdutoModel> buscaPorTag(String tag) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.tag LIKE  :tag ";

		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);

		query.setParameter("tag", "%"+tag+"%");

		return query.getResultList();
	}

	public List<ProdutoModel> buscaPorQtdademin(Integer qtdademin) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.qtdademin =  :qtdademin ";

		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);

		query.setParameter("qtdademin", qtdademin);

		return query.getResultList();
	}

	public List<ProdutoModel> buscaPorPreco(Double preco) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.preco =  :preco ";

		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);

		query.setParameter("preco", preco);

		return query.getResultList();
	}

	public List<ProdutoModel> buscaPorFaixadepreco(String faixadepreco) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.faixadepreco =  :faixadepreco ";

		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);

		query.setParameter("faixadepreco", faixadepreco);

		return query.getResultList();
	}

	public List<ProdutoModel> buscaPorImagem(String imagem) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.imagem =  :imagem ";

		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);

		query.setParameter("imagem", imagem);

		return query.getResultList();
	}

	public List<ProdutoModel> buscaPorUrlanunciante(String urlanunciante) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.urlanunciante =  :urlanunciante ";

		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);

		query.setParameter("urlanunciante", urlanunciante);

		return query.getResultList();
	}

	public List<ProdutoModel> buscaPorDataIncl(Calendar dataIncl) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.dataIncl =  :dataIncl ";

		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);

		query.setParameter("dataIncl", dataIncl);

		return query.getResultList();
	}

	public List<ProdutoModel> buscaPorDataAlt(Calendar dataAlt) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.dataAlt =  :dataAlt ";

		TypedQuery<ProdutoModel> query = manager.createQuery(jpql, ProdutoModel.class);

		query.setParameter("dataAlt", dataAlt);

		return query.getResultList();
	}

}