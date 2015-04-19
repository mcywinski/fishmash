class User < ActiveRecord::Base
  has_secure_password

  validates :password, presence: true, confirmation: true
  validates :email, presence: true, uniqueness: true
  validates :login, presence: true, uniqueness: true
end
