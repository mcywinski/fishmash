class UsersController < ApplicationController
  before_action :require_login, only: [:profile, :set_password]
  layout 'blank', only: [:login, :authenticate]

  def register
    @user = User.new
  end

  def create
    @user = User.new user_create_params
    result = @user.save
    if !@user.save
      @errors = stringify_errors(@user)
      render 'register'
    else
      flash[:success] = 'Account has been successfuly created.'
      redirect_to root_path
    end
  end

  def login
  end

  def authenticate
    user = User.find_by(login: user_login_params[:login]).try(:authenticate, user_login_params[:password])
    if !user
      @errors = 'Invalid login or password provided'
      render 'login'
    else
      set_logged_user_id(user.id)
      flash[:success] = "You've successfuly signed in."
      redirect_to wordlists_path
    end
  end

  def logout
    session.destroy
    flash[:success] = 'You are successfuly logged out.'
    redirect_to root_path
  end

  def profile
    @user = User.find(get_logged_user_id)
  end

  def set_password
    #Routing check
    if params[:user_id].to_i != get_logged_user_id
      flash[:errors] = "Invalid user profile!"
      redirect_to root_path and return
    end

    #Old password check
    user = User.find(get_logged_user_id).try(:authenticate, set_password_params[:password_old])
    if !user
      flash[:errors] = "You have provided incorrect old password."
      redirect_to profile_users_path and return
    end

    #Change
    password_params = { password: set_password_params[:password], password_confirmation: set_password_params[:password_confirmation] }
    if user.update(password_params)
      flash[:success] = "Password has been successfuly changed."
    else
      flash[:errors] = stringify_errors(user)
    end

    redirect_to profile_users_path
  end

  private
    def user_create_params
      params.require(:user).permit(:login, :email, :password, :password_confirmation)
    end

    def user_login_params
      params.require(:user).permit(:login, :password)
    end

    def set_password_params
      params.require(:user).permit(:password_old, :password, :password_confirmation)
    end
end
