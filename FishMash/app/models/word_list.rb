class WordList < ActiveRecord::Base
  belongs_to :main_language, class_name: "Language", foreign_key: "main_language_id"
  belongs_to :foreign_language, class_name: "WordList", foreign_key: "foreign_language_id"

  has_many :word_list_memberships
  has_many :words, through: :word_list_memberships

  validates :name, presence: true, length: { minimum: 3 }
  validates :main_language, presence: true
  validates :foreign_language, presence: true

  # Returns a hash containing word list details.
  def to_dto(get_words)
  	# Converting the main object
  	list_dto = Hash.new
  	list_dto[:id] = self.id
  	list_dto[:name] = self.name
  	list_dto[:description] = self.description
  	list_dto[:main_language_id] = self.main_language_id
  	list_dto[:foreign_language_id] = self.foreign_language_id
  	list_dto[:updated_at] = self.updated_at

  	full_list = Hash.new
    full_list[:details] = list_dto

  	if get_words
    	words_dto = Word.get_list_dto(self.id)
    	full_list[:words] = words_dto
	end

  	return full_list
  end
end
