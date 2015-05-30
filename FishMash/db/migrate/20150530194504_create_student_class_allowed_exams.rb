class CreateStudentClassAllowedExams < ActiveRecord::Migration
  def change
    create_table :student_class_allowed_exams do |t|
      t.references :student_class, index: true
      t.references :exam, index: true
      t.timestamps null: false
    end
  end
end
