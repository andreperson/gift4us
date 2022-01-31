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


<my:template title="${mensagens.get('NomeDoProjeto').valor}"
	fluido="false">
	<div id="container" style="padding-top: 180px;">
		<div class="container">
			<div class="row">
				<!--Middle Part Start-->
				<div id="content" class="col-xs-12">
					
					<!-- Product Tab Start -->
					<div id="product-tab" class="product-tab">
						<ul id="tabs" class="tabs">
							<li><a href="#product-tab"><i class="fa fa-arrow-circle-right"></i> ${categoria}</a></li>
						</ul>

						<div id="tab-bestseller" class="tab_content">
							<div class="owl-carousel product_carousel_tab">
								<c:forEach items="${listaDeProduto}" var="produto">
									<c:set var="urlprodutomontada" scope="application"
										value="${urlmodelo}/modelo-220x330.jpg" />
									<c:if test="${not empty produto.imagem}">
										<c:set var="urlprodutomontada" scope="application"
											value="${urlproduto}/${produto.anunciante.id}/${produto.id}/${produto.imagem}" />
									</c:if>


									<c:set var="desconto" value="0.${produto.desconto}" />       
										
									<c:set var="precocomdesconto" scope="application"
										value="${produto.preco - (produto.preco * desconto)}" />
						
									<div class="product-thumb clearfix">
										<div class="image">
											<a href="../../produtos/produto/${produto.id}"><img src="${urlprodutomontada}"
												width="330" height="220" alt="${produto.titulo}"
												title="${produto.titulo}" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												${produto.titulo}
											</h4>
											<p class="price">
												<c:if test="${produto.desconto > 0}">
												<span class="price-new">$ ${precocomdesconto}</span> 
												<span
													class="price-old">${produto.preco}</span><span class="saving">-${produto.desconto}%</span>
												</c:if>
												<c:if test="${produto.desconto == 0}">
													<span class="price-new">$ ${produto.preco}</span>
												</c:if>
												
											</p>
										</div>
										<div class="button-group">
										<a href="../../produtos/produto/${produto.id}" class="button gray1" title="Ver ${produto.titulo}">
											 <i class="fa fa-angle-double-right"></i> veja[+]
										</a>
										
											<a href="../../produtos/produto/${produto.id}" class="button lilas" title="Ver ${produto.titulo}">
											 <i class="fa fa-shopping-cart"></i> orçar
										</a>
										</div>
										
										
									</div>
								</c:forEach>
								
							</div>
						</div>
					</div>
					<!-- Product Tab Start -->
					<!-- Banner Start -->
					<div class="marketshop-banner">
						<div class="row">
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<a href="#"><img
									src="${urlbanner}/sample-banner-4-600x250.jpg"
									alt="2 Block Banner" title="2 Block Banner" /></a>
							</div>
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<a href="#"><img
									src="${urlbanner}/sample-banner-5-600x250.jpg"
									alt="2 Block Banner 1" title="2 Block Banner 1" /></a>
							</div>
						</div>
					</div>
				</div>
				<!--Middle Part End-->
			</div>
		</div>
	</div>
	
</my:template>