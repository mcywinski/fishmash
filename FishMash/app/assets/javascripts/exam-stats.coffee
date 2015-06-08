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

sum = (a, b) ->
  return a + b

calculatePercentage = (num, total) ->
  return (num / total) * 100

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

        data = {
          labels: ['Participated: ' + String(calculatePercentage(examParticipated, examParticipated + examNotParticipated)) + '%', 'Did not participate: ',  + String(calculatePercentage(examNotParticipated, examParticipated + examNotParticipated)) + '%'],
          series: [examParticipated, examNotParticipated]
        }
        new Chartist.Pie('#participants-chart', data);

        dataPassed = {
          labels: ['Passed students: ' + String(calculatePercentage(examPassed, examPassed + examNotPassed)) + '%', 'Failed students: ' + String(calculatePercentage(examNotPassed, examPassed + examNotPassed)) + '%'],
          series: [examPassed, examNotPassed]
        }
        new Chartist.Pie('#passed-chart', dataPassed);

      error: () ->
        alert('An error occured while getting class information. Try again, please.');
