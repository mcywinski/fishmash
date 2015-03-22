module WordListsHelper
  def output_language(language)
    language.name unless language.nil?
  end
end
