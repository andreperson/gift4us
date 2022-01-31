<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs"%>
<c:url value="<%=ListaDeURLs.HOME_SITE%>" var="url" />
<div id="header">
	<!-- Top Bar Start-->
	<nav id="top" class="htop">
		<div class="container">
			<div class="row">
				<span class="drop-icon visible-sm visible-xs"><i
					class="fa fa-align-justify"></i></span>
				<div class="pull-left flip left-top">
					<div class="links">
						<ul>
							<!--- <li class="mobile"><i class="fa fa-phone"></i>+91 9898777656</li>  --->
							<li class="wrap_custom_block hidden-sm hidden-xs"><a href="login.html"><i
									class="fas fa-user-circle"></i> login
							</a>
							</li>
							<li>|</li>
							<li class="wrap_custom_block hidden-sm hidden-xs"><a href="anuncie.html"><i
									class="far fa-newspaper"></i> anuncie conosco
							</a>
								</li>
						</ul>
					</div>
				</div>
				<div id="top-links" class="nav pull-right flip">
					<ul>
						<li class="email"><a href="mailto:atendimento@gift4us.com.br"><i
									class="fa fa-envelope"></i> atendimento@gift4us.com.br</a></li>
					</ul>
				</div>
			</div>
		</div>
	</nav>
	<!-- Top Bar End-->
	<!-- Header Start-->
	<header class="" style="background-color: #fff;">
		<div class="container">
			<div class="table-container">
				<!-- Logo Start -->
				<div
					class="col-table-cell col-lg-6 col-md-6 col-sm-12 col-xs-12 inner">
					<div id="logo">
						<a href="${url }"> <c:url
								value="${urlRecursos}resources-site/image/logo-gift.png"
								var="urllogo" /> <img title="Gift4Us" src="${urllogo}"
							style="height: 55px; margin-top: 20px; margin-bottom: 7px; margin-left: -20px; image-rendering: pixelated;">
						</a>
					</div>
				</div>
				<!-- Logo End -->
				<!-- Mini Cart Start-->
				<div class="col-table-cell col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<div id="cart">
						<button type="button" data-toggle="dropdown"
							data-loading-text="Loading..." class="heading dropdown-toggle">
							<span class="cart-icon pull-left flip"></span> <span
								id="cart-total">2 item(s) - $1,132.00</span>
						</button>
						<ul class="dropdown-menu">
							<li>
								<table class="table">
									<tbody>
										<tr>
											<td class="text-center"><a href="product.html"><img
													class="img-thumbnail"
													title="Xitefun Causal Wear Fancy Shoes"
													alt="Xitefun Causal Wear Fancy Shoes"
													src="image/product/sony_vaio_1-50x75.jpg"></a></td>
											<td class="text-left"><a href="product.html">Xitefun
													Causal Wear Fancy Shoes</a></td>
											<td class="text-right">x 1</td>
											<td class="text-right">$902.00</td>
											<td class="text-center"><button
													class="btn btn-danger btn-xs remove" title="Remove"
													onClick="" type="button">
													<i class="fa fa-times"></i>
												</button></td>
										</tr>
										<tr>
											<td class="text-center"><a href="product.html"><img
													class="img-thumbnail" title="Aspire Ultrabook Laptop"
													alt="Aspire Ultrabook Laptop"
													src="image/product/samsung_tab_1-50x75.jpg"></a></td>
											<td class="text-left"><a href="product.html">Aspire
													Ultrabook Laptop</a></td>
											<td class="text-right">x 1</td>
											<td class="text-right">$230.00</td>
											<td class="text-center"><button
													class="btn btn-danger btn-xs remove" title="Remove"
													onClick="" type="button">
													<i class="fa fa-times"></i>
												</button></td>
										</tr>
									</tbody>
								</table>
							</li>
							<li>
								<div>
									<table class="table table-bordered">
										<tbody>
											<tr>
												<td class="text-right"><strong>Sub-Total</strong></td>
												<td class="text-right">$940.00</td>
											</tr>
											<tr>
												<td class="text-right"><strong>Eco Tax (-2.00)</strong></td>
												<td class="text-right">$4.00</td>
											</tr>
											<tr>
												<td class="text-right"><strong>VAT (20%)</strong></td>
												<td class="text-right">$188.00</td>
											</tr>
											<tr>
												<td class="text-right"><strong>Total</strong></td>
												<td class="text-right">$1,132.00</td>
											</tr>
										</tbody>
									</table>
									<p class="checkout">
										<a href="cart.html" class="btn btn-primary"><i
											class="fa fa-shopping-cart"></i> View Cart</a>&nbsp;&nbsp;&nbsp;<a
											href="checkout.html" class="btn btn-primary"><i
											class="fa fa-share"></i> Checkout</a>
									</p>
								</div>
							</li>
						</ul>
					</div>
				</div>
				<!-- Mini Cart End-->
				<!-- Search Start-->
				<div
					class="col-table-cell col-lg-3 col-md-3 col-sm-6 col-xs-12 inner">
					<div id="search" class="input-group">
						<input id="filter_name" type="text" name="search" value=""
							placeholder="Search" class="form-control input-lg" />
						<button type="button" class="button-search">
							<i class="fa fa-search"></i>
						</button>
					</div>
				</div>
				<!-- Search End-->
			</div>
		</div>
	</header>
	<!-- Header End-->
	<!-- Main Menu Start-->

	<nav id="menu" class="navbar">
		<div class="navbar-header">
			<span class="visible-xs visible-sm"> Menu <b></b></span>
		</div>
		<div class="container">
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav">
					<li><a class="home_link" title="Home" href="${url }">Home</a></li>
					<li class="dropdown"><a href="category.html">Fashion</a>
						<div class="dropdown-menu">
							<ul>
								<li><a href="category.html">Men <span>&rsaquo;</span></a>
									<div class="dropdown-menu">
										<ul>
											<li><a href="category.html">Sub Categories </a></li>
											<li><a href="category.html">Sub Categories </a></li>
											<li><a href="category.html">Sub Categories </a></li>
											<li><a href="category.html">Sub Categories </a></li>
											<li><a href="category.html">Sub Categories New </a></li>
										</ul>
									</div></li>
								<li><a href="category.html">Women</a></li>
								<li><a href="category.html">Girls<span>&rsaquo;</span></a>
									<div class="dropdown-menu">
										<ul>
											<li><a href="category.html">Sub Categories </a></li>
											<li><a href="category.html">Sub Categories New</a></li>
											<li><a href="category.html">Sub Categories New</a></li>
										</ul>
									</div></li>
								<li><a href="category.html">Boys</a></li>
								<li><a href="category.html">Baby</a></li>
								<li><a href="category.html">Accessories <span>&rsaquo;</span></a>
									<div class="dropdown-menu">
										<ul>
											<li><a href="category.html">New Sub Categories</a></li>
										</ul>
									</div></li>
							</ul>
						</div></li>
					<li class="dropdown"><a href="category.html">Electronics</a>
						<div class="dropdown-menu">
							<ul>
								<li><a href="category.html">Laptops <span>&rsaquo;</span></a>
									<div class="dropdown-menu">
										<ul>
											<li><a href="category.html">New Sub Categories </a></li>
											<li><a href="category.html">New Sub Categories </a></li>
											<li><a href="category.html">Sub Categories New </a></li>
										</ul>
									</div></li>
								<li><a href="category.html">Desktops <span>&rsaquo;</span></a>
									<div class="dropdown-menu">
										<ul>
											<li><a href="category.html">New Sub Categories </a></li>
											<li><a href="category.html">Sub Categories New </a></li>
											<li><a href="category.html">Sub Categories New </a></li>
										</ul>
									</div></li>
								<li><a href="category.html">Cameras <span>&rsaquo;</span></a>
									<div class="dropdown-menu">
										<ul>
											<li><a href="category.html">New Sub Categories</a></li>
										</ul>
									</div></li>
								<li><a href="category.html">Mobile Phones <span>&rsaquo;</span></a>
									<div class="dropdown-menu">
										<ul>
											<li><a href="category.html">New Sub Categories</a></li>
											<li><a href="category.html">New Sub Categories</a></li>
										</ul>
									</div></li>
								<li><a href="category.html">TV &amp; Home Audio <span>&rsaquo;</span></a>
									<div class="dropdown-menu">
										<ul>
											<li><a href="category.html">New Sub Categories </a></li>
											<li><a href="category.html">Sub Categories New </a></li>
										</ul>
									</div></li>
								<li><a href="category.html">MP3 Players</a></li>
							</ul>
						</div></li>
					<li class="dropdown"><a href="category.html">Shoes</a>
						<div class="dropdown-menu">
							<ul>
								<li><a href="category.html">Men</a></li>
								<li><a href="category.html">Women <span>&rsaquo;</span></a>
									<div class="dropdown-menu">
										<ul>
											<li><a href="category.html">New Sub Categories </a></li>
											<li><a href="category.html">Sub Categories </a></li>
										</ul>
									</div></li>
								<li><a href="category.html">Girls</a></li>
								<li><a href="category.html">Boys</a></li>
								<li><a href="category.html">Baby</a></li>
								<li><a href="category.html">Accessories <span>&rsaquo;</span></a>
									<div class="dropdown-menu">
										<ul>
											<li><a href="category.html">New Sub Categories</a></li>
											<li><a href="category.html">New Sub Categories</a></li>
											<li><a href="category.html">Sub Categories</a></li>
										</ul>
									</div></li>
							</ul>
						</div></li>
					<li class="dropdown"><a href="category.html">Watches</a>
						<div class="dropdown-menu">
							<ul>
								<li><a href="category.html">Men's Watches</a></li>
								<li><a href="category.html">Women's Watches</a></li>
								<li><a href="category.html">Kids' Watches</a></li>
								<li><a href="category.html">Accessories</a></li>
							</ul>
						</div></li>
					<li class="dropdown"><a href="category.html">Health &amp;
							Beauty</a>
						<div class="dropdown-menu">
							<ul>
								<li><a href="category.html">Perfumes</a></li>
								<li><a href="category.html">Makeup</a></li>
								<li><a href="category.html">Sun Care</a></li>
								<li><a href="category.html">Skin Care</a></li>
								<li><a href="category.html">Eye Care</a></li>
								<li><a href="category.html">Hair Care</a></li>
							</ul>
						</div></li>
					<li class="menu_brands dropdown"><a href="#">Brands</a>
						<div class="dropdown-menu">
							<div class="col-lg-1 col-md-2 col-sm-3 col-xs-6">
								<a href="#"><img src="image/product/apple_logo-60x60.jpg"
									title="Apple" alt="Apple" /></a><a href="#">Apple</a>
							</div>
							<div class="col-lg-1 col-md-2 col-sm-3 col-xs-6">
								<a href="#"><img src="image/product/canon_logo-60x60.jpg"
									title="Canon" alt="Canon" /></a><a href="#">Canon</a>
							</div>
							<div class="col-lg-1 col-md-2 col-sm-3 col-xs-6">
								<a href="#"><img src="image/product/hp_logo-60x60.jpg"
									title="Hewlett-Packard" alt="Hewlett-Packard" /></a><a href="#">Hewlett-Packard</a>
							</div>
							<div class="col-lg-1 col-md-2 col-sm-3 col-xs-6">
								<a href="#"><img src="image/product/htc_logo-60x60.jpg"
									title="HTC" alt="HTC" /></a><a href="#">HTC</a>
							</div>
							<div class="col-lg-1 col-md-2 col-sm-3 col-xs-6">
								<a href="#"><img src="image/product/palm_logo-60x60.jpg"
									title="Palm" alt="Palm" /></a><a href="#">Palm</a>
							</div>
							<div class="col-lg-1 col-md-2 col-sm-3 col-xs-6">
								<a href="#"><img src="image/product/sony_logo-60x60.jpg"
									title="Sony" alt="Sony" /></a><a href="#">Sony</a>
							</div>
							<div class="col-lg-1 col-md-2 col-sm-3 col-xs-6">
								<a href="#"><img src="image/product/canon_logo-60x60.jpg"
									title="test" alt="test" /></a><a href="#">test</a>
							</div>
							<div class="col-lg-1 col-md-2 col-sm-3 col-xs-6">
								<a href="#"><img src="image/product/apple_logo-60x60.jpg"
									title="test 3" alt="test 3" /></a><a href="#">test 3</a>
							</div>
							<div class="col-lg-1 col-md-2 col-sm-3 col-xs-6">
								<a href="#"><img src="image/product/canon_logo-60x60.jpg"
									title="test 5" alt="test 5" /></a><a href="#">test 5</a>
							</div>
							<div class="col-lg-1 col-md-2 col-sm-3 col-xs-6">
								<a href="#"><img src="image/product/canon_logo-60x60.jpg"
									title="test 6" alt="test 6" /></a><a href="#">test 6</a>
							</div>
							<div class="col-lg-1 col-md-2 col-sm-3 col-xs-6">
								<a href="#"><img src="image/product/apple_logo-60x60.jpg"
									title="test 7" alt="test 7" /></a><a href="#">test 7</a>
							</div>


						</div></li>
					<li class="dropdown wrap_custom_block hidden-sm hidden-xs"><a>Custom
							Block</a>
						<div class="dropdown-menu custom_block">
							<ul>
								<li>
									<table>
										<tbody>
											<tr>
												<td><img alt="" src="image/banner/cms-block.jpg"></td>
												<td><img alt="" src="image/banner/responsive.jpg"></td>
												<td><img alt="" src="image/banner/cms-block.jpg"></td>
											</tr>
											<tr>
												<td><h4>CMS Blocks</h4></td>
												<td><h4>Responsive Template</h4></td>
												<td><h4>Dedicated Support</h4></td>
											</tr>
											<tr>
												<td>This is a CMS block. You can insert any content
													(HTML, Text, Images) Here.</td>
												<td>This is a CMS block. You can insert any content
													(HTML, Text, Images) Here.</td>
												<td>This is a CMS block. You can insert any content
													(HTML, Text, Images) Here.</td>
											</tr>
											<tr>
												<td><strong><a class="btn btn-primary btn-sm"
														href="#">Read More</a></strong></td>
												<td><strong><a class="btn btn-primary btn-sm"
														href="#">Read More</a></strong></td>
												<td><strong><a class="btn btn-primary btn-sm"
														href="#">Read More</a></strong></td>
											</tr>
										</tbody>
									</table>
								</li>
							</ul>
						</div></li>
					<li class="dropdown"><a href="blog.html">Blog</a>
						<div class="dropdown-menu">
							<ul>
								<li><a href="blog.html">Blog</a></li>
								<li><a href="blog-grid.html">Blog Grid</a></li>
								<li><a href="blog-detail.html">Single Post</a></li>
							</ul>
						</div></li>
					<li class="dropdown"><a>Pages</a>
						<div class="dropdown-menu">
							<ul>
								<li><a href="category.html">Category (Grid/List)</a></li>
								<li><a href="product.html">Product Page</a></li>
								<li><a href="cart.html">Shopping Cart</a></li>
								<li><a href="checkout.html">Checkout</a></li>
								<li><a href="compare.html">Product Compare</a></li>
								<li><a href="wishlist.html">Wishlist</a></li>
								<li><a href="search.html">Search</a></li>
								<li><a href="manufacturer.html">Brands</a></li>
							</ul>
							<ul>
								<li><a href="about-us.html">About Us</a></li>
								<li><a href="elements.html">Elements</a></li>
								<li><a href="elements-forms.html">Forms</a></li>
								<li><a href="careers.html">Careers</a></li>
								<li><a href="faq.html">Faq</a></li>
								<li><a href="404.html">404</a></li>
								<li><a href="sitemap.html">Sitemap</a></li>
								<li><a href="contact-us.html">Contact Us</a></li>
								<li><a href="email-template" target="_blank">Email
										Template Page</a></li>
							</ul>
							<ul>
								<li><a href="login.html">Login</a></li>
								<li><a href="register.html">Register</a></li>
								<li><a href="my-account.html">My Account</a></li>
								<li><a href="order-history.html">Order History</a></li>
								<li><a href="order-information.html">Order Information</a></li>
								<li><a href="return.html">Return</a></li>
								<li><a href="gift-voucher.html">Gift Voucher</a></li>
							</ul>
						</div></li>
					<li class="custom-link-right"><a href="#" target="_blank">Buy
							Now!</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<!-- Main Menu End-->
</div>