class WordListMembership < ActiveRecord::Base
  belongs_to :word
  belongs_to :word_list
end
