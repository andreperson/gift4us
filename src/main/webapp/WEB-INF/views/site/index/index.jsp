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

<c:set var="jsFiles" value="/js/Index.js" />

<my:template title="${mensagens.get('NomeDoProjeto').valor}"
	fluido="false" jsFiles="${jsFiles}">
	<div id="container" style="padding-top: 180px;">
		<div class="container">
			<div class="row">
				<!--Middle Part Start-->
				<div id="content" class="col-xs-12">
					<!-- Slideshow Start-->
					<div class="slideshow single-slider owl-carousel">
						<div class="item">
							<a href="../site/produtos/lista/5" title="Camisetas"><img
								class="img-responsive" src="${urlslider}/banner-1.jpg" /></a>
						</div>
						<div class="item">
							<a href="../site/produtos/lista/9"><img
								class="img-responsive" title="Sacolas e Mochilas"
								src="${urlslider}/banner-2.jpg" /></a>
						</div>
					</div>
					<!-- Slideshow End-->
					<!-- Banner Start-->
					<div class="marketshop-banner">
						<div class="row">
							<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
								<a href="../site/produtos/lista/1"><img
									src="${urlbanner}/banner-300-1.jpg" alt="Canecas"
									title="Canecas" /></a>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
								<a href="#"><img src="${urlbanner}/banner-300-2.jpg"
									alt="Sample Banner 2" title="Sample Banner 2" /></a>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
								<a href="#"><img src="${urlbanner}/banner-300-3.jpg"
									alt="Sample Banner 3" title="Sample Banner 3" /></a>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
								<a href="#"><img src="${urlbanner}/banner-300-4.jpg"
									alt="Sample Banner 4" title="Sample Banner 4" /></a>
							</div>
						</div>
					</div>
					<!-- Banner End-->
					<!-- Product Tab Start -->
					<div id="product-tab" class="product-tab">
						<ul id="tabs" class="tabs">
							<li><a href="#tab-bestseller"><i
									class="fa fa-check-circle"></i> Mais Vendidos</a></li>
							<li><a href="#tab-latest"><i class="fa fa-check-circle"></i>
									Novidades</a></li>
						</ul>

						<div id="tab-bestseller" class="tab_content">
							<div class="owl-carousel product_carousel_tab">
								<c:forEach items="${listaDeCategoria}" var="cat">
									<div class="product-thumb">
										<div class="image">
											<a href="../site/produtos/lista/${cat.id}"><img
												src="${urlcategoria}/${cat.id}/${cat.imagem}"
												alt="${cat.nome}" title="${cat.nome}" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>${cat.nome}</h4>
										</div>
										<a href="../site/produtos/lista/${cat.id}"
											class="button gray1" title="Ver ${cat.nome}"> <i
											class="fa fa-angle-double-right"></i> veja[+]
										</a>
									</div>
								</c:forEach>
							</div>
						</div>

						<div id="tab-latest" class="tab_content">
							<div class="owl-carousel product_carousel_tab">
								<c:forEach items="${listaDeProduto}" var="produto">
									<c:set var="urlprodutomontada" scope="application"
										value="${urlmodelo}/modelo-220x330.jpg" />
									<c:if test="${not empty produto.imagem}">
										<c:set var="urlprodutomontada" scope="application"
											value="${urlproduto}/${produto.anunciante.id}/${produto.id}/${produto.imagem}" />
									</c:if>

									<div class="product-thumb clearfix">
										<div class="image">
											<a href="product.html"><img src="${urlprodutomontada}"
												width="330" height="220" alt="${produto.titulo}"
												title="${produto.titulo}" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">${produto.titulo}</a>
											</h4>
											<p class="price">
												<span class="price-new">$ ${produto.preco}</span> <span
													class="price-old">$122.00</span><span class="saving">-10%</span>
											</p>
										</div>
										<div class="button-group">
											<input onClick="cart.add('42');" type="submit"
												value="Incluir no Orçamento" class="btn btn-primary">
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
									src="${urlbanner}/sample-banner-1-600x250.jpg"
									alt="2 Block Banner" title="2 Block Banner" /></a>
							</div>
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<a href="#"><img
									src="${urlbanner}/sample-banner-2-600x250.jpg"
									alt="2 Block Banner 1" title="2 Block Banner 1" /></a>
							</div>
						</div>
					</div>
					<!-- Banner End -->
					<!-- CAMPANHA 1 INICIO -->
					<div class="category-module" id="latest_category">
						<h3 class="subtitle">
							<i class="fa fa-check-circle"></i> ${Campanha1.nome } - <a
								class="viewall" href="category.tpl">ver todas</a>
						</h3>
						<div class="category-module-content">
							<ul id="sub-cat" class="tabs">
								<c:set var="ref" value="tab-linha" />
								<c:forEach items="${Linha1}" var="linha">
									<li><a href="#${ref}${linha.id}" style="cursor: pointer"
										onclick="carregaProdutoByLinha(${linha.id})">${linha.nome }</a></li>
								</c:forEach>
							</ul>
							<c:forEach items="${Linha1}" var="linhaloop" varStatus="count">
								<div id="${ref}${linhaloop.id}" class="tab_content">
									<div class="owl-carousel latest_category_tabs">

										<c:set var="i" value="${count.index}" />

										<c:choose>
											<c:when test="${i == 0}">
												<c:set var="prod" value="${Produto0}" />
											</c:when>
											<c:when test="${i == 1}">
												<c:set var="prod" value="${Produto1}" />
											</c:when>
											<c:when test="${i == 2}">
												<c:set var="prod" value="${Produto2}" />
											</c:when>
											<c:when test="${i == 3}">
												<c:set var="prod" value="${Produto3}" />
											</c:when>
											<c:when test="${i == 4}">
												<c:set var="prod" value="${Produto4}" />
											</c:when>
											<c:when test="${i == 5}">
												<c:set var="prod" value="${Produto5}" />
											</c:when>
											<c:when test="${i == 6}">
												<c:set var="prod" value="${Produto6}" />
											</c:when>
											<c:when test="${i == 7}">
												<c:set var="prod" value="${Produto7}" />
											</c:when>
											<c:when test="${i == 8}">
												<c:set var="prod" value="${Produto8}" />
											</c:when>
											<c:when test="${i == 9}">
												<c:set var="prod" value="${Produto9}" />
											</c:when>
										</c:choose>

										<c:forEach items="${prod}" var="produto">

											<c:set var="urlprodutomontada" scope="application"
												value="${urlmodelo}/modelo-220x330.jpg" />
											<c:if test="${not empty produto.imagem}">
												<c:set var="urlprodutomontada" scope="application"
													value="${urlproduto}/${produto.anunciante.id}/${produto.id}/${produto.imagem}" />
											</c:if>

											<c:set var="desconto" value="0.${produto.desconto}" />

											<c:set var="precocomdesconto" scope="application"
												value="${produto.preco - (produto.preco * desconto)}" />

											<div class="product-thumb">
												<div class="image">
													<a href="../../produtos/produto/${produto.id}"><img
														src="${urlprodutomontada}" width="330" height="220"
														alt="${produto.titulo}" title="${produto.titulo}"
														class="img-responsive" /></a>
												</div>
												<div class="caption">
													<h4>
														<a href="../../produtos/produto/${produto.id}">${produto.titulo}</a>
													</h4>

													<p class="price">
														<c:if test="${produto.desconto > 0}">
															<span class="price-new">$ ${precocomdesconto}</span>
															<span class="price-old">${produto.preco}</span>
															<span class="saving">-${produto.desconto}%</span>
														</c:if>
														<c:if test="${produto.desconto == 0}">
															<span class="price-new">$ ${produto.preco}</span>
														</c:if>
													</p>
												</div>
												<div class="button-group">
													<a href="../../produtos/produto/${produto.id}"
														class="button gray1" title="Ver ${produto.titulo}"> <i
														class="fa fa-angle-double-right"></i> veja[+]
													</a> <a href="../../produtos/produto/${produto.id}"
														class="button lilas" title="Ver ${produto.titulo}"> <i
														class="fa fa-shopping-cart"></i> orçar
													</a>
												</div>
											</div>
										</c:forEach>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
					<!-- CAMPANHA DATAS End-->
					<br>
					<hr>

					<!-- Categories Product Slider Start -->
					<h3 class="subtitle">
						<i class="fa fa-check-circle"></i> ${Linha2.nome } - <a
							class="viewall" href="category.tpl">ver [+]</a>
					</h3>
					<div class="owl-carousel latest_category_carousel">
						<c:forEach items="${ProdutoCampanha2}" var="produto">

							<c:set var="urlprodutomontada" scope="application"
								value="${urlmodelo}/modelo-220x330.jpg" />
							<c:if test="${not empty produto.imagem}">
								<c:set var="urlprodutomontada" scope="application"
									value="${urlproduto}/${produto.anunciante.id}/${produto.id}/${produto.imagem}" />
							</c:if>

							<c:set var="desconto" value="0.${produto.desconto}" />

							<c:set var="precocomdesconto" scope="application"
								value="${produto.preco - (produto.preco * desconto)}" />

							<div class="product-thumb">
								<div class="image">
									<a href="../../produtos/produto/${produto.id}"><img
										src="${urlprodutomontada}" width="330" height="220"
										alt="${produto.titulo}" title="${produto.titulo}"
										class="img-responsive" /></a>
								</div>
								<div class="caption">
									<h4>
										<a href="../../produtos/produto/${produto.id}">${produto.titulo}</a>
									</h4>

									<p class="price">
										<c:if test="${produto.desconto > 0}">
											<span class="price-new">$ ${precocomdesconto}</span>
											<span class="price-old">${produto.preco}</span>
											<span class="saving">-${produto.desconto}%</span>
										</c:if>
										<c:if test="${produto.desconto == 0}">
											<span class="price-new">$ ${produto.preco}</span>
										</c:if>
									</p>
								</div>
								<div class="button-group">
									<a href="../../produtos/produto/${produto.id}"
										class="button gray1" title="Ver ${produto.titulo}"> <i
										class="fa fa-angle-double-right"></i> veja[+]
									</a> <a href="../../produtos/produto/${produto.id}"
										class="button lilas" title="Ver ${produto.titulo}"> <i
										class="fa fa-shopping-cart"></i> orçar
									</a>
								</div>
							</div>
						</c:forEach>

					</div>
					<!-- Categories Product Slider End -->





				</div>
				<!--Middle Part End-->
			</div>
		</div>
	</div>
	<!-- Feature Box Start-->
	<!-- Banner Start -->
	<div class="container">
		<div class="marketshop-banner">
			<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
					<a href="#"><img src="${urlbanner}/sample-banner-4-600x250.jpg"
						alt="2 Block Banner" title="2 Block Banner" /></a>
				</div>
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
					<a href="#"><img src="${urlbanner}/sample-banner-5-600x250.jpg"
						alt="2 Block Banner 1" title="2 Block Banner 1" /></a>
				</div>
			</div>
		</div>
	</div>
	<!-- Banner End -->
	<!-- Feature Box End-->
</my:template>