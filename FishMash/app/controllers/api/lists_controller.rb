class Api::ListsController < ApplicationController
  respond_to :json, :xml

  def index
    lists = WordList.all

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

  private
end
