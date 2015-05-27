# NOTE: wordSrc array required to be rendered on the view

wordsLearned = new Array()
currentWord = null
mistakesCount = 0
wordsToLearn = wordsSrc
currentIndex = 0

getWord = () ->
  if wordsToLearn.length != 0
    currentIndex = getRandomInt(0, wordsToLearn.length - 1)
    currentWord = wordsToLearn[currentIndex]
    $("#learning-question").empty()
    $("#learning-text").val('')
    $("#learning-question").html(currentWord.meaning)
  else
    alert("Good job! You've learned the entire vocabulary for this test")
    window.location.href = "/exams"

checkWord = (word) ->
  text = $("#learning-text").val()
  if text.toLowerCase() == currentWord.phrase.toLowerCase()
    wordsLearned.push(currentWord)
    wordsToLearn.splice(currentIndex, 1)
    $("#learning-learned").empty()
    $("#learning-learned").html(String(wordsLearned.length))
  else
    mistakesCount++
    $("#learning-mistakes").empty()
    $("#learning-mistakes").html(String(mistakesCount))
    alert('Wrong! The correct answer is: ' + currentWord.phrase)

  $("#learning-left").empty()
  $("#learning-left").html(String(wordsToLearn.length))
  getWord()

$ ->
  getWord()
  $("#learning-left").empty()
  $("#learning-left").html(String(wordsToLearn.length))
  $("#learning-submit").click ->
    checkWord(currentWord)

  $("#learning-text").keypress (e) ->
    if e.keyCode == 13
      checkWord(currentWord)