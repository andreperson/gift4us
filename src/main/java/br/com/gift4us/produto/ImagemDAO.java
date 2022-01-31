package br.com.gift4us.produto;

import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import com.google.gson.Gson;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Repository;

@Repository
public class ImagemDAO {

	private final String TABELA = ImagemModel.class.getSimpleName();

	@PersistenceContext
	private EntityManager manager;

	public ImagemDAO() {
	}

	@VisibleForTesting
	public ImagemDAO(EntityManager em) {
		this.manager = em;
	}

	@Transactional
	public void insere(ImagemModel imagem) {
		manager.persist(imagem);
		manager.flush();
		manager.detach(imagem);
	}


	@Transactional
	public void exclui(ImagemModel imagem) {
		manager.remove(manager.find(ImagemModel.class, imagem.getId()));
	}

	public List<ImagemModel> listaTudo() {
		String jpql = "SELECT p FROM " + TABELA + " p ORDER BY p.id desc";
		TypedQuery<ImagemModel> query = manager.createQuery(jpql, ImagemModel.class);
		return query.getResultList();
	}
	
	public ImagemModel buscaPorId(Long id) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.id = :id ";

		TypedQuery<ImagemModel> query = manager.createQuery(jpql, ImagemModel.class);

		query.setParameter("id", id);

		List<ImagemModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new ImagemModel();
		}else{
			return resultado.get(0);
		}
	}

	public ImagemModel buscaPorIdClonando(Long id) {
		ImagemModel encontrado = buscaPorId(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), ImagemModel.class);
	}

	public List<ImagemModel> buscaPorProduto(ProdutoModel produto) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.produto = :produto";

		TypedQuery<ImagemModel> query = manager.createQuery(jpql, ImagemModel.class);

		query.setParameter("produto", produto);

		return query.getResultList();
	}
	

}