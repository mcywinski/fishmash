class StudentClass < ActiveRecord::Base
  has_many :student_class_memberships
  has_many :users, through: :student_class_memberships
  belongs_to :owner, class_name: 'User', foreign_key: :owner_id

  validates :name, presence: true
end
