<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags/tag-site" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs"%>
<c:url value="${urlRecursos}image" var="urlrecursos" />
<c:url value="${urlpadrao}_produtos" var="urlproduto" />
<c:url value="${urlpadrao}_sliders" var="urlslider" />
<c:url value="${urlpadrao}_banners" var="urlbanner" />
<c:url value="${urlpadrao}_modelos" var="urlmodelo" />
<c:url value="${urlpadrao}_categorias" var="urlcategoria" />
<c:url value="${urlRecursos	}resources-site/image/icons" var="urlicons" />

<my:template title="${mensagens.get('NomeDoProjeto').valor}"
	fluido="false" jsFiles="${jsFiles}">
	<div id="container" style="padding-top: 180px;">
		<div class="container">
			<div class="row">
				<!--Middle Part Start-->
				<div id="content" class="col-sm-12">
					<h1 class="title">Orçamento enviado com sucesso!</h1>
					<div class="table-responsive">
						<h4>Agora é só aguardar o contato de nossos parceiros.<br><br>
						Obrigado!<br>
						Equipe gift4us<br></h4>
					</div>

					<div class="buttons">
						<div class="pull-left">
							<a href="../index" class="btn btn-default">Continue Navegando</a>
						</div>
					</div>
					<input type="hidden" id="podelimpar" value="ok">
				</div>
				<!--Middle Part End -->
			</div>
		</div>
	</div>
</my:template>