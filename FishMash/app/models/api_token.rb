class ApiToken < ActiveRecord::Base
  belongs_to :user

  validates :token, uniqueness: true

  after_initialize :set_token

  private
    def set_token
      token = SecureRandom.hex
      puts token
      found = true

      while found do
        existing_token = ApiToken.find_by(token: token)
        if existing_token.nil?
          found = false
        else
          found = true
          token = SecureRandom.hex
        end
      end

      self.token = token
    end
end
