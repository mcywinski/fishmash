class AddTimeLimitToExams < ActiveRecord::Migration
  def change
    add_column :exams, :time_limit, :integer
  end
end
