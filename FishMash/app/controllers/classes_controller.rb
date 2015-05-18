class ClassesController < ApplicationController
  before_action :require_login

  # Displays all classes in the system
  # TODO: Display only classes belonging to a user
  def index
    @classes = StudentClass.all
  end

  def new

  end

  def show
    @student_class = StudentClass.find params[:id]
    
    if @student_class.nil?
      flash[:errors] = "Class with such ID does not exist."
    else
      @students = Array.new
      class_members = StudentClassMembership.where(student_class: @student_class.id)
      
      class_members.each do |student| 
          @students.push(User.find student.user)
      end
    end
  end

  def create
    stud_class = StudentClass.new(class_params)
    if stud_class.save
      redirect_to classes_path
    else
      flash[:errors] = stringify_errors(stud_class)
      redirect_to new_class_path and return
    end
  end

  def edit
    @student_class = StudentClass.find(params[:id])
  end

  def update
    @student_class = StudentClass.find(params[:id])
    if !@student_class.update(class_params)
      flash[:errors] = stringify_errors(@student_class)
      redirect_to edit_class_path(@student_class) and return
    end

    render 'edit'
  end

  def add_member
    @student_class = StudentClass.find params[:class_id]

    login_to_find = params[:member_new][:login]
    disallow_find = false

    if login_to_find.blank?
      disallow_find = true
      flash[:errors] = "You have to type in the login first."
    end

    student_to_add = User.find_by(login: params[:member_new][:login])
    if student_to_add.nil?
      disallow_find = true
      flash[:errors] = "User with such login does not exist."
    end

    if disallow_find
      redirect_to edit_class_path(@student_class)
    else
      @student_class.users.push student_to_add if !@student_class.users.include?(student_to_add)
      if !@student_class.save
        flash[:errors] = stringify_errors(@student_class)
        redirect_to edit_class_path(@student_class)
      else
        render 'edit'
      end
    end
  end

  def remove_member
    @student_class = StudentClass.find params[:class_id]
    @student_to_remove = User.find params[:user_id]

    if @student_to_remove.nil? or @student_class.nil?
      flash[:errors] = "User or class with such ID does not exist."
    else
      student_membership = StudentClassMembership.find_by(user_id: @student_to_remove.id, student_class_id: @student_class.id)
      student_membership.destroy
      redirect_to class_path(@student_class)
    end
  end

  private
    def class_params
      params.require(:student_class).permit(:description, :name)
    end
end
