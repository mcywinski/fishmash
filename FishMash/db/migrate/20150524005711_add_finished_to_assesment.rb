class AddFinishedToAssesment < ActiveRecord::Migration
  def change
    add_column :assesments, :finished, :boolean
  end
end
