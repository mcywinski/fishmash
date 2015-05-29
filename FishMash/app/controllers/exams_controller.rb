class ExamsController < ApplicationController
	before_action :validate_assesment, only: [:answer, :save_answer, :summary]
	before_action only: [:start, :begin] do
		if ExamCommon.is_start_overdue? params[:exam_id]
			redirect_to exams_path
		end
	end
	before_action only: [:learn, :begin] do
		if ExamCommon.is_practice_overdue? params[:exam_id]
			redirect_to exams_path
		end
	end

	MSG_EXAM_TIME_FINISHED = 'Time\'s up. Exam is finished'

	def index
		@exams = Exam.all
	end

	def show
		@exam = Exam.find(params[:id])
	end

	def new
		@lists = WordList.all
	end

	def learn
		@exam = Exam.find(params[:exam_id])
		@words = Array.new
		@exam.word_lists.each do |wordlist|
			wordlist.words.each do |word|
				word_json = Hash.new
				word_json[:phrase] = word.phrase
				word_json[:meaning] = word.meaning
				@words.push word_json
			end
		end
	end

	def create
		@wordlists = Array.new
		# Removing first, empty element from word_list_id array
		wordlist_id_helper = params[:exam][:word_list_id].drop(1)
		wordlists = WordList.find(wordlist_id_helper)
		exam = Exam.new(new_exam_params)
		wordlists.each do |wordlist|
			exam.word_lists.push wordlist
		end

		if exam.save
			flash[:success] = 'Exam has been successfuly created'
			redirect_to exams_path
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
			redirect_to exam_answer_path(exam)
		else
			flash[:errors] = 'This assesment has already been taken.'
			redirect_to exams_path
		end
	end

	def answer
		assesment = @exam.get_assesment(get_logged_user_id)
		@answer = assesment.get_answer

		unless assesment.is_time_exceeded?
			flash[:errors] = MSG_EXAM_TIME_FINISHED
			finish_exam(assesment) and return
		end

		if @answer.nil? # No more questions to answer -> exam's finished.
			finish_exam(assesment)
		end
	end

	def save_answer
		@assesment = @exam.get_assesment(get_logged_user_id)
		unless @assesment.is_time_exceeded?
			flash[:errors] = MSG_EXAM_TIME_FINISHED
			finish_exam(@assesment) and return
		end

		# TODO: Validate if this is user's question.
		answer = Answer.find(params[:answer][:answer_id])
		answer.provide_answer(params[:answer][:answer])

		redirect_to exam_answer_path(@exam)
	end

	def summary
		@assesment = @exam.get_assesment(get_logged_user_id)
	end

	private

	def validate_assesment
		@exam = Exam.find params[:exam_id]
		assesment = @exam.get_assesment(get_logged_user_id)
		if assesment.nil?
			flash[:errors] = 'You haven\'t started this assesment yet.'
			redirect_to exams_path and return
		end
	end

	def new_exam_params
		params.require(:exam).permit(:name, :description, :date_practice_start, :date_practice_finish, :date_exam_start, :date_exam_finish, :word_count, :time_limit)
	end

	def finish_exam(assesment)
		assesment.finished = true
		assesment.save
		redirect_to exam_summary_path(@exam)
	end

end
