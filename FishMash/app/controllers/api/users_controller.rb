class Api::UsersController < ApplicationController
  protect_from_forgery with: :null_session
  respond_to :json, :xml
  before_action :api_authorize, except: [:authenticate]

  def authenticate
    user = User.find_by(login: user_authentication_params[:login]).try(:authenticate, user_authentication_params[:password])
    if user
      token = ApiToken.new
      puts token.inspect
      user.api_tokens.push token
      respond_with token, location: ''
    else
      render_status :unauthorized
    end
  end

  def show
    respond_with api_get_user.to_dto, location: ''
  end

  private
    def user_authentication_params
      params.require(:user).permit(:login, :password)
    end
end
