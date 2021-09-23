<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs"%>
<my:template fluido="true"
	title="${mensagens.get('HistoricoDoSistemaListaTituloDaPagina').valor}"
	cssFiles="datatables/datatables.min.css"
	jsFiles="moment.min.js,datatables/datatables.min.js,datatables/datetime-moment.js,datatables/dt-historico-sistema.js">


	<!-- Page content -->
	<div class="container-fluid mt--1" style="padding-top: 50px;">
		<div class="row">
			<div class="col-xl-12 order-xl-1">
				<div class="card bg-secondary shadow">
					<div class="card-header bg-white border-0">
						<div class="row align-items-center">

							<c:url var="url"
								value="<%=ListaDeURLs.FORMULARIO_INSERCAO_DE_HISTORICODOSISTEMA%>" />

							<div class="col-8">
								<h3 class="mb-0">${mensagens.get('HistoricoDoSistemaListaTituloDaPagina').valor}
								</h3>
							</div>
						</div>
					</div>
					<div class="card-body">
						<form>
							<c:if test="${empty listaDeHistoricoDoSistema}">
								<h6 class="heading-small text-muted mb-4">${mensagens.get('HistoricoDoSistemaListaSemRegistro').valor}</h6>
							</c:if>
							<div class="pl-lg-4">
								<div class="row">
									<div class="card-body">
										<c:if test="${not empty listaDeHistoricoDoSistema}">
											<table
												class="table table-striped table-bordered w-100 mt-3 dt-table">
												<thead>
													<tr>
														<th>
															${mensagens.get('HistoricoDoSistemaListaId').valor}</th>
														<th>
															${mensagens.get('HistoricoDoSistemaListaLogin').valor}</th>
														<th>
															${mensagens.get('HistoricoDoSistemaListaNome').valor}</th>
														<th>
															${mensagens.get('HistoricoDoSistemaListaDatahora').valor}
														</th>
														<th>
															${mensagens.get('HistoricoDoSistemaListaLocal').valor}</th>
														<th>
															${mensagens.get('HistoricoDoSistemaListaAcao').valor}</th>
														<th>
															${mensagens.get('HistoricoDoSistemaListaDados').valor}</th>

													</tr>
												</thead>
												<tbody id="corpo-da-tabela">
													<c:forEach items="${listaDeHistoricoDoSistema}"
														var="historicoDoSistema">
														<tr>
															<td>${historicoDoSistema.id}</td>
															<td>${historicoDoSistema.login}</td>
															<td>${historicoDoSistema.nome}</td>
															<td><fmt:formatDate
																	pattern="dd/MM/yyyy HH:mm:ss SSS"
																	value="${historicoDoSistema.datahora.time}" var="data" />${data}</td>
															<td>${historicoDoSistema.local}</td>
															<td>${historicoDoSistema.acao}</td>
															<td>${historicoDoSistema.dados}</td>

														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>

									</div>
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
					value="<%=ListaDeURLs.FORMULARIO_INSERCAO_DE_HISTORICODOSISTEMA%>" />
				<h4 class="float-start text-white mb-0">${mensagens.get('HistoricoDoSistemaListaTituloDaPagina').valor}

				</h4>
				<div class="div-btn-export float-end"></div>
			</div>
			<div class="card-body">
				<c:if test="${not empty listaDeHistoricoDoSistema}">
					<table
						class="table table-striped table-bordered w-100 mt-3 dt-table">
						<thead>
							<tr>
								<th>${mensagens.get('HistoricoDoSistemaListaId').valor}</th>
								<th>${mensagens.get('HistoricoDoSistemaListaLogin').valor}
								</th>
								<th>${mensagens.get('HistoricoDoSistemaListaNome').valor}</th>
								<th>
									${mensagens.get('HistoricoDoSistemaListaDatahora').valor}</th>
								<th>${mensagens.get('HistoricoDoSistemaListaLocal').valor}
								</th>
								<th>${mensagens.get('HistoricoDoSistemaListaAcao').valor}</th>
								<th>${mensagens.get('HistoricoDoSistemaListaDados').valor}
								</th>

							</tr>
						</thead>
						<tbody id="corpo-da-tabela">
							<c:forEach items="${listaDeHistoricoDoSistema}"
								var="historicoDoSistema">
								<tr>
									<td>${historicoDoSistema.id}</td>
									<td>${historicoDoSistema.login}</td>
									<td>${historicoDoSistema.nome}</td>
									<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss SSS"
											value="${historicoDoSistema.datahora.time}" var="data" />${data}</td>
									<td>${historicoDoSistema.local}</td>
									<td>${historicoDoSistema.acao}</td>
									<td>${historicoDoSistema.dados}</td>

								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				<c:if test="${empty listaDeHistoricoDoSistema}">
					<div class="alert alert-info">
						${mensagens.get('HistoricoDoSistemaListaSemRegistro').valor}</div>
				</c:if>
			</div>
		</div>
	</div>
	<br>
	<sec:authorize access="hasRole('ROLE_HISTORICODOSISTEMA_EXCLUIR')">
		<c:url var="url"
			value="<%=ListaDeURLs.EXCLUSAO_DE_HISTORICODOSISTEMA%>" />
		<my:modal-excluir url="${url}" name="id" />
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</sec:authorize>

</my:template>