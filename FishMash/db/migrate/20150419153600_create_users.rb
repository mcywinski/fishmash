class CreateUsers < ActiveRecord::Migration
  def change
    create_table :users do |t|
      t.string :login
      t.string :email
      t.string :password_digest
      t.integer :user_type
      t.boolean :active
      t.string :activation_token

      t.timestamps null: false
    end
  end
end
