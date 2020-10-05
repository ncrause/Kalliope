(($) ->
	$ ->
		$('#enableAdvance').change ->
			$('#advance').prop('disabled', !$(this).is(':checked'))
) jQuery
