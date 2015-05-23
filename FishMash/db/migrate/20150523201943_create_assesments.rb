class CreateAssesments < ActiveRecord::Migration
  def change
    create_table :assesments do |t|
      t.references :exam, index: true
      t.references :user, index: true
      t.datetime :time_started

      t.timestamps null: false
    end
  end
end
