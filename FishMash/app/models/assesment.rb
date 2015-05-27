class Assesment < ActiveRecord::Base
  belongs_to :exam
  belongs_to :user

  has_many :answers

  def get_answer
    self.answers.where("finished = 0 or finished IS NULL").first
  end
end
