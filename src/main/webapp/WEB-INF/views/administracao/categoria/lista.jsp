<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs"%>
<my:template fluido="true"
	title="${mensagens.get('CategoriaListaTituloDaPagina').valor}"
	cssFiles="datatables/datatables.min.css"
	jsFiles="moment.min.js,datatables/datatables.min.js,datatables/datetime-moment.js,datatables/dt-default.js">
	<!-- Page content -->
	<div class="container-fluid mt--1" style="padding-top: 50px;">
		<div class="row">
			<div class="col-xl-12 order-xl-1">
				<div class="card bg-secondary shadow">
					<div class="card-header bg-white border-0">
						<div class="row align-items-center">

							<c:url var="url"
								value="<%=ListaDeURLs.FORMULARIO_INSERCAO_DE_CATEGORIA%>" />

							<div class="col-8">


								<h3 class="mb-0">${mensagens.get('CategoriaListaTituloDaPagina').valor}

									<sec:authorize access="hasRole('ROLE_ADMIN')">
										<a class="btn btn-warning btn-sm" title="Incluir"
											href="${url}"><i class="fa fa-plus"></i></a>
									</sec:authorize>
								</h3>
							</div>
						</div>
					</div>
					<div class="card-body">
						<form>
							<c:if test="${empty listaDeCategoria}">
								<h6 class="heading-small text-muted mb-4">${mensagens.get('CategoriaListaSemRegistro').valor}</h6>
							</c:if>
							<div class="pl-lg-4">
								<div class="row">

									<c:if test="${not empty listaDeCategoria}">
										<table
											class="table table-striped table-bordered w-100 mt-3 dt-table">
											<thead>
												<tr>
													<th>${mensagens.get('CategoriaListaId').valor}</th>
													<th>${mensagens.get('CategoriaListaNome').valor}</th>
													<th>${mensagens.get('CategoriaListaImagem').valor}</th>
													<th>${mensagens.get('CategoriaListaDataAlt').valor}</th>
													<th class="text-center">${mensagens.get('CategoriaListaAcoes').valor}</th>
												</tr>
											</thead>
											<tbody id="corpo-da-tabela">
												<c:forEach items="${listaDeCategoria}" var="categoria">
													<tr>
														<td>${categoria.id}</td>
														<td>${categoria.nome}</td>
														<td>${categoria.imagem}</td>
														<td><fmt:formatDate pattern="dd/MM/yyyy"
																value="${categoria.dataAlt.time}" var="data" />${data}</td>
														<td class="text-center text-nowrap"><c:url var="url"
																value="<%=ListaDeURLs.FORMULARIO_EDICAO_DE_CATEGORIA%>" />
															<sec:authorize access="hasAnyRole('ROLE_CONFIGURACOES', 'ROLE_ADMIN')">
																<a class="btn btn-primary btn-sm"
																	href="${url}/${categoria.id}"><i
																	class="fas fa-pencil-alt"></i></a>
															</sec:authorize> <sec:authorize
																access="hasAnyRole('ROLE_EXCLUIR', 'ROLE_ADMIN')">
																<a
																	class="btn btn-danger text-white btn-sm modal-excluir-link"
																	href="#" data-id="${categoria.id}"
																	data-descricao="${categoria.nome}"><i
																	class="fas fa-times"></i></a>
															</sec:authorize></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:if>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<br>
	<sec:authorize access="hasAnyRole('ROLE_EXCLUIR', 'ROLE_ADMIN')">
		<c:url var="url" value="<%=ListaDeURLs.EXCLUSAO_DE_CATEGORIA%>" />
		<my:modal-excluir url="${url}" name="id" />
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</sec:authorize>

</my:template>