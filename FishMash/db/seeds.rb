language_list = [
       ['Polski', 'Język polski'],
       ['English', 'English language'],
       ['Deutsch', 'Deutsch sprache'],
       ['Francais', 'Francais']
]

language_list.each do |name, description|
       Language.create(name: name, description: description)
end

word_lists_list = [
       ['General English revision', 'Powtórka testowa - angielski', 1, 2],
       ['Wiederholung der deutschen', 'Powtórka testowa - niemiecki', 1, 3],
       ['Répétition des Français', 'Powtórka testowa - francuski', 1, 4]
]

word_lists_list.each do |name, description, main_language_id, foreign_language_id|
       WordList.create(name: name, description: description, main_language_id: main_language_id, foreign_language_id: foreign_language_id)
end

word_list = [
       ['Cat', 'Kot', 2, 1],
       ['Dog', 'Pies', 2, 1],
       ['Rhino', 'Nosorożec', 2, 1],
       ['Giraffe', 'Żyrafa', 2, 1],
       ['Katze', 'Kot', 3, 1],
       ['Hund', 'Pies', 3, 1],
       ['Schmetterling', 'Motyl', 3, 1],
       ['Pferd', 'Koń', 3, 1],
       ['Chat', 'Kot', 4, 1],
       ['Chien', 'Pies', 4, 1],
       ['Crotale', 'Grzechotnik', 4, 1],
       ['Regulus', 'Mysikrólik', 4, 1]
]

word_list.each do |phrase, meaning, phrase_language_id, meaning_language_id|
       Word.create(phrase: phrase, meaning: meaning, phrase_language_id: phrase_language_id, meaning_language_id: meaning_language_id)
end

wordlist_memberships_list = [
       [1, 1],
       [2, 1],
       [3, 1],
       [4, 1],
       [5, 2],
       [6, 2],
       [7, 2],
       [8, 2],
       [9, 3],
       [10, 3],
       [11, 3],
       [12, 3]
]

wordlist_memberships_list.each do |word_id, word_list_id|
       WordListMembership.create(word_id: word_id, word_list_id: word_list_id)
end

exams_list = [
       ['Test 1 - Animals', 'Test about animals', '2016-01-01 00:00:00', '2016-01-08 00:00:00', '2016-01-09 00:00:00', '2016-01-16 00:00:00', 4],
       ['Test 1 - die Tiere', 'Der Test an Tieren', '2016-01-01 00:00:00', '2016-01-08 00:00:00', '2016-01-09 00:00:00', '2016-01-16 00:00:00', 4],
       ['Test 1 - Animaux', 'Test sur les animaux', '2016-01-01 00:00:00', '2016-01-08 00:00:00', '2016-01-09 00:00:00', '2016-01-16 00:00:00', 4]
]

exams_list.each do |name, description, date_practice_start, date_practice_finish, date_exam_start, date_exam_finish, word_count|
       Exam.create(name: name, description: description, date_practice_start: date_practice_start, date_practice_finish: date_practice_finish, date_exam_start: date_exam_start, date_exam_finish: date_exam_finish, word_count: word_count)
end

wordlist_exams_list = [
       [1,1],
       [2,2],
       [3,3]
]

wordlist_exams_list.each do |wordlist_id, exam_id|
       WordListExam.create(wordlist_id: wordlist_id, exam_id: exam_id)
end
