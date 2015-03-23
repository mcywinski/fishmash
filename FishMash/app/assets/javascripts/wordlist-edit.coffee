# Identifiers

wordForeignInput = '#wordlist-phrase'
wordMeaningInput = '#wordlist-meaning'
wordAddButton = '#wordlist-edit-add'
wordListContainer = '#wordlist-list'

# Logic

renderWord = (word) ->
  $(wordListContainer).append('<span>' + word.phrase + '</span> - <span>' + word.meaning + '</span><br>')

getWords = () ->
  $.ajax
    type: 'GET'
    url: '/api/lists/' + wordListId
    success: (wordList) ->
      $(wordListContainer).empty()
      wordList.words.reverse() # Reversing in order to display chronogically newest words on top of the list.
      renderWord word for word in wordList.words
    error: ->
      alert('An error has occured while getting the word list. Try again, please')

addWordToList = (word) ->
  $.ajax
    type: 'POST'
    url: '/api/lists/' + wordListId + '/add'
    data:
      word_id: word.id
    success: ->
      getWords()
    error: ->
      alert('An error has occured while adding the word to the list. Try again, please.')

$ ->
  getWords()

  $(wordAddButton).click ->
    phrase = $(wordForeignInput).val()
    meaning = $(wordMeaningInput).val()

    if phrase.trim() == '' or meaning.trim() == ''
      alert('You must fill both fields: The phrase and it\' meaning')
      return

    $.ajax
      type: 'POST'
      url: '/api/words'
      data:
        word:
          phrase: phrase
          meaning: meaning
          phrase_language_id: phraseLanguageId
          meaning_language_id: meaningLanguageId
      success: (word) ->
        addWordToList(word)
        $(wordForeignInput).val('')
        $(wordMeaningInput).val('')
      error: ->
        alert('An error has occured while creating the word. Try again, please.')
