class WordListsController < ApplicationController

	#Fetching all lists from database
	def index
		@lists = WordList.all
	end

	#Showing specific wordlist
	def show
		@list = WordList.find(params[:list_id])
	end
end
