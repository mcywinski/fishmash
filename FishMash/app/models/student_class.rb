class StudentClass < ActiveRecord::Base
  has_many :student_class_memberships
  has_many :users, through: :student_class_memberships

  validates :name, presence: true
end
