class WordListExam < ActiveRecord::Base
  belongs_to :exam
  belongs_to :word_list
end
