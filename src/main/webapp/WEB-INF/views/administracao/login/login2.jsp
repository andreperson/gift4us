<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<my:template fluido="false" title="${mensagens.get('NomeDoProjeto').valor}">
	<div class="col-lg-4 offset-lg-4">
		<div class="card w-100">
			<div class="card-header">
				<h2 class="card-title">Acesso Restrito</h2>
			</div>
			<c:if
				test="${not empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION}">
				<div class="alert alert-danger" style="margin: 5px;">
					<i class="fa fa-exclamation-circle" aria-hidden="true"></i>
					${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}
				</div>
			</c:if>
			<div class="card-body">
				<c:url value="/efetuaLogin" var="action" />
				<form:form action="${action}" id="form-login" method="post">
					<div class="mb-3">
						<label for="login" class="form-label">Matricula</label> 
						<input id="login" autofocus="autofocus" class="form-control" name="login"
							required="required" type="text">
					</div>
					<div class="mb-3">
						<label for="senha" class="form-label">Senha</label> 
						<input id="senha" class="form-control" name="senha" type="password">
					</div>
					<div align="center">
						<button type=submit id="btnAcessar" class="btn btn-primary">
							<i class="fas fa-user"></i> Login 
						</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</my:template>