class LanguageDifferenceValidator < ActiveModel::Validator
  include ApplicationHelper

  def validate(record)
    if record.main_language_id == DEFAULT_OPTION_ID or record.foreign_language_id == DEFAULT_OPTION_ID
      message = 'must be selected.'
      record.errors[:foreign_language_id] << message
      puts "#{record.class.name}:#{message}"
    end

    if record.main_language_id == record.foreign_language_id
      message = 'must be different.'
      record.errors[:foreign_language_id] << message
      puts "#{record.class.name}:#{message}"
    end
  end
end
