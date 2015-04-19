class UsersController < ApplicationController
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
      redirect_to root_path
    end
  end

  def logout
    session.destroy
    flash[:success] = 'You are successfuly logged out.'
    redirect_to root_path
  end

  private

    def user_create_params
      params.require(:user).permit(:login, :email, :password, :password_confirmation)
    end

    def user_login_params
      params.require(:user).permit(:login, :password)
    end
end
