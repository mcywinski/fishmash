class CreateWordlistExams < ActiveRecord::Migration
  def change
    create_table :word_list_exams do |t|
      t.references :wordlist
      t.references :exam

      t.timestamps
    end
  end
end
