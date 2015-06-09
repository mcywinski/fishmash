class Api::LanguagesController < ApplicationController
  protect_from_forgery with: :null_session
  respond_to :json, :xml
  before_action :api_authorize

  def index
    languages = Array.new
    Language.all.each do |lang|
      languages.push lang.to_dto
    end

    respond_with languages
  end
end
