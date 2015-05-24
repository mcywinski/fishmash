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

  def start
    exam = Exam.find params[:exam_id]
    result_dto = Hash.new
    result_dto[:started] = exam.start_assesment(api_get_user.id)
    result_dto[:message] = result_dto[:started] ? 'Exam succesfuly started' : 'This assesment has already been taken.'

    respond_with result_dto, location: ''
  end

  # Gets the next question to answer
  def get_question
    assesment = Exam.find(params[:exam_id]).get_assesment(api_get_user.id)
    answer = assesment.get_answer
    if answer.nil? # No more questions to answer -> exam's finished.
      assesment.finished = true
      assesment.save
      respond_with Answer.exam_finished_dto, location: '' and return
    end

    respond_with Answer.to_simple_dto(answer), location: ''
  end

  # Saves the answer in db
  def answer
    # TODO: Validate if this is user's question
    answer = Answer.find(params[:answer_id])

    if answer.finished?
      respond_with nil, status: :bad_request
    end

    answer_result = Hash.new
    answer_result[:saved] = answer.provide_answer(params[:answer_text])
    answer_result[:messsage] = answer_result[:saved] ? 'Answer saved' : 'An error occured while saving the answer'

    respond_with answer_result, location: ''
  end

  def summary
    assesment = Exam.find(params[:exam_id]).get_assesment(api_get_user.id)
    respond_with nil, status: :bad_request and return unless assesment.finished?
    answers = Array.new
    assesment.answers.each do |answer|
      answers.push Answer.to_full_dto(answer)
      answers.last[:exam_finished] = true
    end

    respond_with answers, location: ''
  end
end
