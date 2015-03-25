class ExamsController < ApplicationController

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
		wordlist = WordList.find(params[:exam][:wordlist_id]) 
		exam = Exam.new(new_exam_params)	
		wordlist_exam = WordListExam.new

		if wordlist
			if exam.save
				wordlist_exam.wordlist_id = wordlist.id
				wordlist_exam.exam_id = exam.id

				wordlist_exam.save
				redirect_to exams_path
			else
				flash[:errors] = stringify_errors(exam)
				redirect_to new_exam_path
			end
		else
			flash[:errors] = stringify_errors(exam)
			redirect_to new_exam_path
		end
	end

	private

	def new_exam_params
		params.require(:exam).permit(:name, :description, :date_practice_start, :date_practice_finish, :date_exam_start, :date_exam_finish, :word_count)
	end

end
