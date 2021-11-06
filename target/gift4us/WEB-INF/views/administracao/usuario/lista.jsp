<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs"%>
<my:template fluido="true"
	title="${mensagens.get('UsuarioListaTituloDaPagina').valor}"
	cssFiles="datatables/datatables.min.css"
	jsFiles="moment.min.js,datatables/datatables.min.js,datatables/datetime-moment.js,datatables/dt-default.js">


	<div class="container-fluid mt--1" style="padding-top: 50px;">
		<div class="row">
			<div class="col-xl-12 order-xl-1">
				<div class="card bg-secondary shadow">
					<div class="card-header bg-white border-0">
						<div class="row align-items-center">
							<c:url var="url"
								value="<%=ListaDeURLs.FORMULARIO_INSERCAO_DE_USUARIO%>" />
							<div class="col-8">
								<h3 class="mb-0">${mensagens.get('UsuarioListaTituloDaPagina').valor}
									<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_GERENCIAL')">
										<a class="btn btn-warning btn-sm" title="Incluir"
											href="${url}"><i class="fa fa-plus"></i></a>
									</sec:authorize>
								</h3>
							</div>
						</div>
					</div>
					<div class="card-body">
						<form>
							<c:if test="${empty listaDeUsuario}">
								<h6 class="heading-small text-muted mb-4">${mensagens.get('UsuarioListaSemRegistro').valor}</h6>
							</c:if>
							<div class="pl-lg-4">
								<div class="row">

									<c:if test="${not empty listaDeUsuario}">
										<table
											class="table table-striped table-bordered w-100 mt-3 dt-table">
											<thead>
												<tr>
													<th>${mensagens.get('UsuarioListaId').valor}</th>
													<th>${mensagens.get('UsuarioListaLogin').valor}</th>
													<th>${mensagens.get('UsuarioListaNome').valor}</th>
													<th>${mensagens.get('UsuarioListaGrupo').valor}</th>
													<th>${mensagens.get('UsuarioListaAnunciante').valor}</th>
													<th class="text-center">${mensagens.get('UsuarioListaAcoes').valor}</th>
												</tr>
											</thead>
											<tbody id="corpo-da-tabela">
												<c:forEach items="${listaDeUsuario}" var="usuario">
													<tr>
														<td>${usuario.id}</td>
														<td>${usuario.login}</td>
														<td>${usuario.nome}</td>
														<td><c:forEach items="${usuario.listaDeGrupo}"
																var="grupo">
																<span style="display: block">${grupo.nome}</span>
															</c:forEach></td>
														<td>${usuario.anunciante.razaosocial}</td>
														<td class="text-center text-nowrap"><sec:authorize
																access="hasAnyRole('ROLE_ADMIN','ROLE_GERENCIAL')">
																<a
																	class="btn btn-warning text-white btn-sm modal-redefinir-senha-link"
																	data-id="${usuario.id}"
																	data-descricao="${usuario.login}"><i
																	class="fas fa-key"></i></a>
															</sec:authorize> <c:url var="url"
																value="<%=ListaDeURLs.FORMULARIO_EDICAO_DE_USUARIO%>" />
															<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_GERENCIAL')">
																<a class="btn btn-primary btn-sm"
																	href="${url}/${usuario.id}"><i
																	class="fas fa-pencil-alt"></i></a>
															</sec:authorize> <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_GERENCIAL')">
																<a
																	class="btn btn-danger text-white btn-sm modal-excluir-link"
																	href="#" data-id="${usuario.id}"
																	data-descricao="${usuario.login}"><i
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

	<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_GERENCIAL')">
		<c:url var="url" value="<%=ListaDeURLs.EXCLUSAO_DE_USUARIO%>" />
		<my:modal-excluir url="${url}" name="id" />
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</sec:authorize>
	<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_GERENCIAL')">
		<c:url var="url" value="<%=ListaDeURLs.REDEFINIR_SENHA%>" />
		<my:modal-redefinir-senha url="${url}" name="id" />
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</sec:authorize>
</my:template>