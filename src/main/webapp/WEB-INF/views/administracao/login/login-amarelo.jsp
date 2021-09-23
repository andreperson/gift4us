<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/tag-login" prefix="my"%>
<c:set var="cssFiles" value="login/login.css" />
<c:set var="jsFiles" value="login.js" />

<my:template fluido="false"
	title="${mensagens.get('NomeDoProjeto').valor}" cssFiles="${cssFiles}"
	jsFiles="${jsFiles}">
	<div class="col-lg-4 offset-lg-4">
		<c:if test="${not empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION}">
			<div class="textoerro">
				<p>
					<i class="fa fa-exclamation-circle" aria-hidden="true"></i>
					${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}
			</div>
		</c:if>
		
		<div class="card-body">
			<c:url value="/efetuaLogin" var="action" />
		</div>
		<div class="login-container">
			<section class="login" id="login">
				<header>
					<h2>Gift4Us</h2>
					<h4>Login</h4>
				</header>
				<form:form action="${action}" id="form-login" method="post">
					<input id="login" name="login" type="text" class="login-input"
						placeholder="User" required autofocus />
					<input id="senha" name="senha" type="password" class="login-input"
						placeholder="Password" required />
					<div class="submit-container">
						<button type="submit" class="login-button">SIGN IN</button>
					</div>
				</form:form>
			</section>
		</div>
	</div>
</my:template>