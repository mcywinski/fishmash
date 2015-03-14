class CreateWordLists < ActiveRecord::Migration
  def change
    create_table :word_lists do |t|
      t.string :name
      t.text :description
      t.references :main_language, index: true
      t.references :foreign_language, index: true

      t.timestamps null: false
    end
  end
end
