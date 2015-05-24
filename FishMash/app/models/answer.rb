class Answer < ActiveRecord::Base
  belongs_to :word
  belongs_to :user
  belongs_to :assesment
end
