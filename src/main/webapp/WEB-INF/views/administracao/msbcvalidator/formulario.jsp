<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs" %>
<c:set var="cssFiles" value="bootstrap/bootstrap-toggle.min.css,jqte/jquery-te.css" />
<c:set var="jsFiles" value="jquery/bootstrap-toggle.min.js,jquery/jquery-te.min.js,MsbcValidatorFormulario.js" />
<my:template fluido="false" title="${mensagens.get('MsbcValidatorFormularioTituloDaPagina').valor}" cssFiles="${cssFiles}" jsFiles="${jsFiles}">
	<c:if test="${empty msbcValidator.id}">
		<c:url var="url" value="<%=ListaDeURLs.INSERCAO_DE_MSBCVALIDATOR %>" />
	</c:if>
	<c:if test="${not empty msbcValidator.id}">
		<c:url var="url" value="<%=ListaDeURLs.EDICAO_DE_MSBCVALIDATOR %>" />
	</c:if>
	<div class="col">
		<div class="card w-100">
			<div class="card-header bg-info">
				<h4 class="text-white mb-0">${mensagens.get('MsbcValidatorFormularioTituloDaPagina').valor}</h4>
			</div>
			<div class="card-body">
				<form class="msbc-validator-form" id="formulario-form" name="formulario" action="${url}?${_csrf.parameterName}=${_csrf.token}" method="POST" enctype='multipart/form-data'>
				<input type="hidden" id="${_csrf.parameterName}" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<c:if test="${not empty msbcValidator.id}">
					<input type="hidden" name="id" id="id" value="${msbcValidator.id}" />
				</c:if>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="requiredOnly">${mensagens.get('MsbcValidatorFormularioRequiredOnly').valor}:</label>
					<input type="text" id="requiredOnly" name="requiredOnly" class="form-control msbc-validator" value="${msbcValidator.requiredOnly}" 
						data-msbc-required="Campo obrigatório" />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="maxLengthOnly">${mensagens.get('MsbcValidatorFormularioMaxLengthOnly').valor}:</label>
					<input type="text" id="maxLengthOnly" name="maxLengthOnly" class="form-control msbc-validator" value="${msbcValidator.maxLengthOnly}" 
						data-msbc-maxlength="15|O campo deve ter no máximo {value} caracteres" />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="somenteNumerosOnly">${mensagens.get('MsbcValidatorFormularioSomenteNumerosOnly').valor}:</label>
					<input type="text" id="somenteNumerosOnly" name="somenteNumerosOnly" class="form-control msbc-validator" value="${msbcValidator.somenteNumerosOnly}" 
						data-msbc-type-value="somente-numero" />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="removeAcentosOnly">${mensagens.get('MsbcValidatorFormularioRemoveAcentosOnly').valor}:</label>
					<input type="text" id="removeAcentosOnly" name="removeAcentosOnly" class="form-control msbc-validator" value="${msbcValidator.removeAcentosOnly}" 
						data-msbc-change-character="áàãâäéèêëíìîïóòõôöúùûüçÁÀÃÂÄÉÈÊËÍÌÎÏÓÒÕÖÔÚÙÛÜÇ|aaaaaeeeeiiiiooooouuuucAAAAAEEEEIIIIOOOOOUUUUC" />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="textDefault">${mensagens.get('MsbcValidatorFormularioTextDefault').valor}:</label>
					<input type="text" id="textDefault" name="textDefault" class="form-control msbc-validator" value="${msbcValidator.textDefault}" 
						data-msbc-required="Campo obrigatório"
						data-msbc-maxlength="30|O campo deve ter no máximo {value} caracteres" />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="textSomenteNumero">${mensagens.get('MsbcValidatorFormularioTextSomenteNumero').valor}:</label>
					<input type="text" id="textSomenteNumero" name="textSomenteNumero" class="form-control msbc-validator" value="${msbcValidator.textSomenteNumero}" 
						data-msbc-required="Campo obrigatório"
						data-msbc-maxlength="35|O campo deve ter no máximo {value} caracteres" 
						data-msbc-type-value="somente-numero" />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="textRemoveAcentos">${mensagens.get('MsbcValidatorFormularioTextRemoveAcentos').valor}:</label>
					<input type="text" id="textRemoveAcentos" name="textRemoveAcentos" class="form-control msbc-validator" value="${msbcValidator.textRemoveAcentos}" 
						data-msbc-required="Campo obrigatório"
						data-msbc-maxlength="40|O campo deve ter no máximo {value} caracteres"
						data-msbc-change-character="áàãâäéèêëíìîïóòõôöúùûüçÁÀÃÂÄÉÈÊËÍÌÎÏÓÒÕÖÔÚÙÛÜÇ|aaaaaeeeeiiiiooooouuuucAAAAAEEEEIIIIOOOOOUUUUC" />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="textEmail">${mensagens.get('MsbcValidatorFormularioTextEmail').valor}:</label>
					<input type="text" id="textEmail" name="textEmail" class="form-control msbc-validator" value="${msbcValidator.textEmail}" 
						data-msbc-required="Campo obrigatório"
						data-msbc-maxlength="45|O campo deve ter no máximo {value} caracteres" 
						data-msbc-email="E-mail inválido" />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="cpfSomenteNumeros">${mensagens.get('MsbcValidatorFormularioCpfSomenteNumeros').valor}:</label>
					<input type="text" id="cpfSomenteNumeros" name="cpfSomenteNumeros" class="form-control msbc-validator" value="${msbcValidator.cpfSomenteNumeros}" 
						data-msbc-required="Campo obrigatório"
						data-msbc-type-value="somente-numero" 
						data-msbc-equalslength="11|O campo deve ter exatamente {value} dígitos" 
						data-msbc-maxlength="11|O campo deve ter no máximo {value} caracteres" 
						data-msbc-cpf="CPF inválido" />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="cnpjSomenteNumeros">${mensagens.get('MsbcValidatorFormularioCnpjSomenteNumeros').valor}:</label>
					<input type="text" id="cnpjSomenteNumeros" name="cnpjSomenteNumeros" class="form-control msbc-validator" value="${msbcValidator.cnpjSomenteNumeros}" 
						data-msbc-required="Campo obrigatório"
						data-msbc-type-value="somente-numero" 
						data-msbc-equalslength="14|O campo deve ter exatamente {value} dígitos" 
						data-msbc-maxlength="14|O campo deve ter no máximo {value} caracteres" 
						data-msbc-cnpj="CNPJ inválido" />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="telDDD">${mensagens.get('MsbcValidatorFormularioTelDDD').valor}:</label>
					<input type="text" id="telDDD" name="telDDD" class="form-control msbc-validator" value="${msbcValidator.telDDD}" 
						data-msbc-required="Campo obrigatório" 
						data-msbc-type-value="somente-numero"
						data-msbc-equalslength="2|O campo deve ter exatamente {value} dígitos"
						data-msbc-maxlength="2|O campo deve ter no máximo {value} dígitos" />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="telNumero">${mensagens.get('MsbcValidatorFormularioTelNumero').valor}:</label>
					<input type="text" id="telNumero" name="telNumero" class="form-control msbc-validator" value="${msbcValidator.telNumero}" 
						data-msbc-required="Campo obrigatório" 
						data-msbc-type-value="somente-numero"
						data-msbc-minlength="8|O campo deve ter no mínimo {value} dígitos"
						data-msbc-maxlength="9|O campo deve ter no máximo {value} dígitos" />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="cepSomenteNumeros">${mensagens.get('MsbcValidatorFormularioCepSomenteNumeros').valor}:</label>
					<input type="text" id="cepSomenteNumeros" name="cepSomenteNumeros" class="form-control msbc-validator" value="${msbcValidator.cepSomenteNumeros}" 
						data-msbc-required="Campo obrigatório"
						data-msbc-maxlength="8|O campo deve ter no máximo {value} dígitos" 
						data-msbc-type-value="somente-numero" />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label text-right" for="dataTeste">${mensagens.get('MsbcValidatorFormularioDataTeste').valor}:</label>
					<fmt:formatDate pattern="dd/MM/yyyy" value="${msbcValidator.dataTeste.time}" var="data" />
					<input class="form-control data msbc-validator" type="text" name="dataTeste" id="dataTeste"  value="${data}" autocomplete="off" 
						data-msbc-required="Campo obrigatório"
						data-msbc-maxlength="10|O campo deve ter no máximo {value} caracteres" />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label text-right" for="dataTime">${mensagens.get('MsbcValidatorFormularioDataTime').valor}:</label>
					<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${msbcValidator.dataTime.time}" var="data" />
					<input class="form-control datatime msbc-validator" type="text" name="dataTime" id="dataTime"  value="${data}" autocomplete="off" 
						data-msbc-required="Campo obrigatório"
						data-msbc-maxlength="19|O campo deve ter no máximo {value} caracteres" />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="doubleTeste">${mensagens.get('MsbcValidatorFormularioDoubleTeste').valor}:</label>
					<input type="text" id="doubleTeste" name="doubleTeste" class="form-control msbc-validator" value="${msbcValidator.doubleTeste}" 
						data-msbc-required="Campo obrigatório"
						data-msbc-maxlength="20|O campo deve ter no máximo {value} caracteres" />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="integerTeste">${mensagens.get('MsbcValidatorFormularioIntegerTeste').valor}:</label>
					<input type="text" id="integerTeste" name="integerTeste" class="form-control msbc-validator" value="${msbcValidator.integerTeste}" 
						data-msbc-required="Campo obrigatório"
						data-msbc-maxlength="5|O campo deve ter no máximo {value} caracteres" />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="longTeste">${mensagens.get('MsbcValidatorFormularioLongTeste').valor}:</label>
					<input type="text" id="longTeste" name="longTeste" class="form-control msbc-validator" value="${msbcValidator.longTeste}" 
						data-msbc-required="Campo obrigatório"
						data-msbc-maxlength="5|O campo deve ter no máximo {value} caracteres" />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="msbcvalidator">${mensagens.get('MsbcValidatorFormularioTimeStampTeste').valor}:</label>
					<input class='form-control' type='text' value='Esta data é salva automaticamente pelo horario do sistema' disabled='disabled'>
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="textAreaSimples">${mensagens.get('MsbcValidatorFormularioTextAreaSimples').valor}:</label>
					<textarea class="form-control msbc-validator" name="textAreaSimples" id="textAreaSimples" rows="5" cols="80" 
						data-msbc-required="Campo obrigatório"
						data-msbc-maxlength="1000|O campo deve ter no máximo {value} caracteres">${msbcValidator.textAreaSimples}</textarea>
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="textAreaHtml">${mensagens.get('MsbcValidatorFormularioTextAreaHtml').valor}:</label>
					<textarea class="form-control texto-html msbc-validator" name="textAreaHtml" id="textAreaHtml" rows="5" cols="80" 
						data-msbc-required="Campo obrigatório"
						data-msbc-maxlength="1000|O campo deve ter no máximo {value} caracteres" >${msbcValidator.textAreaHtml}</textarea>
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label text-right" for="booleanTeste">${mensagens.get('MsbcValidatorFormularioBooleanTeste').valor}:</label>
					<c:set var="checked" value="" />
					<c:if test="${msbcValidator.booleanTeste}">
						<c:set var="checked" value="checked='checked'" />
					</c:if>
					<input name='booleanTeste' id='booleanTeste' type='checkbox' data-toggle='toggle' data-on="${mensagens.get('MsbcValidatorFormularioBooleanTesteLigado').valor}" data-off="${mensagens.get('MsbcValidatorFormularioBooleanTesteDesligado').valor}" data-onstyle='primary' data-offstyle='danger' ${checked} >
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="arquivoTeste">${mensagens.get('MsbcValidatorFormularioArquivoTeste').valor}:</label>
					<input class="form-control msbc-validator" name="arquivoTesteFile" id="arquivoTeste" value="${msbcValidator.arquivoTeste}" type="file" 
						data-msbc-required="Campo obrigatório" />
					<input name="arquivoTeste" value="${msbcValidator.arquivoTeste}" type="hidden" />
					<c:if test="${not empty msbcValidator.arquivoTeste}">
						<span>${msbcValidator.arquivoTeste}</span>
					</c:if>
				</div>
				<div class="mb-3">
					<div class="col-md-4 offset-md-4">
						<c:if test="${empty msbcValidator.id}">
							<input class="btn btn-success w-100 msbc-validator-button-submit" type="submit" value="${mensagens.get('MsbcValidatorFormularioBotaoInsere').valor}" />
						</c:if>
						<c:if test="${not empty msbcValidator.id}">
							<input class="btn btn-success w-100 msbc-validator-button-submit" type="submit" value="${mensagens.get('MsbcValidatorFormularioBotaoAltera').valor}" />
						</c:if>
					</div>
				</div>

				</form>
			</div>
		</div>
	</div>
</my:template>