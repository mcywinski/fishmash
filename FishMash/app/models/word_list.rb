class WordList < ActiveRecord::Base
  belongs_to :main_language, class_name: "Language", foreign_key: "main_language_id"
  belongs_to :foreign_language, class_name: "WordList", foreign_key: "foreign_language_id"

  has_many :word_list_memberships
  has_many :words, through: :word_list_memberships

  validates :name, presence: true, length: { minimum: 3 }
  validates :main_language, presence: true
  validates :foreign_language, presence: true
end
