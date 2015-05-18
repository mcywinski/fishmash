class StudentClassMembership < ActiveRecord::Base
  belongs_to :user
  belongs_to :student_class
end
