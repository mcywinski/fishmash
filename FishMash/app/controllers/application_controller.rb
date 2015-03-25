class ApplicationController < ActionController::Base
  # Prevent CSRF attacks by raising an exception.
  # For APIs, you may want to use :null_session instead.
  protect_from_forgery with: :exception

  def api
  end

  def stringify_errors(model)
  	errors = ""
  	model.errors.full_messages.each do |msg|
  		errors += "<p>" + msg + "</p>"
  	end
  	return errors
  end
end
