class AddOwnerIdToWordLists < ActiveRecord::Migration
  def change
    add_column :word_lists, :owner_id, :integer
  end
end
