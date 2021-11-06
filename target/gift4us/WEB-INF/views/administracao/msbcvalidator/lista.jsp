<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs" %>
<my:template fluido="true" title="${mensagens.get('MsbcValidatorListaTituloDaPagina').valor}"
	cssFiles="datatables/datatables.min.css" 
	jsFiles="moment.min.js,datatables/datatables.min.js,datatables/datetime-moment.js,datatables/dt-default.js">
	
	<div class="col">
		<div class="card w-100">
			<div class="card-header bg-info">
				<c:url var="url" value="<%=ListaDeURLs.FORMULARIO_INSERCAO_DE_MSBCVALIDATOR %>" />
				<h4 class="float-start text-white mb-0">${mensagens.get('MsbcValidatorListaTituloDaPagina').valor}
				<sec:authorize access="hasRole('ROLE_MSBCVALIDATOR_INSERIR')">
				<a class="btn btn-warning btn-sm" href="${url}"><i class="fa fa-plus"></i></a>
			</sec:authorize>
				</h4>
				<div class="div-btn-export float-end"></div>
			</div>
			<div class="card-body">
				<c:if test="${not empty listaDeMsbcValidator}">
					<table class="table table-striped table-bordered w-100 mt-3 dt-table">
						<thead>
							<tr>
								<th>
								${mensagens.get('MsbcValidatorListaId').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaRequiredOnly').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaMaxLengthOnly').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaSomenteNumerosOnly').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaRemoveAcentosOnly').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaTextDefault').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaTextSomenteNumero').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaTextRemoveAcentos').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaTextEmail').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaCpfSomenteNumeros').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaCnpjSomenteNumeros').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaTelDDD').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaTelNumero').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaCepSomenteNumeros').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaDataTeste').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaDataTime').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaDoubleTeste').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaIntegerTeste').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaLongTeste').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaTimeStampTeste').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaTextAreaSimples').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaTextAreaHtml').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaBooleanTeste').valor}
							</th>
							<th>
								${mensagens.get('MsbcValidatorListaArquivoTeste').valor}
							</th>
								<th class="text-center">${mensagens.get('MsbcValidatorListaAcoes').valor}</th>
							</tr>
						</thead>
						<tbody id="corpo-da-tabela">
							<c:forEach items="${listaDeMsbcValidator}" var="msbcValidator">
							<tr>
								<td>${msbcValidator.id}</td>
							<td>${msbcValidator.requiredOnly}</td>
							<td>${msbcValidator.maxLengthOnly}</td>
							<td>${msbcValidator.somenteNumerosOnly}</td>
							<td>${msbcValidator.removeAcentosOnly}</td>
							<td>${msbcValidator.textDefault}</td>
							<td>${msbcValidator.textSomenteNumero}</td>
							<td>${msbcValidator.textRemoveAcentos}</td>
							<td>${msbcValidator.textEmail}</td>
							<td>${msbcValidator.cpfSomenteNumeros}</td>
							<td>${msbcValidator.cnpjSomenteNumeros}</td>
							<td>${msbcValidator.telDDD}</td>
							<td>${msbcValidator.telNumero}</td>
							<td>${msbcValidator.cepSomenteNumeros}</td>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${msbcValidator.dataTeste.time}" var="data" />${data}</td>
							<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${msbcValidator.dataTime.time}" var="data" />${data}</td>
							<td>${msbcValidator.doubleTeste}</td>
							<td>${msbcValidator.integerTeste}</td>
							<td>${msbcValidator.longTeste}</td>
							<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss SSS" value="${msbcValidator.timeStampTeste.time}" var="data" />${data}</td>
							<td>${msbcValidator.textAreaSimples}</td>
							<td>${msbcValidator.textAreaHtml}</td>
							<td>${msbcValidator.booleanTeste}</td>
							<td>
								<c:if test="${not empty msbcValidator.arquivoTeste}">								
									<c:url var="url" value="/administracao/msbcvalidator/arquivo/mostra/${msbcValidator.id}/ArquivoTeste" />
									<a href="${url}" target="_blanck">ArquivoTeste</a>
								</c:if>
							</td>
								<td class="text-center text-nowrap">
								<c:url var="url" value="<%=ListaDeURLs.FORMULARIO_EDICAO_DE_MSBCVALIDATOR %>" />
								<sec:authorize access="hasRole('ROLE_MSBCVALIDATOR_ALTERAR')">
									<a class="btn btn-primary btn-sm" href="${url}/${msbcValidator.id}"><i class="fas fa-pencil-alt"></i></a>
								</sec:authorize>
								<sec:authorize access="hasRole('ROLE_MSBCVALIDATOR_EXCLUIR')">
									<a class="btn btn-danger text-white btn-sm modal-excluir-link" href="#" data-id="${msbcValidator.id}" data-descricao="${msbcValidator.requiredOnly}"><i class="fas fa-times"></i></a>
								</sec:authorize>
							</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				<c:if test="${empty listaDeMsbcValidator}">
					<div class="alert alert-info">
						${mensagens.get('MsbcValidatorListaSemRegistro').valor}
					</div>
				</c:if>
			</div>
		</div>
	</div>
	<sec:authorize access="hasRole('ROLE_MSBCVALIDATOR_EXCLUIR')">
		<c:url var="url" value="<%=ListaDeURLs.EXCLUSAO_DE_MSBCVALIDATOR %>" />
		<my:modal-excluir url="${url}"  name="id" />
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</sec:authorize>
	
</my:template>