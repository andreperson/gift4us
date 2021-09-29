<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs"%>
<c:url value="<%=ListaDeURLs.HOME%>" var="url" />

<span style="margin-right: 40px; float: right; font-size: 13px;">
	 <sec:authentication
		property="principal.login" var="login" /> <sec:authentication
		property="principal.id" var="id" /> <span style="color: #707071;">
		<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
			<c:url value="ListaDeURLs.USUARIOS_EDITAR" var="url" />
			<a href="${url}?usuarioId=${id}" class="nav-link"
				title="Editar Usuário" style="color: #fff;"></a><i
				class="fas fa-user-circle" style="margin-top: 3px;"></i>&nbsp;${login}
		</sec:authorize> <c:url value="<%=ListaDeURLs.LOGOUT%>" var="url" /> 
			
			<!-- 
		<br>
		<a href="${url}"
		class="nav-link" title="Logoff" style="color: #fff;"> <i
			class="fas fa-sign-out-alt"
			title="${mensagens.get('MenuBotaoSair').valor}"></i>
			${mensagens.get('MenuBotaoSair').valor}
			 
	</a>
-->
</span>
</span>
