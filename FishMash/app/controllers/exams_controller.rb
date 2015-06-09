class ExamsController < ApplicationController
	before_action :require_login
	before_action :require_teacher, only: [:new, :create, :stats]
	before_action :validate_assesment, only: [:answer, :save_answer, :summary]
	before_action only: [:start, :begin] do # Checks validity of dates for exams
		if ExamCommon.is_start_overdue? params[:exam_id]
			redirect_to exams_path
		end
	end
	before_action only: [:learn] do # Checks validity of dates for learning
		if ExamCommon.is_practice_overdue? params[:exam_id]
			redirect_to exams_path
		end
	end
	before_action only: [:stats] do
		unless ExamCommon.is_owned_by(params[:exam_id], get_logged_user_id)
			redirect_to exams_path
		end
	end

	respond_to :json, :xml, only: [:get_stats] #Helper action for obtaining stats information for stats view.

	MSG_EXAM_TIME_FINISHED = 'Time\'s up. Exam is finished'

	def index
		user = get_logged_user
		if user.is_teacher?
			@exams = user.owned_exams
		elsif user.is_student?
			@exams = user.get_available_exams
		end
	end

	def show
		@exam = Exam.find(params[:id])
	end

	def new
		user = get_logged_user
		@lists = user.owned_wordlists
		@student_classes = user.owned_student_classes
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
		class_list_id_helper = params[:exam][:student_class_id].drop(1)
		wordlists = WordList.find(wordlist_id_helper)
		student_classes = StudentClass.find(class_list_id_helper)
		exam = Exam.new(new_exam_params)
		wordlists.each do |wordlist|
			exam.word_lists.push wordlist
		end
		student_classes.each do |studclass|
			exam.student_classes.push studclass
		end
		exam.owner = get_logged_user
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
		@finish_time = (assesment.time_started + @exam.time_limit.minutes)
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

	def stats
		@exam = Exam.find params[:exam_id]
	end

	def get_stats
		exam = Exam.find params[:exam_id]
		student_class = (exam.student_classes.select { |stud_class| stud_class.id.equal? params[:class_id].to_i }).first
		if student_class.nil?
			respond_with nil, status: :bad_request, location: ''
		end
		stats = student_class.get_stats(exam.id)
		respond_with stats, location: ''
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
