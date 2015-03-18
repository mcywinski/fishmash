class WordListsController < ApplicationController

	#Fetching all lists from database
	def index
		@lists = WordLists.all
	end

	#Showing specific wordlist
	def show
		@list = WordLists.find(params[:list_id])
	end
end
