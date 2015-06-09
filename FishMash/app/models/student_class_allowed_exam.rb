class StudentClassAllowedExam < ActiveRecord::Base
  belongs_to :student_class
  belongs_to :exam
end
