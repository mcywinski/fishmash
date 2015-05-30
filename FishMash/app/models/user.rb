class User < ActiveRecord::Base
  has_secure_password
  has_many :api_tokens
  has_many :student_class_memberships
  has_many :student_classes, through: :student_class_memberships
  has_many :assesments
  has_many :owned_exams, class_name: 'Exam', foreign_key: :owner_id
  has_many :owned_wordlists, class_name: 'WordList', foreign_key: :owner_id

  validates :password, presence: true, confirmation: true
  validates :email, presence: true, uniqueness: true
  validates :login, presence: true, uniqueness: true

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
