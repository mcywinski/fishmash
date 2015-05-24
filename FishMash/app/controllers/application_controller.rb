class ApplicationController < ActionController::Base
  # Prevent CSRF attacks by raising an exception.
  # For APIs, you may want to use :null_session instead.
  protect_from_forgery with: :exception
  helper_method :is_user_logged_in?
  helper_method :get_logged_user_id

  def api
  end

  def require_login
    unless is_user_logged_in?
      flash[:errors] = 'You must be logged in to access this area.'
      redirect_to root_path
    end
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

  def render_status(status_code)
    render nothing: true, status: status_code
  end

  def api_authorize
    token = ApiToken.find_by(token: api_token)
    render_status :unauthorized if !token
  end

  def api_get_user
    token = ApiToken.find_by(token: api_token)
    return token.user
  end

  def api_token
    params.require(:api_token)
  end
end
