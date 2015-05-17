class StudentClass < ActiveRecord::Base
  has_many :student_class_memberships
  has_many :users, trough: :student_class_memberships
end
