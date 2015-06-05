class Api::ListsController < ApplicationController
  protect_from_forgery with: :null_session
  before_action :api_authorize

  respond_to :json, :xml

  def index
    user = api_get_user
    if user.is_teacher?
			lists = user.owned_wordlists
		elsif user.is_student?
			lists = user.get_available_wordlists
		end

    lists_dto = Array.new
    lists.each do |list|
      lists_dto.push(list.to_dto(false))
    end

    respond_with lists_dto
  end

  def show
    list = WordList.find params[:id]
    list_dto = list.to_dto(true)

    respond_with list_dto
  end

  def add
    word_list = WordList.find(params[:list_id])
    word = Word.find(params[:word_id])

    word_list.words.push word unless word_list.words.include? word

    respond_with nil, location: ''
  end

  def remove
    word_list = WordList.find(params[:list_id])
    word = Word.find(params[:word_id])

    word_list.words.delete word if word_list.words.include? word

    respond_with nil, location: ''
  end

  private
end
