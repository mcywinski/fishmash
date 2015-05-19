class Exam < ActiveRecord::Base
	has_many :word_list_exams
  	has_many :word_lists, through: :word_list_exams

  	validates :name, presence: true, length: { minimum: 1}
  	validates :word_count, presence: true
  	validates :date_exam_start, presence: true
  	validates :date_exam_finish, presence: true
		validates_with DatesSanityValidator

	def to_dto
		exam_dto = Hash.new
		exam_dto[:name] = self.name
		exam_dto[:word_count] = self.word_count
		exam_dto[:date_exam_start] = self.date_exam_start
		exam_dto[:date_exam_finish] = self.date_exam_finish

		exam_dto
	end

end
