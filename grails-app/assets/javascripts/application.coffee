(($) ->
	$ ->
		# Make all AJAX queries display the spinner
		$(document).ajaxStart ->
			$('#spinner').fadeIn()
		.ajaxStop ->
			$('#spinner').fadeOut()

		# enable tooltip globally
		$('.has-tooltip, [data-toggle="tooltip"]').tooltip()
		
		$('.clipboardable').click (evt) ->
			evt.preventDefault()
			
			target = $($(this).data('clipboard-target'))
			
			target[0].select()
			target[0].setSelectionRange(0, 99999)
			document.execCommand('copy')
			
			console.log(target[0]);

		# initialize all global behaviours
		#$(document).data('allfun-behaviours', new allfun.Behaviours($))
			
) jQuery
