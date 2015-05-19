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

	private

	def new_exam_params
		params.require(:exam).permit(:name, :description, :date_practice_start, :date_practice_finish, :date_exam_start, :date_exam_finish, :word_count)
	end

end
