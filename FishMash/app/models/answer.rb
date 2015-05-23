class Answer < ActiveRecord::Base
  belongs_to :word
  belongs_to :user
  belongs_to :assesment

  after_initialize :init

  def init
    self.passed = nil
    self.finished = false
  end
end
