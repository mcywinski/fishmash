class CreateAnswers < ActiveRecord::Migration
  def change
    create_table :answers do |t|
      t.references :word, index: true
      t.references :user, index: true
      t.references :assesment, index: true
      t.string :answer
      t.boolean :passed
      t.boolean :finished

      t.timestamps null: false
    end
  end
end
