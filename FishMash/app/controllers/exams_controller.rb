class ExamsController < ApplicationController
	before_action :validate_assesment, only: [:answer, :save_answer]

	def index
		@exams = Exam.all
	end

	def show
		@exam = Exam.find(params[:id])
	end

	def new
		@lists = WordList.all
	end

	def create
		@wordlists = Array.new
		# Removing first, empty element from wordlist_id array
		wordlist_id_helper = params[:exam][:wordlist_id].drop(1)
		wordlists = WordList.find(wordlist_id_helper)
		exam = Exam.new(new_exam_params)	

		if exam.save
			wordlists.each do |wordlist|
				wordlists_exam = WordListExam.new(wordlist_id: wordlist.id, exam_id: exam.id)
				wordlists_exam.save
				redirect_to exams_path
			end
		else
			flash[:errors] = stringify_errors(exam)
			redirect_to new_exam_path
		end
	end

	def begin
		@exam = Exam.find params[:exam_id]
	end

	def start
		exam = Exam.find params[:exam_id]
		if exam.start_assesment(get_logged_user_id)
			render 'Whee!'
		else
			flash[:errors] = 'This assesment has already been taken.'
			redirect_to exams_path
		end
	end

	def answer
		assesment = Assesment.find_by(exam_id: @exam.id)
		@answer = assesment.answers.where("finished = false or finished = NULL").first
	end

	def save_answer
		assesment = Assesment.find_by(exam_id: @exam.id)
		answer = Answer.find(params[:answer][:answer_id])
		answer.answer = params[:answer][:answer]
		answer.finished = true
		answer.passed = (answer.answer.downcase.eql? answer.word.phrase.downcase)
		answer.save

		redirect_to exam_answer_path(@exam)
	end

	private

	def validate_assesment
		@exam = Exam.find params[:exam_id]
		assesment = Assesment.find_by(exam_id: @exam.id)
		if assesment.nil?
			flash[:errors] = 'You haven\'t started this assesment yet.'
			redirect_to exams_path and return
		end
	end

	def new_exam_params
		params.require(:exam).permit(:name, :description, :date_practice_start, :date_practice_finish, :date_exam_start, :date_exam_finish, :word_count)
	end

end
