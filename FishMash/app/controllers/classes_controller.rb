class ClassesController < ApplicationController
  before_action :require_login

  # Displays all classes in the system
  # TODO: Display only classes belonging to a user
  def index
    @classes = StudentClass.all
  end

  def new

  end

  def create
    stud_class = StudentClass.new(class_create_params)
    if stud_class.save
      redirect_to classes_path
    else
      flash[:errors] = stringify_errors(stud_class)
      render 'new'
    end
  end

  private
    def class_create_params
      params.require(:student_class).permit(:description, :name)
    end
end
