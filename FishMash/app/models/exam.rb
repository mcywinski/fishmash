class Exam < ActiveRecord::Base
	has_many :word_list_exams
	has_many :word_lists, through: :word_list_exams
	has_many :student_class_allowed_exams
	has_many :student_classes, through: :student_class_allowed_exams
	has_many :assesments
	belongs_to :owner, class_name: 'User', foreign_key: :owner_id

	validates :name, presence: true, length: { minimum: 1}
	validates :word_count, presence: true
	validates :date_exam_start, presence: true
	validates :date_exam_finish, presence: true
	validates :time_limit, presence: true

	def to_dto(options)
		exam_dto = Hash.new
		exam_dto[:id] = self.id
		exam_dto[:name] = self.name
		exam_dto[:date_exam_start] = self.date_exam_start
		exam_dto[:date_exam_finish] = self.date_exam_finish
		exam_dto[:date_practice_start] = self.date_practice_start
		exam_dto[:date_practice_finish] = self.date_practice_finish
		exam_dto[:word_count] = self.word_count
		exam_dto[:time_limit] = self.time_limit

		if options[:user_id]
			exam_dto[:is_finished] = self.is_finished?(options[:user_id])
		end

		exam_dto
	end

	def is_finished?(user_id)
		assesment = self.get_assesment(user_id)
		if assesment.nil?
			return false
		else
			return assesment.finished?
		end
	end

	def start_assesment(user_id)
		existing_assesment = Assesment.where(exam_id: self.id, user_id: user_id).first
		return false unless existing_assesment.nil?

		assesment = Assesment.new(user_id: user_id, exam_id: self.id, time_started: Time.now)
		if assesment.save
			return false if self.word_lists.length.eql? 0 # TODO: Return statuses instead of true or false
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

	def get_assesment(user_id)
		existing_assesment = Assesment.where(exam_id: self.id, user_id: user_id).first
	end
end
