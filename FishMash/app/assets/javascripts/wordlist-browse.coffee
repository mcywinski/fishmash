# Identifiers

wordviewMain = '#wordlist-wordview-main'
wordviewForeign = '#wordlist-wordview-foreign'

keyCodeUp = 38
keyCodeDown = 40
keyCodeLeft = 37
keyCodeRight = 39

# Variables

words = []
activeWordIndex = 0

activeMain = true

# Logic

switchToMain = () ->
  $(wordviewMain).show()
  $(wordviewForeign).hide()
  activeMain = true

switchToForeign = () ->
  $(wordviewMain).hide()
  $(wordviewForeign).show()
  activeMain = false

switchLanguage = () ->
  if activeMain
    switchToForeign()
  else
    switchToMain()

switchWordNext = () ->
  if activeWordIndex == (words.length - 1)
    activeWordIndex = 0
  else
    activeWordIndex++

  populateWordView(words[activeWordIndex])
  switchToMain()

switchWordPrevious = () ->
  if activeWordIndex == 0
    activeWordIndex = (words.length - 1)
  else
    activeWordIndex--
  populateWordView(words[activeWordIndex])
  switchToMain()

generateWordspan = (text) ->
  return '<h2>' + text + '</h2>'

setupKeyboard = () ->
  $(document).on 'keydown', (event) ->
    if event.which == keyCodeLeft
      switchWordPrevious()
    else if event.which == keyCodeRight
      switchWordNext()
    else if event.which == keyCodeDown or event.which == keyCodeUp
      switchLanguage()

initBrowsing = () ->
  if words.length > 0
    populateWordView(words[0])
    setupKeyboard()
  else
    alert('There are no words available in this list.')

populateWordView = (word) ->
  $(wordviewMain).html(generateWordspan(word.meaning))
  $(wordviewForeign).html(generateWordspan(word.phrase))
  $(wordviewForeign).hide()

$ ->
  $.ajax
    type: 'GET'
    url: '/api/lists/' + listId
    success: (data, status) ->
      words = data.words
      initBrowsing()
    error: ->
      alert('An error has occured while getting the list words! Try again, please.')
