class Api::UsersController < ApplicationController
  protect_from_forgery with: :null_session
  respond_to :json, :xml
  before_action :api_authorize, except: [:authenticate]

  def authenticate
    user = User.find_by(login: user_authentication_params[:login]).try(:authenticate, user_authentication_params[:password])
    if !user
      render_status :unauthorized
    else
      token = ApiToken.new
      puts token.inspect
      user.api_tokens.push token
      respond_with token, location: ''
    end
  end

  def show
    respond_with api_get_user.to_dto, location: ''
  end

  def set_password
    user = api_get_user
    pass_params = set_password_params
    result = user.change_password(pass_params[:password_old], pass_params[:password], pass_params[:password_confirmation])
    success = result.equal? UserCommon::PASS_CHANGE_SUCCESS
    result = { success: success, result_status: result  }

    respond_with result, location: ''
  end

  private
    def user_authentication_params
      params.require(:user).permit(:login, :password)
    end

  def set_password_params
    { password_old: params.require(:password_old), password: params.require(:password), password_confirmation: params.require(:password_confirmation) }
  end
end
