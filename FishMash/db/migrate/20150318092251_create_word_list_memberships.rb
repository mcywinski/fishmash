class CreateWordListMemberships < ActiveRecord::Migration
  def change
    create_table :word_list_memberships do |t|
      t.references :word, index: true
      t.references :word_list, index: true

      t.timestamps null: false
    end
  end
end
