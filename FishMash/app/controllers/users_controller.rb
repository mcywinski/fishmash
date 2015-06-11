class UsersController < ApplicationController
  before_action :require_login, only: [:profile, :set_password]
  layout 'blank', only: [:login, :authenticate, :register]

  def register
    @user = User.new
  end

  def create
    user_type = user_create_params[:user_type].to_i
    unless user_type == 1 or user_type == 2
      throw 'Invalid user type provided'
    end
    @user = User.new user_create_params
    result = @user.save
    if !@user.save
      @errors = stringify_errors(@user)
      render 'register'
    else
      flash[:success] = 'Account has been successfuly creOated.'
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

    user = get_logged_user
    result = user.change_password(set_password_params[:password_old], set_password_params[:password], set_password_params[:password_confirmation])
    if result == UserCommon::PASS_CHANGE_SUCCESS
      flash[:success] = 'Password has been successfuly changed.'
    elsif result == UserCommon::PASS_CHANGE_NOT_EQUAL
      flash[:errors] = 'Provided passwords were not equal.'
    elsif result == UserCommon::PASS_CHANGE_FAILURE
      flash[:errors] = 'An error occured while changing the password.'
    elsif result == UserCommon::PASS_CHANGE_OLD_INVALID
      flash[:errors] = 'Old password was invalid.'
    end

    redirect_to profile_users_path
  end

  private
    def user_create_params
      params.require(:user).permit(:login, :email, :password, :password_confirmation, :user_type)
    end

    def user_login_params
      params.require(:user).permit(:login, :password)
    end

    def set_password_params
      params.require(:user).permit(:password_old, :password, :password_confirmation)
    end
end
