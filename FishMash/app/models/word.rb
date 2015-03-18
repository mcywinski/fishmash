class Word < ActiveRecord::Base
  belongs_to :phrase_language, class_name: "Language", foreign_key: "phrase_language_id"
  belongs_to :meaning_language, class_name: "Language", foreign_key: "meaning_language_id"

  validates :phrase, presence: true, length: { minimum: 1}
  validates :meaning, presence: true, length: { minimum: 1}
  validates :phrase_language, presence: true
  validates :meaning_language, presence: true
end
