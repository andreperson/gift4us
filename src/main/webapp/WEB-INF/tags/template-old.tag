<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="title" required="true"%>
<%@ attribute name="fluido" required="true" type="java.lang.Boolean"%>
<%@ attribute name="cssFiles" required="false"%>
<%@ attribute name="jsFiles" required="false"%>
<%@ attribute name="refresh" required="false"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>${title}</title>
<link href="<c:url value="/resources/imagens/sbc_logo.ico"/>" rel="icon" type="image/x-icon" />
<link href="<c:url value="/resources/imagens/sbc_logo.ico"/>" rel="shortcut icon" type="image/x-icon" />
<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/css/datetimepicker/jquery.datetimepicker.min.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/css/fontawesome/css/all.min.css"/>">


<c:forEach items="${cssFiles}" var="file">
	<c:url var="url" value="/resources/css/${file}" />
	<link rel="stylesheet" href="${url}">
</c:forEach>
</head>
<body>
	<c:if test="${fluido}">
		<c:set value="container-fluid" var="tipoDeContainer" />
	</c:if>
	<c:if test="${not fluido}">
		<c:set value="container" var="tipoDeContainer" />
	</c:if>
	<c:import url="../menu.jsp" />

	<div class="${tipoDeContainer}">
		<div class="row mb-2" id="mensagens">
			<my:erros />
			<my:sucesso />
		</div>
		<div class="row">
			<jsp:doBody />
		</div>
		<br/>
	</div>
	<!-- Default scripts -->
	<script type="text/javascript">var urlPadrao = '<c:url value="/"/>';</script>
	<script src="<c:url value="/resources/js/jquery/jquery.min.js"/>"></script>
	<script src="<c:url value="/resources/js/bootstrap/bootstrap.bundle.min.js"/>"></script>
	<script src="<c:url value="/resources/js/jquery/jquery.datetimepicker.full.min.js"/>"></script>
	<script src="<c:url value="/resources/js/funcoes.js"/>"></script>
	<script src="<c:url value="/resources/js/msbc-validator-1.8.0.min.js"/>"></script>
	

	<c:forEach items="${jsFiles}" var="file">
		<c:url var="url" value="/resources/js/${file}" />
		<script src="${url}"></script>
	</c:forEach>
</body>
</html>