<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs"%>


<my:template fluido="false"
	title="${mensagens.get('AnuncianteFormularioTituloDaPagina').valor}"
	cssFiles="${cssFiles}" jsFiles="${jsFiles}">

		<!-- Page content -->
		<div class="container-fluid mt--1" style="padding-top: 50px;">
			<div class="row">
				<div class="col-xl-12 order-xl-1">
					<div class="card bg-secondary shadow">
						<div class="card-header bg-white border-0">
							<div class="row align-items-center">

								<c:url var="url"
									value="<%=ListaDeURLs.FORMULARIO_INSERCAO_DE_ANUNCIANTE%>" />

								<div class="col-8">


									<h3 class="mb-0">${mensagens.get('AnuncianteListaTituloDaPagina').valor}

										<sec:authorize access="hasAnyRole('ROLE_CONFIGURACOES', 'ROLE_ADMIN')">
											<a class="btn btn-warning btn-sm" title="Incluir" href="${url}"><i
												class="fa fa-plus"></i></a>
										</sec:authorize>
									</h3>
								</div>
							</div>
						</div>
						<div class="card-body">
							<form>
								<c:if test="${empty listaDeAnunciante}">
									<h6 class="heading-small text-muted mb-4">${mensagens.get('AnuncianteListaSemRegistro').valor}</h6>
								</c:if>
								<div class="pl-lg-4">
									<div class="row">

										<c:if test="${not empty listaDeAnunciante}">
											<table class="table table-striped table-bordered dt-table">
												<thead>
													<tr>
														<th>${mensagens.get('AnuncianteListaId').valor}</th>
														<th>${mensagens.get('AnuncianteListaRazaosocial').valor}</th>
														<th>${mensagens.get('AnuncianteListaFantasia').valor}</th>
														<th>${mensagens.get('AnuncianteListaEmail').valor}</th>
														<th>${mensagens.get('AnuncianteListaDataIncl').valor}</th>
														<th>
															${mensagens.get('AnuncianteListaAnuncianteTipo').valor}</th>
														<th class="text-center">${mensagens.get('AnuncianteListaAcoes').valor}</th>
													</tr>
												</thead>
												<tbody id="corpo-da-tabela">
													<c:forEach items="${listaDeAnunciante}" var="anunciante">
														<tr>
															<td>${anunciante.id}</td>
															<td>${anunciante.razaosocial}</td>
															<td>${anunciante.fantasia}</td>
															<td>${anunciante.email}</td>
															<td><fmt:formatDate pattern="dd/MM/yyyy"
																	value="${anunciante.dataIncl.time}" var="data" />${data}</td>
															<td>${anunciante.anuncianteTipo.nome}</td>
															<td class="text-center text-nowrap"><c:url var="url"
																	value="<%=ListaDeURLs.FORMULARIO_EDICAO_DE_ANUNCIANTE%>" />
																<sec:authorize access="hasAnyRole('ROLE_CONFIGURACOES', 'ROLE_ADMIN')">
																	<a class="btn btn-primary btn-sm"
																		href="${url}/${anunciante.id}"><i
																		class="fas fa-pencil-alt"></i></a>
																</sec:authorize> 
																<sec:authorize access="hasAnyRole('ROLE_CONFIGURACOES', 'ROLE_ADMIN')">
																	<a
																		class="btn btn-danger text-white btn-sm modal-excluir-link"
																		href="#" data-id="${anunciante.id}"
																		data-descricao="${anunciante.razaosocial}"><i
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
	<sec:authorize access="hasAnyRole('ROLE_CONFIGURACOES', 'ROLE_ADMIN')">
		<c:url var="url" value="<%=ListaDeURLs.EXCLUSAO_DE_ANUNCIANTE%>" />
		<my:modal-excluir url="${url}"  name="id" />
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</sec:authorize>
</my:template>