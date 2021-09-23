<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs" %>
<my:template fluido="true" title="${mensagens.get('ModeloListaTituloDaPagina').valor}"
	cssFiles="datatables/datatables.min.css" 
	jsFiles="moment.min.js,datatables/datatables.min.js,datatables/datetime-moment.js,datatables/dt-default.js">
	
	<div class="col">
		<div class="card w-100">
			<div class="card-header bg-info">
				<c:url var="url" value="<%=ListaDeURLs.FORMULARIO_INSERCAO_DE_MODELO %>" />
				<h4 class="float-start text-white mb-0">${mensagens.get('ModeloListaTituloDaPagina').valor}
				<sec:authorize access="hasRole('ROLE_MODELO_INSERIR')">
				<a class="btn btn-warning btn-sm" href="${url}"><i class="fa fa-plus"></i></a>
			</sec:authorize>
				</h4>
				<div class="div-btn-export float-end"></div>
			</div>
			<div class="card-body">
				<c:if test="${not empty listaDeModelo}">
					<table class="table table-striped table-bordered w-100 mt-3 dt-table">
						<thead>
							<tr>
								<th>
								${mensagens.get('ModeloListaId').valor}
							</th>
							<th>
								${mensagens.get('ModeloListaDescricao').valor}
							</th>
							<th>
								${mensagens.get('ModeloListaTitulo').valor}
							</th>
							<th>
								${mensagens.get('ModeloListaCorTitulo').valor}
							</th>
							<th>
								${mensagens.get('ModeloListaImagemUnica').valor}
							</th>
							<th>
								${mensagens.get('ModeloListaImagemCabecalho').valor}
							</th>
							<th>
								${mensagens.get('ModeloListaImagemRodape').valor}
							</th>
							<th>
								${mensagens.get('ModeloListaAtivo').valor}
							</th>
								<th class="text-center">${mensagens.get('ModeloListaAcoes').valor}</th>
							</tr>
						</thead>
						<tbody id="corpo-da-tabela">
							<c:forEach items="${listaDeModelo}" var="modelo">
							<tr>
								<td>${modelo.id}</td>
							<td>${modelo.descricao}</td>
							<td>${modelo.titulo}</td>
							<td>${modelo.corTitulo}</td>
							<td>
								<c:if test="${not empty modelo.imagemUnica}">								
									<c:url var="url" value="/administracao/modelo/arquivo/mostra/${modelo.id}/ImagemUnica" />
									<a href="${url}" target="_blanck">ImagemUnica</a>
								</c:if>
							</td>
							<td>
								<c:if test="${not empty modelo.imagemCabecalho}">								
									<c:url var="url" value="/administracao/modelo/arquivo/mostra/${modelo.id}/ImagemCabecalho" />
									<a href="${url}" target="_blanck">ImagemCabecalho</a>
								</c:if>
							</td>
							<td>
								<c:if test="${not empty modelo.imagemRodape}">								
									<c:url var="url" value="/administracao/modelo/arquivo/mostra/${modelo.id}/ImagemRodape" />
									<a href="${url}" target="_blanck">ImagemRodape</a>
								</c:if>
							</td>
							<td>${modelo.ativo}</td>
								<td class="text-center text-nowrap">
								<c:url var="url" value="<%=ListaDeURLs.FORMULARIO_EDICAO_DE_MODELO %>" />
								<sec:authorize access="hasRole('ROLE_MODELO_ALTERAR')">
									<a class="btn btn-primary btn-sm" href="${url}/${modelo.id}"><i class="fas fa-pencil-alt"></i></a>
								</sec:authorize>
								<sec:authorize access="hasRole('ROLE_MODELO_EXCLUIR')">
									<a class="btn btn-danger text-white btn-sm modal-excluir-link" href="#" data-id="${modelo.id}" data-descricao="${modelo.descricao}"><i class="fas fa-times"></i></a>
								</sec:authorize>
							</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				<c:if test="${empty listaDeModelo}">
					<div class="alert alert-info">
						${mensagens.get('ModeloListaSemRegistro').valor}
					</div>
				</c:if>
			</div>
		</div>
	</div>
	<sec:authorize access="hasRole('ROLE_MODELO_EXCLUIR')">
		<c:url var="url" value="<%=ListaDeURLs.EXCLUSAO_DE_MODELO %>" />
		<my:modal-excluir url="${url}"  name="id" />
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</sec:authorize>
	
</my:template>