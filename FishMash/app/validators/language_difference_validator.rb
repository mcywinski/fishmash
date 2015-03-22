class LanguageDifferenceValidator < ActiveModel::Validator
  include ApplicationHelper

  def validate(record)
    message = 'A language must be selected.'
    if record.main_language_id == DEFAULT_OPTION_ID or record.foreign_language_id == DEFAULT_OPTION_ID
      record.errors[:foreign_language_id] << message
      puts "#{record.class.name}:#{message}"
    end

    message = 'Languages must be different.'
    if record.main_language_id == record.foreign_language_id
      record.errors[:foreign_language_id] << message
      puts "#{record.class.name}:#{message}"
    end
  end
end
