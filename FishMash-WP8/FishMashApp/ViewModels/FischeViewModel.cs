using FishMashApp.Common;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using FishMashApp.WebAPI;
using FishMash.WebAPI;

namespace FishMashApp.ViewModels
{
    class FischeViewModel : BaseViewModel
    {
        private List<ReadWord.Word> listOfWords;
        private int currentWordId;
        private bool showTranslatedWord;

        #region Binding
        private string wordTranslate;

        public string WordTranslate
        {
            get { return wordTranslate; }
            set 
            { 
                wordTranslate = value;
                OnPropertyChanged();
            }
        }

        

        #region Icommand
        public ICommand NextButtonClick
        {
            get
            {
                return null ??
                    new RelayCommand(o =>
                    {
                        nextWord();
                    });
            }
        }
        public ICommand PreviousButtonClick
        {
            get
            {
                return null ??
                    new RelayCommand(o =>
                    {
                        previousWord();
                    });
            }
        }
        #endregion

        #endregion
        public FischeViewModel(INavigationService iNavigation)
        {
            this.navigationService = iNavigation;
        }

        #region ButtonsEvent
        private void nextWord()
        {
            if (currentWordId >= listOfWords.Count - 1)
            {
                currentWordId = 0;
            }
            else
            {
                currentWordId++;
            }
            reloadWord();
        }
        private void previousWord()
        {
            if (currentWordId == 0)
                currentWordId = listOfWords.Count - 1;
            else
                currentWordId--;

            reloadWord();
        }

        public void WordTranslateGridTapped()
        {
            if (showTranslatedWord)
            {
                showTranslatedWord = false;
                WordTranslate = listOfWords[currentWordId].Phrase.ToString();
            }
            else
            {
                showTranslatedWord = true;
                WordTranslate = listOfWords[currentWordId].Meaning.ToString();
            }
        }
        #endregion

        #region WebService
        public async void FillList(int id)
        {
            listOfWords = await WebService.GetWordsOfListAsync(id);
            ShowFirstWord();
        }
        #endregion

        #region BrowseWords
        private void ShowFirstWord()
        {
            if (listOfWords.Count == 0)
            {
                WordTranslate = "Brak pobranych słówek"; 
            }
            else
            {
                currentWordId = 0;
                showTranslatedWord = false;
                WordTranslate = listOfWords[currentWordId].Meaning.ToString();
            }
        }

        private void reloadWord()
        {            
            WordTranslate = listOfWords[currentWordId].Meaning.ToString();
        }

        #endregion
    }
}
