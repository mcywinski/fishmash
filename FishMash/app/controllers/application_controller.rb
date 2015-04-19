class ApplicationController < ActionController::Base
  # Prevent CSRF attacks by raising an exception.
  # For APIs, you may want to use :null_session instead.
  protect_from_forgery with: :exception
  helper_method :is_user_logged_in?

  def api
  end

  def is_user_logged_in?
    !(session[:logged_user_id].nil?)
  end

  def set_logged_user_id(id)
    session[:logged_user_id] = id
  end

  def get_logged_user_id
    session[:logged_user_id]
  end

  def stringify_errors(model)
  	errors = ""
  	model.errors.full_messages.each do |msg|
  		errors += "<p>" + msg + "</p>"
  	end
  	return errors
  end
end
