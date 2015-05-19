class DatesSanityValidator < ActiveModel::Validator
  include ApplicationHelper

  def validate(record)
    if record.date_exam_start >= record.date_exam_finish
      message = 'end must be after start.'
      record.errors.push(message)
      puts "#{record.class.name}:#{message}"
    end
  end
end