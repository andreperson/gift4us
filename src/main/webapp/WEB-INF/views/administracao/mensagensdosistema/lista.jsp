<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs"%>
<my:template fluido="true"
	title="${mensagens.get('MensagensDoSistemaListaTituloDaPagina').valor}"
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
								value="<%=ListaDeURLs.FORMULARIO_INSERCAO_DE_MENSAGENSDOSISTEMA%>" />

							<div class="col-8">


								<h3 class="mb-0">${mensagens.get('MensagensDoSistemaListaTituloDaPagina').valor}

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
							<c:if test="${empty listaDeMensagensDoSistema}">
								<h6 class="heading-small text-muted mb-4">${mensagens.get('MensagensDoSistemaListaSemRegistro').valor}</h6>
							</c:if>
							<div class="pl-lg-4">
								<div class="row">

									<c:if test="${not empty listaDeMensagensDoSistema}">
										<table
											class="table table-striped table-bordered w-100 mt-3 dt-table">
											<thead>
												<tr>
													<th>
														${mensagens.get('MensagensDoSistemaListaPropriedade').valor}
													</th>
													<th>
														${mensagens.get('MensagensDoSistemaListaValor').valor}</th>
													<th>
														${mensagens.get('MensagensDoSistemaListaTela').valor}</th>
													<th class="text-center">${mensagens.get('MensagensDoSistemaListaAcoes').valor}</th>
												</tr>
											</thead>
											<tbody id="corpo-da-tabela">
												<c:forEach items="${listaDeMensagensDoSistema}"
													var="mensagensDoSistema">
													<tr>
														<td>${mensagensDoSistema.propriedade}</td>
														<td>${mensagensDoSistema.valor}</td>
														<td>${mensagensDoSistema.tela}</td>
														<td class="text-center text-nowrap"><c:url var="url"
																value="<%=ListaDeURLs.FORMULARIO_EDICAO_DE_MENSAGENSDOSISTEMA%>" />
															<sec:authorize
																access="hasRole('ROLE_MENSAGENSDOSISTEMA_ALTERAR')">
																<a class="btn btn-primary btn-sm"
																	href="${url}/${mensagensDoSistema.propriedade}"><i
																	class="fas fa-pencil-alt"></i></a>
															</sec:authorize> <sec:authorize
																access="hasRole('ROLE_MENSAGENSDOSISTEMA_EXCLUIR')">
																<a
																	class="btn btn-danger text-white btn-sm modal-excluir-link"
																	href="#" data-id="${mensagensDoSistema.propriedade}"
																	data-descricao="${mensagensDoSistema.valor}"><i
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

	<div class="col">
		<div class="card w-100">
			<div class="card-header bg-info">
				<c:url var="url"
					value="<%=ListaDeURLs.FORMULARIO_INSERCAO_DE_MENSAGENSDOSISTEMA%>" />
				<h4 class="float-start text-white mb-0">${mensagens.get('MensagensDoSistemaListaTituloDaPagina').valor}
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<a class="btn btn-warning btn-sm" href="${url}"><i
							class="fa fa-plus"></i></a>
					</sec:authorize>
				</h4>
				<div class="div-btn-export float-end"></div>
			</div>
			<div class="card-body">
				<c:if test="${not empty listaDeMensagensDoSistema}">
					<table
						class="table table-striped table-bordered w-100 mt-3 dt-table">
						<thead>
							<tr>
								<th>
									${mensagens.get('MensagensDoSistemaListaPropriedade').valor}</th>
								<th>${mensagens.get('MensagensDoSistemaListaValor').valor}
								</th>
								<th>${mensagens.get('MensagensDoSistemaListaTela').valor}</th>
								<th class="text-center">${mensagens.get('MensagensDoSistemaListaAcoes').valor}</th>
							</tr>
						</thead>
						<tbody id="corpo-da-tabela">
							<c:forEach items="${listaDeMensagensDoSistema}"
								var="mensagensDoSistema">
								<tr>
									<td>${mensagensDoSistema.propriedade}</td>
									<td>${mensagensDoSistema.valor}</td>
									<td>${mensagensDoSistema.tela}</td>
									<td class="text-center text-nowrap"><c:url var="url"
											value="<%=ListaDeURLs.FORMULARIO_EDICAO_DE_MENSAGENSDOSISTEMA%>" />
										<sec:authorize
											access="hasRole('ROLE_MENSAGENSDOSISTEMA_ALTERAR')">
											<a class="btn btn-primary btn-sm"
												href="${url}/${mensagensDoSistema.propriedade}"><i
												class="fas fa-pencil-alt"></i></a>
										</sec:authorize> <sec:authorize
											access="hasRole('ROLE_MENSAGENSDOSISTEMA_EXCLUIR')">
											<a
												class="btn btn-danger text-white btn-sm modal-excluir-link"
												href="#" data-id="${mensagensDoSistema.propriedade}"
												data-descricao="${mensagensDoSistema.valor}"><i
												class="fas fa-times"></i></a>
										</sec:authorize></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				<c:if test="${empty listaDeMensagensDoSistema}">
					<div class="alert alert-info">
						${mensagens.get('MensagensDoSistemaListaSemRegistro').valor}</div>
				</c:if>
			</div>
		</div>
	</div>
	<br>
	<sec:authorize access="hasRole('ROLE_MENSAGENSDOSISTEMA_EXCLUIR')">
		<c:url var="url"
			value="<%=ListaDeURLs.EXCLUSAO_DE_MENSAGENSDOSISTEMA%>" />
		<my:modal-excluir url="${url}" name="propriedade" />
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</sec:authorize>

</my:template>