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
	fluido="false">
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
							<li><a href="#tab-bestseller"><i class="fa fa-check-circle"></i> Mais Vendidos</a></li>
							<li><a href="#tab-latest"><i class="fa fa-check-circle"></i> Novidades</a></li>
						</ul>

						<div id="tab-bestseller" class="tab_content" >
							<div class="owl-carousel product_carousel_tab">
								<c:forEach items="${listaDeCategoria}" var="cat">
									<div class="product-thumb">
										<div class="image">
											<a href="../site/produtos/lista/${cat.id}"><img
												src="${urlcategoria}/${cat.id}/${cat.imagem}"
												alt="${cat.nome}" title="${cat.nome}" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												${cat.nome}
											</h4>
										</div>
										<a href="../site/produtos/lista/${cat.id}" class="button gray1" title="Ver ${cat.nome}">
											<i class="fa fa-angle-double-right"></i>  veja[+]
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
					<!-- Categories Product Slider Start-->
					<div class="category-module" id="latest_category">
						<h3 class="subtitle">
							<i class="fa fa-check-circle"></i> ${Campanha1.nome } - <a class="viewall" href="category.tpl">ver todas</a>
						</h3>
						<div class="category-module-content">
							<ul id="sub-cat" class="tabs">
								<c:forEach items="${Linha1}" var="linha1">
									<c:set var="ref" value="#tab-" />
									<li><a href="${ref }${linha1.id }" style="cursor:pointer">${linha1.nome }</a></li>
								</c:forEach>
								<li><a href="#tab-cat2">Dia da Mulher</a></li>
								<li><a href="#tab-cat3">Dia das Mães</a></li>
								<li><a href="#tab-cat4">Dia dos Pais</a></li>
								<li><a href="#tab-cat5">Dia das Crianças</a></li>
								<li><a href="#tab-cat6">Natal</a></li>
							</ul>
							
							
							
							
							<div id="tab-4" class="tab_content">
								<div class="owl-carousel latest_category_tabs">
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/samsung_tab_1-220x330.jpg"
												alt="Aspire Ultrabook Laptop"
												title="Aspire Ultrabook Laptop" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">
												<c:forEach items="${Produto1}" var="produto1">
													<div>xxxxxxxxxxxxx</div>
												</c:forEach></a>
											</h4>
											<p class="price">
												<span class="price-new">$230.00</span> <span
													class="price-old">$241.99</span> <span class="saving">-5%</span>
											</p>
											<div class="rating">
												<span class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star-o fa-stack-2x"></i></span>
											</div>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/macbook_pro_1-220x330.jpg"
												alt=" Strategies for Acquiring Your Own Laptop "
												title=" Strategies for Acquiring Your Own Laptop "
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html"> Strategies for Acquiring Your
													Own Laptop </a>
											</h4>
											<p class="price">
												<span class="price-new">$1,400.00</span> <span
													class="price-old">$1,900.00</span> <span class="saving">-26%</span>
											</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/macbook_air_1-220x330.jpg"
												alt="Laptop Silver black" title="Laptop Silver black"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Laptop Silver black</a>
											</h4>
											<p class="price">
												<span class="price-new">$1,142.00</span> <span
													class="price-old">$1,202.00</span> <span class="saving">-5%</span>
											</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/macbook_1-220x330.jpg"
												alt="Ideapad Yoga 13-59341124 Laptop"
												title="Ideapad Yoga 13-59341124 Laptop"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Ideapad Yoga 13-59341124 Laptop</a>
											</h4>
											<p class="price">$211.00</p>
											<div class="rating">
												<span class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star-o fa-stack-2x"></i></span>
											</div>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/ipod_shuffle_1-220x330.jpg"
												alt="Hp Pavilion G6 2314ax Notebok Laptop"
												title="Hp Pavilion G6 2314ax Notebok Laptop"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Hp Pavilion G6 2314ax Notebok
													Laptop</a>
											</h4>
											<p class="price">$122.00</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/ipod_touch_1-220x330.jpg"
												alt="Samsung Galaxy S4" title="Samsung Galaxy S4"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Samsung Galaxy S4</a>
											</h4>
											<p class="price">
												<span class="price-new">$62.00</span> <span
													class="price-old">$122.00</span> <span class="saving">-50%</span>
											</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div id="tab-cat2" class="tab_content">
								<div class="owl-carousel latest_category_tabs">
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/ipod_shuffle_1-220x330.jpg"
												alt="Hp Pavilion G6 2314ax Notebok Laptop"
												title="Hp Pavilion G6 2314ax Notebok Laptop"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Hp Pavilion G6 2314ax Notebok
													Laptop</a>
											</h4>
											<p class="price">$122.00</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div id="tab-cat3" class="tab_content">
								<div class="owl-carousel latest_category_tabs">
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/FinePix-Long-Zoom-Camera-220x330.jpg"
												alt="FinePix S8400W Long Zoom Camera"
												title="FinePix S8400W Long Zoom Camera"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">FinePix S8400W Long Zoom Camera</a>
											</h4>
											<p class="price">$122.00</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/nikon_d300_1-220x330.jpg"
												alt="Digital Camera for Elderly"
												title="Digital Camera for Elderly" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Digital Camera for Elderly</a>
											</h4>
											<p class="price">
												<span class="price-new">$92.00</span> <span
													class="price-old">$98.00</span> <span class="saving">-6%</span>
											</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div id="tab-cat4" class="tab_content">
								<div class="owl-carousel latest_category_tabs">
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/samsung_tab_1-220x330.jpg"
												alt="Aspire Ultrabook Laptop"
												title="Aspire Ultrabook Laptop" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Aspire Ultrabook Laptop</a>
											</h4>
											<p class="price">
												<span class="price-new">$230.00</span> <span
													class="price-old">$241.99</span> <span class="saving">-5%</span>
											</p>
											<div class="rating">
												<span class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star-o fa-stack-2x"></i></span>
											</div>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/iphone_1-220x330.jpg"
												alt="iPhone5" title="iPhone5" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">iPhone5</a>
											</h4>
											<p class="price">$123.20</p>
											<div class="rating">
												<span class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star-o fa-stack-2x"></i></span>
											</div>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/ipod_touch_1-220x330.jpg"
												alt="Samsung Galaxy S4" title="Samsung Galaxy S4"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Samsung Galaxy S4</a>
											</h4>
											<p class="price">
												<span class="price-new">$62.00</span> <span
													class="price-old">$122.00</span> <span class="saving">-50%</span>
											</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/palm_treo_pro_1-220x330.jpg"
												alt="HTC M7 with Stunning Looks"
												title="HTC M7 with Stunning Looks" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">HTC M7 with Stunning Looks</a>
											</h4>
											<p class="price">$337.99</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div id="tab-cat5" class="tab_content">
								<div class="owl-carousel latest_category_tabs">
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/samsung_tab_1-220x330.jpg"
												alt="Aspire Ultrabook Laptop"
												title="Aspire Ultrabook Laptop" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Aspire Ultrabook Laptop</a>
											</h4>
											<p class="price">
												<span class="price-new">$230.00</span> <span
													class="price-old">$241.99</span> <span class="saving">-5%</span>
											</p>
											<div class="rating">
												<span class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star-o fa-stack-2x"></i></span>
											</div>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/ipod_classic_1-220x330.jpg"
												alt="Portable Mp3 Player" title="Portable Mp3 Player"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Portable Mp3 Player</a>
											</h4>
											<p class="price">$122.00</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/macbook_pro_1-220x330.jpg"
												alt=" Strategies for Acquiring Your Own Laptop "
												title=" Strategies for Acquiring Your Own Laptop "
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html"> Strategies for Acquiring Your
													Own Laptop </a>
											</h4>
											<p class="price">
												<span class="price-new">$1,400.00</span> <span
													class="price-old">$1,900.00</span> <span class="saving">-26%</span>
											</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/macbook_air_1-220x330.jpg"
												alt="Laptop Silver black" title="Laptop Silver black"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Laptop Silver black</a>
											</h4>
											<p class="price">
												<span class="price-new">$1,142.00</span> <span
													class="price-old">$1,202.00</span> <span class="saving">-5%</span>
											</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/macbook_1-220x330.jpg"
												alt="Ideapad Yoga 13-59341124 Laptop"
												title="Ideapad Yoga 13-59341124 Laptop"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Ideapad Yoga 13-59341124 Laptop</a>
											</h4>
											<p class="price">$211.00</p>
											<div class="rating">
												<span class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star-o fa-stack-2x"></i></span>
											</div>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/ipod_nano_1-220x330.jpg"
												alt="Mp3 Player" title="Mp3 Player" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Mp3 Player</a>
											</h4>
											<p class="price">$122.00</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/FinePix-Long-Zoom-Camera-220x330.jpg"
												alt="FinePix S8400W Long Zoom Camera"
												title="FinePix S8400W Long Zoom Camera"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">FinePix S8400W Long Zoom Camera</a>
											</h4>
											<p class="price">$122.00</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/ipod_shuffle_1-220x330.jpg"
												alt="Hp Pavilion G6 2314ax Notebok Laptop"
												title="Hp Pavilion G6 2314ax Notebok Laptop"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Hp Pavilion G6 2314ax Notebok
													Laptop</a>
											</h4>
											<p class="price">$122.00</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button"
												onClick="cart.add('34');">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="wishlist.add('34');">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="compare.add('34');">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/ipod_touch_1-220x330.jpg"
												alt="Samsung Galaxy S4" title="Samsung Galaxy S4"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Samsung Galaxy S4</a>
											</h4>
											<p class="price">
												<span class="price-new">$62.00</span> <span
													class="price-old">$122.00</span> <span class="saving">-50%</span>
											</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/nikon_d300_1-220x330.jpg"
												alt="Digital Camera for Elderly"
												title="Digital Camera for Elderly" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Digital Camera for Elderly</a>
											</h4>
											<p class="price">
												<span class="price-new">$92.00</span> <span
													class="price-old">$98.00</span> <span class="saving">-6%</span>
											</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div id="tab-cat6" class="tab_content">
								<div class="owl-carousel latest_category_tabs">
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/ipod_classic_1-220x330.jpg"
												alt="Portable Mp3 Player" title="Portable Mp3 Player"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Portable Mp3 Player</a>
											</h4>
											<p class="price">$122.00</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button"
												onClick="cart.add('48');">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/ipod_nano_1-220x330.jpg"
												alt="Mp3 Player" title="Mp3 Player" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Mp3 Player</a>
											</h4>
											<p class="price">$122.00</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- Categories DATAS End-->

					<!-- Categories CORPORATIVOS Start -->
						<!-- Categories Product Slider Start-->
					<div class="category-module" id="latest_category">
						<h3 class="subtitle">
							<i class="fa fa-check-circle"></i> Corporativos - <a class="viewall" href="category.tpl">ver todos</a>
						</h3>
						<div class="category-module-content">
							<ul id="sub-cat" class="tabs">
								<li><a href="#tab-cat7">Escritório</a></li>
								<li><a href="#tab-cat8">Linha Esológica</a></li>
								<li><a href="#tab-cat9">Informática</a></li>
								<li><a href="#tab-cat10">Bar e Bebidas</a></li>
								<li><a href="#tab-cat11">Cozinha</a></li>
								<li><a href="#tab-cat12">Viagens</a></li>
							</ul>
							<div id="tab-cat7" class="tab_content">
								<div class="owl-carousel latest_category_tabs">
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/samsung_tab_1-220x330.jpg"
												alt="Aspire Ultrabook Laptop"
												title="Aspire Ultrabook Laptop" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Aspire Ultrabook Laptop</a>
											</h4>
											<p class="price">
												<span class="price-new">$230.00</span> <span
													class="price-old">$241.99</span> <span class="saving">-5%</span>
											</p>
											<div class="rating">
												<span class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star-o fa-stack-2x"></i></span>
											</div>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/macbook_pro_1-220x330.jpg"
												alt=" Strategies for Acquiring Your Own Laptop "
												title=" Strategies for Acquiring Your Own Laptop "
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html"> Strategies for Acquiring Your
													Own Laptop </a>
											</h4>
											<p class="price">
												<span class="price-new">$1,400.00</span> <span
													class="price-old">$1,900.00</span> <span class="saving">-26%</span>
											</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/macbook_air_1-220x330.jpg"
												alt="Laptop Silver black" title="Laptop Silver black"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Laptop Silver black</a>
											</h4>
											<p class="price">
												<span class="price-new">$1,142.00</span> <span
													class="price-old">$1,202.00</span> <span class="saving">-5%</span>
											</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/macbook_1-220x330.jpg"
												alt="Ideapad Yoga 13-59341124 Laptop"
												title="Ideapad Yoga 13-59341124 Laptop"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Ideapad Yoga 13-59341124 Laptop</a>
											</h4>
											<p class="price">$211.00</p>
											<div class="rating">
												<span class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star-o fa-stack-2x"></i></span>
											</div>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/ipod_shuffle_1-220x330.jpg"
												alt="Hp Pavilion G6 2314ax Notebok Laptop"
												title="Hp Pavilion G6 2314ax Notebok Laptop"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Hp Pavilion G6 2314ax Notebok
													Laptop</a>
											</h4>
											<p class="price">$122.00</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/ipod_touch_1-220x330.jpg"
												alt="Samsung Galaxy S4" title="Samsung Galaxy S4"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Samsung Galaxy S4</a>
											</h4>
											<p class="price">
												<span class="price-new">$62.00</span> <span
													class="price-old">$122.00</span> <span class="saving">-50%</span>
											</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div id="tab-cat8" class="tab_content">
								<div class="owl-carousel latest_category_tabs">
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/ipod_shuffle_1-220x330.jpg"
												alt="Hp Pavilion G6 2314ax Notebok Laptop"
												title="Hp Pavilion G6 2314ax Notebok Laptop"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Hp Pavilion G6 2314ax Notebok
													Laptop</a>
											</h4>
											<p class="price">$122.00</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div id="tab-cat9" class="tab_content">
								<div class="owl-carousel latest_category_tabs">
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/FinePix-Long-Zoom-Camera-220x330.jpg"
												alt="FinePix S8400W Long Zoom Camera"
												title="FinePix S8400W Long Zoom Camera"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">FinePix S8400W Long Zoom Camera</a>
											</h4>
											<p class="price">$122.00</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/nikon_d300_1-220x330.jpg"
												alt="Digital Camera for Elderly"
												title="Digital Camera for Elderly" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Digital Camera for Elderly</a>
											</h4>
											<p class="price">
												<span class="price-new">$92.00</span> <span
													class="price-old">$98.00</span> <span class="saving">-6%</span>
											</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div id="tab-cat10" class="tab_content">
								<div class="owl-carousel latest_category_tabs">
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/samsung_tab_1-220x330.jpg"
												alt="Aspire Ultrabook Laptop"
												title="Aspire Ultrabook Laptop" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Aspire Ultrabook Laptop</a>
											</h4>
											<p class="price">
												<span class="price-new">$230.00</span> <span
													class="price-old">$241.99</span> <span class="saving">-5%</span>
											</p>
											<div class="rating">
												<span class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star-o fa-stack-2x"></i></span>
											</div>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/iphone_1-220x330.jpg"
												alt="iPhone5" title="iPhone5" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">iPhone5</a>
											</h4>
											<p class="price">$123.20</p>
											<div class="rating">
												<span class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star-o fa-stack-2x"></i></span>
											</div>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/ipod_touch_1-220x330.jpg"
												alt="Samsung Galaxy S4" title="Samsung Galaxy S4"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Samsung Galaxy S4</a>
											</h4>
											<p class="price">
												<span class="price-new">$62.00</span> <span
													class="price-old">$122.00</span> <span class="saving">-50%</span>
											</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/palm_treo_pro_1-220x330.jpg"
												alt="HTC M7 with Stunning Looks"
												title="HTC M7 with Stunning Looks" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">HTC M7 with Stunning Looks</a>
											</h4>
											<p class="price">$337.99</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div id="tab-cat11" class="tab_content">
								<div class="owl-carousel latest_category_tabs">
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/samsung_tab_1-220x330.jpg"
												alt="Aspire Ultrabook Laptop"
												title="Aspire Ultrabook Laptop" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Aspire Ultrabook Laptop</a>
											</h4>
											<p class="price">
												<span class="price-new">$230.00</span> <span
													class="price-old">$241.99</span> <span class="saving">-5%</span>
											</p>
											<div class="rating">
												<span class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star-o fa-stack-2x"></i></span>
											</div>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/ipod_classic_1-220x330.jpg"
												alt="Portable Mp3 Player" title="Portable Mp3 Player"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Portable Mp3 Player</a>
											</h4>
											<p class="price">$122.00</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/macbook_pro_1-220x330.jpg"
												alt=" Strategies for Acquiring Your Own Laptop "
												title=" Strategies for Acquiring Your Own Laptop "
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html"> Strategies for Acquiring Your
													Own Laptop </a>
											</h4>
											<p class="price">
												<span class="price-new">$1,400.00</span> <span
													class="price-old">$1,900.00</span> <span class="saving">-26%</span>
											</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/macbook_air_1-220x330.jpg"
												alt="Laptop Silver black" title="Laptop Silver black"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Laptop Silver black</a>
											</h4>
											<p class="price">
												<span class="price-new">$1,142.00</span> <span
													class="price-old">$1,202.00</span> <span class="saving">-5%</span>
											</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/macbook_1-220x330.jpg"
												alt="Ideapad Yoga 13-59341124 Laptop"
												title="Ideapad Yoga 13-59341124 Laptop"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Ideapad Yoga 13-59341124 Laptop</a>
											</h4>
											<p class="price">$211.00</p>
											<div class="rating">
												<span class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star fa-stack-2x"></i><i
													class="fa fa-star-o fa-stack-2x"></i></span> <span
													class="fa fa-stack"><i
													class="fa fa-star-o fa-stack-2x"></i></span>
											</div>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/ipod_nano_1-220x330.jpg"
												alt="Mp3 Player" title="Mp3 Player" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Mp3 Player</a>
											</h4>
											<p class="price">$122.00</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/FinePix-Long-Zoom-Camera-220x330.jpg"
												alt="FinePix S8400W Long Zoom Camera"
												title="FinePix S8400W Long Zoom Camera"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">FinePix S8400W Long Zoom Camera</a>
											</h4>
											<p class="price">$122.00</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/ipod_shuffle_1-220x330.jpg"
												alt="Hp Pavilion G6 2314ax Notebok Laptop"
												title="Hp Pavilion G6 2314ax Notebok Laptop"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Hp Pavilion G6 2314ax Notebok
													Laptop</a>
											</h4>
											<p class="price">$122.00</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button"
												onClick="cart.add('34');">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="wishlist.add('34');">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="compare.add('34');">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/ipod_touch_1-220x330.jpg"
												alt="Samsung Galaxy S4" title="Samsung Galaxy S4"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Samsung Galaxy S4</a>
											</h4>
											<p class="price">
												<span class="price-new">$62.00</span> <span
													class="price-old">$122.00</span> <span class="saving">-50%</span>
											</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/nikon_d300_1-220x330.jpg"
												alt="Digital Camera for Elderly"
												title="Digital Camera for Elderly" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Digital Camera for Elderly</a>
											</h4>
											<p class="price">
												<span class="price-new">$92.00</span> <span
													class="price-old">$98.00</span> <span class="saving">-6%</span>
											</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div id="tab-cat12" class="tab_content">
								<div class="owl-carousel latest_category_tabs">
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/ipod_classic_1-220x330.jpg"
												alt="Portable Mp3 Player" title="Portable Mp3 Player"
												class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Portable Mp3 Player</a>
											</h4>
											<p class="price">$122.00</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button"
												onClick="cart.add('48');">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
									<div class="product-thumb">
										<div class="image">
											<a href="product.html"><img
												src="${urlproduto}/product/ipod_nano_1-220x330.jpg"
												alt="Mp3 Player" title="Mp3 Player" class="img-responsive" /></a>
										</div>
										<div class="caption">
											<h4>
												<a href="product.html">Mp3 Player</a>
											</h4>
											<p class="price">$122.00</p>
										</div>
										<div class="button-group">
											<button class="btn-primary" type="button" onClick="">
												<span>Add to Cart</span>
											</button>
											<div class="add-to-links">
												<button type="button" data-toggle="tooltip"
													title="Add to wishlist" onClick="">
													<i class="fa fa-heart"></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Add to compare" onClick="">
													<i class="fa fa-exchange"></i>
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					
					<!-- Categories CORPORATIVOS End -->
					

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