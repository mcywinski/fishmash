class StudentClass < ActiveRecord::Base
  has_many :student_class_memberships
  has_many :users, through: :student_class_memberships
  has_many :student_class_allowed_exams
	has_many :exams, through: :student_class_allowed_exams
  belongs_to :owner, class_name: 'User', foreign_key: :owner_id

  validates :name, presence: true

  def get_stats(exam_id)
    stats = Array.new
    self.users.each do |student|
      exam = Exam.find(exam_id)

      statistic_dto = Hash.new
      statistic_dto[:login] = student.login
      statistic_dto[:user_id] = student.id
      statistic_dto[:exam] = exam.to_dto(user_id: student.id)

      # Append answers if they were provided
      if exam.is_finished?(student.id)
        assesment = Assesment.where(user_id: student.id, exam_id: exam.id).first
        answers = Array.new
        assesment.answers.each do |answer|
          answers.push Answer.to_simple_dto(answer)
        end
        statistic_dto[:answers] = answers
      else
        statistic_dto[:answers] = nil
      end

      stats.push statistic_dto
    end

    return stats
  end

end
