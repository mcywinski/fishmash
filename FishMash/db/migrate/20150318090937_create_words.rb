class CreateWords < ActiveRecord::Migration
  def change
    create_table :words do |t|
      t.string :phrase
      t.string :meaning
      t.references :phrase_language, index: true
      t.references :meaning_language, index: true

      t.timestamps null: false
    end
  end
end
