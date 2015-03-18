class Api::ListsController < ApplicationController
  respond_to :json, :xml

  def index
    lists = WordList.all
    respond_with lists
  end

  def show
    list = WordList.find params[:id]
    respond_with list
  end

  private
end
