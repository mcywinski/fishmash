class CreateStudentClasses < ActiveRecord::Migration
  def change
    create_table :student_classes do |t|
      t.string :name
      t.text :description

      t.timestamps null: false
    end
  end
end
