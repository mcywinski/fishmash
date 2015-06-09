class AddOwnerToExams < ActiveRecord::Migration
  def change
    add_column :exams, :owner_id, :integer
  end
end
