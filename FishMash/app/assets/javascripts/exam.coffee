# jQuery selectors
createExamBtn = '#create-exam'

$(createExamBtn).click ->
	$('input[name="nazwa"]').prop('disabled', false)