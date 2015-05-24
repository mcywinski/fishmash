class Answer < ActiveRecord::Base
  belongs_to :word
  belongs_to :user
  belongs_to :assesment

  def self.to_simple_dto(answer)
    answer_dto = answer.to_base_dto
    return answer_dto
  end

  def self.to_full_dto(answer)
    answer_dto = answer.to_base_dto
    answer_dto[:phrase] = answer.word.phrase
    return answer_dto
  end

  def provide_answer(answer_text)
    self.answer = answer_text
    self.finished = true
    self.passed = (self.answer.downcase.eql? self.word.phrase.downcase)
    self.save
  end

  def self.exam_finished_dto
    answer_dto = Hash.new
    answer_dto[:id] = 0
    answer_dto[:exam_finished] = true
    answer_dto
  end

  def to_base_dto
    answer_dto = Hash.new

    answer_dto[:id] = self.id
    answer_dto[:answer] = self.answer
    answer_dto[:finished] = self.finished
    answer_dto[:passed] = self.passed
    answer_dto[:meaning] = self.word.meaning
    answer_dto[:exam_finished] = false

    answer_dto
  end
end
