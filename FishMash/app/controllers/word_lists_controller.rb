class WordListsController < ApplicationController

	# Displaying all lists
	def index
		@lists = WordList.all
	end

	# Displaying specific wordlist
	def show
		@list = WordList.find(params[:id])
	end

	# View for creating the new list
	def new
		@languages = Language.all
	end

	# Creates a new instance of WordList
	def create
		word_list = WordList.new(word_list_create_params)
		if word_list.save
			redirect_to wordlists_path
		else
			flash[:errors] = stringify_errors(word_list)
			redirect_to new_wordlist_path
		end
	end

	private

	def word_list_create_params
		params.require(:word_list).permit(:name, :description, :main_language_id, :foreign_language_id)
	end

end
