<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs"%>
<c:url value="<%=ListaDeURLs.HOME%>" var="url" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/menu/menu.css"/>" media="screen" />

<a href="${url}"> <c:url
		value="${baseURL}/resources/Images/logo-gift.png" var="urllogo" /> <img
	title="Gift4Us" src="${urllogo}"
	style="height: 75px; margin-top: -35px; margin-bottom: 7px; margin-left: -47px; image-rendering: pixelated;">
</a>

<!-- Navigation -->
<ul class="navbar-nav"
	style="font-size: 0.9em; margin-top: 10px; margin-left: -70px;">
	<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_CONFIGURACOES')">
		<li class="ms-3 ms-sm-0"><c:url
				value="<%=ListaDeURLs.LISTA_DE_PRODUTO%>" var="url" /> <a
			href="${url}" class="nav-link"> <c:url
					value="${baseURL}/resources/Images/icone/ico-menu-produto3.png"
					var="urllogo" /> <img title="Produtos" src="${urllogo}"
				class="menu-icone"> <span class="menu-texto">&nbsp;Produtos</span></a></li>

	</sec:authorize>

	<sec:authorize
		access="hasAnyRole('ROLE_ADMIN','ROLE_GERENCIAL', 'ROLE_ANUNCIANTE')">
		<li class="ms-3 ms-sm-0"><c:url
				value="<%=ListaDeURLs.LISTA_DE_ORCAMENTO%>" var="url" /> <a
			href="${url}" class="nav-link"><c:url
					value="${baseURL}/resources/Images/icone/ico-menu-orcamento3.png"
					var="urllogo" /> <img title="Orçamentos" src="${urllogo}"
				class="menu-icone"> <span class="menu-texto">&nbsp;Orçamentos</span>
		</a></li>
	</sec:authorize>

	<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_GERENCIAL')">
		<li class="ms-3 ms-sm-0"><c:url
				value="<%=ListaDeURLs.LISTA_DE_ORCAMENTO%>" var="url" /> <a
			href="${url}" class="nav-link"><c:url
					value="${baseURL}/resources/Images/icone/ico-menu-usuario.png"
					var="urllogo" /> <img title="Usuários" src="${urllogo}"
				class="menu-icone"> <span class="menu-texto">&nbsp;Usuários</span>
		</a></li>
	</sec:authorize>



	<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_CONFIGURACOES')">
		<li class="ms-3 ms-sm-0 dropdown"><a href="#"
			class="nav-link dropdown-toggle" data-bs-toggle="dropdown"
			role="button" aria-haspopup="true" aria-expanded="false"> <c:url
					value="${baseURL}/resources/Images/icone/ico-menu-config.png"
					var="urllogo" /> <img title="Configurações" src="${urllogo}"
				class="menu-icone"> <span class="menu-texto">&nbsp;Configurações</span>
		</a>
			<div class="dropdown-menu me-2">

				<sec:authorize
					access="hasAnyRole('ROLE_ADMIN','ROLE_CONFIGURACOES')">
					<c:url value="<%=ListaDeURLs.LISTA_DE_CATEGORIA%>" var="url" />
					<a href="${url}" class="dropdown-item"> <c:url
							value="${baseURL}/resources/Images/icone/ico-menu-categoria.png"
							var="urllogo" /> <img title="Categoria" src="${urllogo}"
						class="menu-icone--admin"> <span class="menu-texto--admin">&nbsp;Categoria</span>
					</a>
				</sec:authorize>

				<sec:authorize
					access="hasAnyRole('ROLE_ADMIN','ROLE_CONFIGURACOES')">
					<c:url value="<%=ListaDeURLs.LISTA_DE_SUBCATEGORIA%>" var="url" />
					<a href="${url}" class="dropdown-item"> <c:url
							value="${baseURL}/resources/Images/icone/ico-menu-sub-categoria.png"
							var="urllogo" /> <img title="Sub Categoria" src="${urllogo}"
						class="menu-icone--admin"> <span class="menu-texto--admin">&nbsp;Sub
							Categoria</span>
					</a>
				</sec:authorize>

				<sec:authorize
					access="hasAnyRole('ROLE_ADMIN','ROLE_CONFIGURACOES')">
					<c:url value="<%=ListaDeURLs.LISTA_DE_ANUNCIANTE%>" var="url" />
					<a href="${url}" class="dropdown-item"> <c:url
							value="${baseURL}/resources/Images/icone/ico-menu-anunciante.png"
							var="urllogo" /> <img title="Anunciante" src="${urllogo}"
						class="menu-icone--admin"> <span class="menu-texto--admin">&nbsp;Anunciante</span>
					</a>
				</sec:authorize>

				<sec:authorize
					access="hasAnyRole('ROLE_ADMIN','ROLE_CONFIGURACOES')">
					<c:url value="<%=ListaDeURLs.LISTA_DE_ANUNCIANTETIPO%>" var="url" />
					<a href="${url}" class="dropdown-item"> <c:url
							value="${baseURL}/resources/Images/icone/ico-menu-anunciante-tipo.png"
							var="urllogo" /> <img title="anunciante Tipo" src="${urllogo}"
						class="menu-icone--admin"> <span class="menu-texto--admin">&nbsp;Anunciante
							Tipo</span>
					</a>
				</sec:authorize>
				<sec:authorize
					access="hasAnyRole('ROLE_ADMIN','ROLE_CONFIGURACOES')">
					<c:url value="<%=ListaDeURLs.LISTA_DE_STATUS%>" var="url" />
					<a href="${url}" class="dropdown-item"> <c:url
							value="${baseURL}/resources/Images/icone/ico-menu-status.png"
							var="urllogo" /> <img title="Status" src="${urllogo}"
						class="menu-icone--admin"> <span class="menu-texto--admin">&nbsp;Status</span>
					</a>
				</sec:authorize>




			</div></li>
	</sec:authorize>

	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<li class="ms-3 ms-sm-0 dropdown"><a href="#"
			class="nav-link dropdown-toggle" data-bs-toggle="dropdown"
			role="button" aria-haspopup="true" aria-expanded="false"> <c:url
					value="${baseURL}/resources/Images/icone/ico-menu-admin.png"
					var="urllogo" /> <img title="Administrativo" src="${urllogo}"
				class="menu-icone"> <span class="menu-texto">&nbsp;Administrativo</span>
		</a>
			<div class="dropdown-menu me-2">

				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<c:url value="<%=ListaDeURLs.LISTA_DE_USUARIO%>" var="url" />
					<a href="${url}" class="dropdown-item"> <c:url
							value="${baseURL}/resources/Images/icone/ico-menu-usuario-admin.png"
							var="urllogo" /> <img title="Usuários Admin" src="${urllogo}"
						class="menu-icone--admin"> <span class="menu-texto--admin">&nbsp;Usuários
							Admin</span>
					</a>
				</sec:authorize>


				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<c:url value="<%=ListaDeURLs.LISTA_DE_GRUPO%>" var="url" />
					<a href="${url}" class="dropdown-item"> <c:url
							value="${baseURL}/resources/Images/icone/ico-menu-grupo2.png"
							var="urllogo" /><img title="Grupos" src="${urllogo}"
						class="menu-icone--admin"> <span class="menu-texto--admin">&nbsp;Grupo</span>
					</a>
				</sec:authorize>

				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<c:url value="<%=ListaDeURLs.LISTA_DE_MENSAGENSDOSISTEMA%>"
						var="url" />
					<a href="${url}" class="dropdown-item"> <c:url
							value="${baseURL}/resources/Images/icone/ico-menu-msg.png"
							var="urllogo" /> <img title="Mensagens do Sistema"
						src="${urllogo}" class="menu-icone--admin"> <span
						class="menu-texto--admin">&nbsp;Mensagens do Sistema</span>
					</a>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<c:url value="<%=ListaDeURLs.LISTA_DE_CONFIGURACOESDOSISTEMA%>"
						var="url" />
					<a href="${url}" class="dropdown-item"> <c:url
							value="${baseURL}/resources/Images/icone/ico-menu-config-sistema.png"
							var="urllogo" /> <img title="Configurações do Sistema"
						src="${urllogo}" class="menu-icone--admin"> <span
						class="menu-texto--admin">&nbsp;Configurações do sistema</span>
					</a>
				</sec:authorize>


				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<c:url value="<%=ListaDeURLs.LISTA_DE_HISTORICODOSISTEMA%>"
						var="url" />
					<a href="${url}" class="dropdown-item"> <c:url
							value="${baseURL}/resources/Images/icone/ico-menu-historico.png"
							var="urllogo" /> <img title="Histórico do Sistema"
						src="${urllogo}" class="menu-icone--admin"> <span
						class="menu-texto--admin">&nbsp;Histórico do sistema</span>
					</a>
				</sec:authorize>



			</div></li>
	</sec:authorize>
</ul>



<div style="padding-top: 200px;">
	<ul class="navbar-nav"
		style="font-size: 0.9em; margin-top: -30px; margin-left: -70px;">

		<li class="ms-3 ms-sm-0 me-3 ms-xl-auto"><sec:authentication
				property="principal.login" var="login" /> <sec:authentication
				property="principal.id" var="id" /> <span style="color: #707071;">
				<sec:authorize access="hasAnyRole('ROLE_USUARIO_LOGADO')">
					<c:url value="ListaDeURLs.USUARIOS_EDITAR" var="url" />
					<a href="${url}?usuarioId=${id}" class="dropdown-item"
						title="Editar Usuário" style="color: #337AB7;"><i
						class="fas fa-user-circle fa" style="margin-top: 3px;"></i>
						${login}</a>
				</sec:authorize> <c:url value="<%=ListaDeURLs.LOGOUT%>" var="url" /> <a
				href="${url}" class="dropdown-item" title="Logoff"
				style="color: #cc0000;"> <i class="fas fa-sign-out-alt fa"
					title="${mensagens.get('MenuBotaoSair').valor}"></i>
					${mensagens.get('MenuBotaoSair').valor}
			</a>

		</span></li>
	</ul>
</div>


