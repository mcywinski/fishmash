class Api::ExamsController < ApplicationController
  protect_from_forgery with: :null_session
  respond_to :json, :xml
  before_action :api_authorize

  def index
    exams_dto = Array.new
    Exam.all.each do |exam|
      exams_dto.push exam.to_dto user_id: api_get_user.id
    end

    respond_with exams_dto
  end

  def show
    respond_with Exam.find(params[:id]).to_dto(user_id: api_get_user.id)
  end
end
