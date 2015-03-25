class Api::WordsController < ApplicationController
  protect_from_forgery with: :null_session

  respond_to :json, :xml

  def create
    word = Word.where(phrase: word_create_params[:phrase], meaning: word_create_params[:meaning], phrase_language_id: word_create_params[:phrase_language_id], meaning_language_id: word_create_params[:meaning_language_id]).first

    if word.nil?
      word = Word.new(word_create_params)
      if word.save
        respond_with word, location: '' # FIXME: Extract methods requiring location argument to external method
      else
        respond_with nil, status: :bad_request, location: ''
      end
    else
      respond_with word, location: ''
    end
  end

  private

  def word_create_params
    params.require(:word).permit(:phrase, :meaning, :phrase_language_id, :meaning_language_id)
  end
end
