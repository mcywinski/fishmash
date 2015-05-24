class Answer < ActiveRecord::Base
  belongs_to :word
  belongs_to :user
  belongs_to :assesment

  def to_dto
    answer_dto = Hash.new

    answer_dto[:id] = self.id
    answer_dto[:answer] = self.answer
    answer_dto[:finished] = self.finished
    answer_dto[:passed] = self.passed
    answer_dto[:meaning] = self.word.meaning

    return answer_dto
  end

  def provide_answer(answer_text)
    self.answer = answer_text
    self.finished = true
    self.passed = (self.answer.downcase.eql? self.word.phrase.downcase)
    self.save
  end
end
