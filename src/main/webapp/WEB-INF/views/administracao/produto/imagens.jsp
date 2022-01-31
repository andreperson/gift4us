<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs"%>
<c:url value="${urlRecursos}image" var="urlrecursos" />
<c:url value="${urlpadrao}_produtos" var="urlproduto" />


<c:set var="cssFiles"
	value="../css/bootstrap/bootstrap-toggle.min.css,../css/bootstrap/select2.min.css,../css/bootstrap/select2-bootstrap4.min.css" />
<c:set var="jsFiles"
	value="/js/ProdutoFormulario.js,/js/jquery/bootstrap-toggle.min.js,js/jquery/select2.min.js,/js/jquery/select2-pt-BR.js" />


<my:template fluido="false"
	title="${mensagens.get('ImagensFormularioTituloDaPagina').valor}"
	cssFiles="${cssFiles}" jsFiles="${jsFiles}">

	<c:if test="${not empty produto.id}">
		<c:url var="url" value="<%=ListaDeURLs.INSERCAO_DE_IMAGEM%>" />
		<c:url var="urlexclui" value="<%=ListaDeURLs.EXCLUSAO_DE_IMAGEM%>" />
	</c:if>


	<!-- Page content -->
	<div class="container-fluid mt--1" style="padding-top: 50px;">
		<div class="row">
			<div class="col-xl-12 order-xl-1">
				<div class="card bg-secondary shadow">
					<div class="card-header bg-white border-0">
						<div class="row align-items-center">
							<div class="col-8">
								<h3 class="mb-0">${mensagens.get('ImagensFormularioTituloDaPagina').valor}
								</h3>
							</div>
						</div>
					</div>
					<div class="card-body">
						<form id="ata-cadastro"
							action="${url}?${_csrf.parameterName}=${_csrf.token}"
							enctype="multipart/form-data" method="post">

							<input type="hidden" id="${_csrf.parameterName}"
								name="${_csrf.parameterName}" value="${_csrf.token}" />
							<c:if test="${not empty produto.id}">
								<input type="hidden" name="produtoid" id="produtoid" value="${produto.id}" />
								<input type="hidden" name="anuncianteid" id="anuncianteid" value="${produto.anunciante.id}" />
							</c:if>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="titulo">ID do
									Produto: ${produto.id}</label>
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="arquivo">${mensagens.get('ProdutoFormularioImagem').valor}:</label>
								<input class="form-control texto-html" name="arquivo"
									id="arquivo" type="file" data-msbc-required="Campo obrigatório">
							</div>

							<div class="mb-3">
								<div class="col-md-4 offset-md-4">
									<c:if test="${not empty produto.id}">
										<input
											class="btn btn-primary w-100 msbc-validator-button-submit"
											type="submit"
											value="${mensagens.get('ProdutoFormularioBotaoImagemInsere').valor}" />
									</c:if>
								</div>
							</div>
						</form>

						<div>
							<c:if test="${not empty lstImagem}">
								<table
									class="table table-striped table-bordered w-100 mt-3 dt-table">
									<thead>
										<tr>
											<tr>
												<th>${mensagens.get('ImagemListaId').valor}</th>
												<th>${mensagens.get('ImagemListaNome').valor}</th>
												<th>${mensagens.get('ImagemListaProduto').valor}</th>
												<th>${mensagens.get('ImagemListaMiniatura').valor}</th>
												<th class="text-center">${mensagens.get('ImagemListaAcoes').valor}</th>
											</tr>
										</tr>
									</thead>
									<tbody id="corpo-da-tabela">
										<c:forEach items="${lstImagem}" var="img">
											<c:set var="urlprodutomontada" scope="application" value="${urlmodelo}/modelo-220x330.jpg" />
											<c:if test="${not empty img.imagem}">
											<c:set var="urlprodutomontada" scope="application"
												value="${urlproduto}/${img.produto.anunciante.id}/${img.produto.id}/${img.imagem}" />
											</c:if>
											<tr>
												<td>${img.id}</td>
												<td>${img.imagem}</td>
												<td>${img.produto.id}</td>
												<td><img src="${urlprodutomontada}" width="35" class="img-responsive" /></td>
												<td class="text-center text-nowrap"><sec:authorize
														access="hasAnyRole('ROLE_ANUNCIANTE', 'ROLE_GERENCIAL', 'ROLE_CONFIGURACOES', 'ROLE_ADMIN')">
														<a href="${urlexclui}/${img.id}"><i
															class="fas fa-times"></i> Excluir Imagem</a>
													</sec:authorize></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</c:if>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
	<br>
</my:template>