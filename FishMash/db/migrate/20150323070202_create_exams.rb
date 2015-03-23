class CreateExams < ActiveRecord::Migration
  def change
    create_table :exams do |t|
      t.string :name
      t.text :description
      t.date :date_practice_start
      t.date :date_practice_finish
      t.date :date_exam_start
      t.date :date_exam_finish
      t.integer :word_count

      t.timestamps null: false
    end
  end
end
