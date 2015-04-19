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

  private

    def user_create_params
      params.require(:user).permit(:login, :email, :password, :password_confirmation)
    end
end
