classChooserBtn = '.studclass-choose'
examId = $('#exam-id').val()

$ ->
  $(classChooserBtn).click ->
    classId = $(this).attr('class-id')


    $.ajax
      type: 'post'
      url: '/exams/' + examId + '/get_stats/' + classId
      success: (postback) ->
        alert(postback)
      error: () ->
        alert('An error occured while getting class information. Try again, please.');
