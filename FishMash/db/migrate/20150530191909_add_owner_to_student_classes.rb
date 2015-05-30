class AddOwnerToStudentClasses < ActiveRecord::Migration
  def change
    add_column :student_classes, :owner_id, :integer
  end
end
