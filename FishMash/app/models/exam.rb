class Exam < ActiveRecord::Base
	has_many :word_list_exams
  	has_many :word_lists, through: :word_list_exams
end
