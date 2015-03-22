class Word < ActiveRecord::Base
  belongs_to :phrase_language, class_name: "Language", foreign_key: "phrase_language_id"
  belongs_to :meaning_language, class_name: "Language", foreign_key: "meaning_language_id"

  has_many :word_list_memberships
  has_many :word_lists, through: :word_list_memberships

  validates :phrase, presence: true, length: { minimum: 1}
  validates :meaning, presence: true, length: { minimum: 1}
  validates :phrase_language, presence: true
  validates :meaning_language, presence: true

  # Returns an array of words hashes belonging to the list of given ID.
  def self.get_list_dto(list_id)
  	word_list = WordList.find list_id
  	list = Array.new

  	# Converting dependent words to hashes
  	word_list.words.each do |word|
  		word_dto = Hash.new
  		word_dto[:id] = word.id
  		word_dto[:phrase] = word.phrase
  		word_dto[:meaning] = word.meaning
  		list.push(word_dto)
  	end

  	return list
  end
end
