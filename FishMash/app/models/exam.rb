class Exam < ActiveRecord::Base
	has_many :word_list_exams
  	has_many :word_lists, through: :word_list_exams

  	validates :name, presence: true, length: { minimum: 1}
  	validates :word_count, presence: true
  	validates :date_exam_start, presence: true
  	validates :date_exam_finish, presence: true
end
