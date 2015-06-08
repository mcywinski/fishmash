classChooserBtn = '.studclass-choose'
examId = $('#exam-id').val()

minimumSatisfactoryResult = 0.5

calculateExamPassed = (answers) ->
  len = answers.length
  questionsPassed = 0

  for answer in answers
    if answer.passed
      questionsPassed++

    successFactor = questionsPassed / len

    return successFactor >= minimumSatisfactoryResult

$ ->
  examParticipated = 0
  examNotParticipated = 0

  examPassed = 0
  examNotPassed = 0

  $(classChooserBtn).click ->
    classId = $(this).attr('class-id')

    examParticipated = 0
    examNotParticipated = 0

    $.ajax
      type: 'post'
      url: '/exams/' + examId + '/get_stats/' + classId
      success: (postback) ->
        for statRecord in postback
          noCalculation = false

          if statRecord.exam.is_finished
            examParticipated++
          else
            examNotParticipated++
            examNotPassed++
            noCalculation = true

          if !noCalculation
            if calculateExamPassed(statRecord.answers)
              examPassed++
            else
              examNotPassed++

      error: () ->
        alert('An error occured while getting class information. Try again, please.');
