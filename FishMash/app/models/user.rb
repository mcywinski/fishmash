class User < ActiveRecord::Base
  has_secure_password
  has_many :api_tokens
  has_many :student_class_memberships
  has_many :student_classes, through: :student_class_memberships
  has_many :assesments
  has_many :owned_exams, class_name: 'Exam', foreign_key: :owner_id
  has_many :owned_wordlists, class_name: 'WordList', foreign_key: :owner_id
  has_many :owned_student_classes, class_name: 'StudentClass', foreign_key: :owner_id

  validates :password, presence: true, confirmation: true
  validates :email, presence: true, uniqueness: true
  validates :login, presence: true, uniqueness: true

  def change_password(pass_old, pass_new, pass_new_confirmation)
    return UserCommon::PASS_CHANGE_NOT_EQUAL if pass_new != pass_new_confirmation

    #Old password check
    user = User.find(self.id).try(:authenticate, pass_old)
    if !user
      return UserCommon::PASS_CHANGE_OLD_INVALID
    end

    #Change
    password_params = { password: pass_new, password_confirmation: pass_new_confirmation }
    if user.update(password_params)
      return UserCommon::PASS_CHANGE_SUCCESS
    else
      return UserCommon::PASS_CHANGE_FAILURE
    end
  end

  def get_available_exams
    exams = Array.new
    self.student_classes.each do |student_class|
      exams = exams | student_class.exams
    end
    return exams
  end

  def get_available_wordlists
    wordlists = Array.new
    self.get_available_exams.each do |exam|
      if exam.date_practice_start <= Date.today and exam.date_practice_finish >= Date.today
        wordlists = wordlists | exam.word_lists
      end
    end
    return wordlists
  end

  def is_student?
    return self.user_type == 1
  end

  def is_teacher?
    return self.user_type == 2
  end

  def to_dto
    user_dto = Hash.new
    user_dto[:login] = self.login
    user_dto[:email] = self.email
    user_dto[:created_at] = self.created_at
    user_dto[:updated_at] = self.updated_at
    user_dto[:user_type] = self.user_type

    return user_dto
  end
end
