class Exam < ActiveRecord::Base
	has_many :word_list_exams
	has_many :word_lists, through: :word_list_exams
	has_many :assesments

	validates :name, presence: true, length: { minimum: 1}
	validates :word_count, presence: true
	validates :date_exam_start, presence: true
	validates :date_exam_finish, presence: true

	def start_assesment(user_id)
		existing_assesment = Assesment.where(exam_id: self.id, user_id: user_id).first
		return false unless existing_assesment.nil?

		assesment = Assesment.new(user_id: user_id, exam_id: exam_id, time_started: Time.now)
		if assesment.save
			return assesment
		else
			return false
		end

	end
end
