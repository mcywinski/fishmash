# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)

languages = Language.create([{ name: 'Polski' }, { name: 'English'}, { name: 'Deutsch' }])
word_lists = WordList.create([{ name: 'General English revision', description: 'Powtórka testowa', main_language_id: 1, foreign_language_id: 2 }, { name: 'Animals', description: 'Basic animal-related vocebulary', main_language_id: 1, foreign_language_id: 2 }, { name: 'Familie', description: 'Podstawowy zakres słownictwa z zakresu rodziny z języka niemieckiego.', main_language_id: 1, foreign_language_id: 3 }])
