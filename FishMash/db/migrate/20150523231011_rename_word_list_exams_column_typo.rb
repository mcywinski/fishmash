class RenameWordListExamsColumnTypo < ActiveRecord::Migration
  def change
    remove_column :word_list_exams, :wordlist_id, :integer
    add_column :word_list_exams, :word_list_id, :integer
  end
end
