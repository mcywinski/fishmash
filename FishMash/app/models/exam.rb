class Exam < ActiveRecord::Base
	has_many :word_list_exams
	has_many :word_lists, through: :word_list_exams
	has_many :assesments

	validates :name, presence: true, length: { minimum: 1}
	validates :word_count, presence: true
	validates :date_exam_start, presence: true
	validates :date_exam_finish, presence: true

	def start_assesment(user_id)
		existing_assesment = Assesment.where(exam_id: self.id, user_id: user_id).first
		return false unless existing_assesment.nil?

		assesment = Assesment.new(user_id: user_id, exam_id: self.id, time_started: Time.now)
		if assesment.save
			gen_count = self.word_count / self.word_lists.length
			randomizer = Random.new(Time.now.to_i)
			# Generate answer for every word list
			self.word_lists.each do |word_list|
				used_indexes = Array.new
				for i in 1..gen_count
					index = 0
					begin
						index = randomizer.rand(word_list.words.length)
					end until !used_indexes.include? index
					used_indexes.push index

					word = word_list.words[index]
					Answer.new(user_id: user_id, assesment_id: assesment.id, word_id: word.id).save
				end
			end
			return assesment
		else
			return false
		end

	end
end
