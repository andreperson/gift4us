<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs"%>
<my:template fluido="true"
	title="${mensagens.get('ConfiguracoesDoSistemaListaTituloDaPagina').valor}"
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
								value="<%=ListaDeURLs.FORMULARIO_INSERCAO_DE_CONFIGURACOESDOSISTEMA%>" />

							<div class="col-8">


								<h3 class="mb-0">${mensagens.get('ConfiguracoesDoSistemaListaTituloDaPagina').valor}

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
							<c:if test="${empty listaDeConfiguracoesDoSistema}">
								<h6 class="heading-small text-muted mb-4">${mensagens.get('ConfiguracoesDoSistemaListaSemRegistro').valor}</h6>
							</c:if>
							<div class="pl-lg-4">
								<div class="row">

									<c:if test="${not empty listaDeConfiguracoesDoSistema}">
										<table
											class="table table-striped table-bordered w-100 mt-3 dt-table">
											<thead>
												<tr>
													<th>
														${mensagens.get('ConfiguracoesDoSistemaListaPropriedade').valor}
													</th>
													<th>
														${mensagens.get('ConfiguracoesDoSistemaListaValor').valor}
													</th>
													<th class="text-center">${mensagens.get('ConfiguracoesDoSistemaListaAcoes').valor}</th>
												</tr>
											</thead>
											<tbody id="corpo-da-tabela">
												<c:forEach items="${listaDeConfiguracoesDoSistema}"
													var="configuracoesDoSistema">
													<tr>
														<td>${configuracoesDoSistema.propriedade}</td>
														<td>${configuracoesDoSistema.valor}</td>
														<td class="text-center text-nowrap"><c:url var="url"
																value="<%=ListaDeURLs.FORMULARIO_EDICAO_DE_CONFIGURACOESDOSISTEMA%>" />
															<sec:authorize
																access="hasRole('ROLE_CONFIGURACOESDOSISTEMA_ALTERAR')">
																<a class="btn btn-primary btn-sm"
																	href="${url}/${configuracoesDoSistema.propriedade}"><i
																	class="fas fa-pencil-alt"></i></a>
															</sec:authorize> <sec:authorize
																access="hasRole('ROLE_CONFIGURACOESDOSISTEMA_EXCLUIR')">
																<a
																	class="btn btn-danger text-white btn-sm modal-excluir-link"
																	href="#"
																	data-id="${configuracoesDoSistema.propriedade}"
																	data-descricao="${configuracoesDoSistema.valor}"><i
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
	<sec:authorize access="hasRole('ROLE_CONFIGURACOESDOSISTEMA_EXCLUIR')">
		<c:url var="url"
			value="<%=ListaDeURLs.EXCLUSAO_DE_CONFIGURACOESDOSISTEMA%>" />
		<my:modal-excluir url="${url}" name="propriedade" />
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</sec:authorize>

</my:template>