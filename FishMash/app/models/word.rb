class Word < ActiveRecord::Base
  belongs_to :phrase_language, class_name: 'Language', foreign_key: 'phrase_language_id'
  belongs_to :meaning_language, class_name: 'Language', foreign_key: 'meaning_language_id'

  has_many :word_list_memberships
  has_many :word_lists, through: :word_list_memberships

  validates :phrase, presence: true, length: { minimum: 1}
  validates :meaning, presence: true, length: { minimum: 1}
  validates :phrase_language, presence: true
  validates :meaning_language, presence: true

  before_save :ensure_unique

  # Checks if a duplicate of a pair phrase - meaning already exists in the database.
  # If so, false is returned and true otherwise.
  def ensure_unique
    !Word.exists?(phrase: self.phrase, meaning: self.meaning, phrase_language_id: self.phrase_language_id, meaning_language_id: self.meaning_language_id)
  end

  # Returns an array of words hashes belonging to the list of given ID.
  def self.get_list_dto(list_id)
  	word_list = WordList.find list_id
  	list = Array.new

  	# Converting dependent words to hashes
  	word_list.words.each do |word|
  		word_dto = word.to_dto
  		list.push(word_dto)
  	end

  	return list
  end

  # Converts a word instance to DTO.
  def to_dto
    word_dto = Hash.new
    word_dto[:id] = self.id
    word_dto[:phrase] = self.phrase
    word_dto[:meaning] = self.meaning

    word_dto
  end
end
