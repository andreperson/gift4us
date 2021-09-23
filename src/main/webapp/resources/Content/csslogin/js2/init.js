_5grid.ready(function() {

	// Dropdown Menus (desktop only)
		if (_5grid.isDesktop)
			$('#page-header nav > ul').dropotron({
				offsetY: -13,
				IEOffsetY: 7
			});

	// Banner slider
		var banner = $('#banner');
		if (banner.length > 0)
		{
			banner.slidertron({
				mode: 'fade',	// Change this to 'slide' to switch back to sliding mode
				viewerSelector: '.viewer',
				reelSelector: '.viewer .reel',
				slidesSelector: '.viewer .reel .slide',
				navNextSelector: '.nav-next',
				navPreviousSelector: '.nav-previous',
				slideCaptionSelector: '.caption-',
				captionLineSelector: '.caption-line-',
				captionLines: 2,
				advanceDelay: 7500,
				speed: 1000,
				autoFit: true,
				autoFitAspectRatio: (1200 / 440)
			});

			if (_5grid.isMobile)
			{
				_5grid.orientationChange(function() {
					banner.trigger('slidertron_reFit');
				});

				_5grid.mobileUINavOpen(function() {
					banner.trigger('slidertron_stopAdvance');
				});
			}
		}

});