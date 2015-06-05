class Language < ActiveRecord::Base
  has_many :main_wordlists, class_name: "WordList", foreign_key: "main_language_id"
  has_many :foreign_wordlists, class_name: "WordList", foreign_key: "foreign_language_id"

  def to_dto
    dto = Hash.new
    dto[:id] = self.id
    dto[:name] = self.name
    dto[:description] = self.description

    return dto
  end
end
