module ExamCommon
  def ExamCommon.is_start_overdue?(id)
    exam = get_exam(id)
    return (exam.date_exam_finish < Date.today or exam.date_exam_start > Date.today)
  end

  def ExamCommon.is_practice_overdue?(id)
    exam = get_exam(id)
    return (exam.date_practice_finish < Date.today or exam.date_practice_start > Date.today)
  end

  def ExamCommon.get_exam(id)
    Exam.find id
  end
end
