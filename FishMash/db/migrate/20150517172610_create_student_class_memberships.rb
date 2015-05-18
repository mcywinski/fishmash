class CreateStudentClassMemberships < ActiveRecord::Migration
  def change
    create_table :student_class_memberships do |t|
      t.references :user, index: true
      t.references :student_class, index: true

      t.timestamps null: false
    end
  end
end
