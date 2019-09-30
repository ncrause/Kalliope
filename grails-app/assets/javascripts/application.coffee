(($) ->
	$ ->
		# Make all AJAX queries display the spinner
		$(document).ajaxStart ->
			$('#spinner').fadeIn()
		.ajaxStop ->
			$('#spinner').fadeOut()

		# enable tooltip globally
		$('.has-tooltip, [data-toggle="tooltip"]').tooltip()

		# initialize all global behaviours
		#$(document).data('allfun-behaviours', new allfun.Behaviours($))
			
) jQuery
