require 'active_support'

class Assesment < ActiveRecord::Base
  belongs_to :exam
  belongs_to :user

  has_many :answers

  def get_answer
    self.answers.where("finished = false or finished IS NULL").first
  end

  def is_time_exceeded?
    return false if self.exam.time_limit.nil?
    if self.exam.time_limit > 0
      return (self.time_started + self.exam.time_limit.minutes) >= Time.now
    else
      return false
    end
  end
end
