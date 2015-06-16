classChooserBtn = '.studclass-choose'
examId = $('#exam-id').val()
studentResultTableId = '#student-results-table'

minimumSatisfactoryResult = 0.5

calculateExamPassed = (answers) ->
  if answers == null
    return false
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
    examPassed = 0
    examNotPassed = 0
    iterator = 0

    $.ajax
      type: 'post'
      url: '/exams/' + examId + '/get_stats/' + classId
      success: (postback) ->
        $('#student-results').show();
        $(studentResultTableId).empty();
        for statRecord in postback
          noCalculation = false
          iterator++
          if statRecord.exam.is_finished
            examParticipated++
          else
            examNotParticipated++
            noCalculation = true

          if !noCalculation
            if calculateExamPassed(statRecord.answers)
              examPassed++
            else
              examNotPassed++

          if calculateExamPassed(statRecord.answers)
            resultColor = 'green'
            result = 'Passed'
          else
            if !statRecord.exam.is_finished
              resultColor = 'orange'
              result = 'Did not participate'
            else
              resultColor = 'red'
              result = 'Failed'
          $(studentResultTableId).append('<tr><td>' + iterator + '</td><td>' + statRecord.login + '</td><td style="color: ' + resultColor + ';">' + result + '</td></tr>')

        data = {
          labels: ['Participated: ' + String(calculatePercentage(examParticipated, examParticipated + examNotParticipated)) + '%', 'Did not participate: ' + String(calculatePercentage(examNotParticipated, examParticipated + examNotParticipated)) + '%'],
          series: [examParticipated, examNotParticipated]
        }
        new Chartist.Pie('#participants-chart', data);

        passedLabelPassed = 'Passed students: ' + String(calculatePercentage(examPassed, examPassed + examNotPassed + examNotParticipated)) + '%'
        passedLabelFailed = 'Failed students: ' + String(calculatePercentage(examNotPassed, examPassed + examNotPassed)) + '%'
        passedLabelNotParticipated = 'Did not participate: ' + String(calculatePercentage(examNotParticipated, examPassed + examNotPassed + examNotParticipated)) + '%'

        dataPassed = {
          labels: [passedLabelPassed, passedLabelFailed, passedLabelNotParticipated],
          series: [examPassed, examNotPassed, examNotParticipated]
        }
        new Chartist.Pie('#passed-chart', dataPassed);

      error: () ->
        alert('An error occured while getting class information. Try again, please.');
