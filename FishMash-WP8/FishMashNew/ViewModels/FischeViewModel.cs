using FishMashNew.Common;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using FishMashNew.WebAPI;
using FishMash.WebAPI;

namespace FishMashNew.ViewModels
{
    class FischeViewModel : BaseViewModel
    {
        private List<ReadWord.Word> listOfWords;
        private int currentWordId;
        private bool showTranslatedWord;
        private bool emptyList;

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
            if (!emptyList)
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
        }
        private void previousWord()
        {
            if (!emptyList)
            {
                if (currentWordId == 0)
                    currentWordId = listOfWords.Count - 1;
                else
                    currentWordId--;

                reloadWord();
            }
        }

        public void WordTranslateGridTapped()
        {
            if (!emptyList)
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
                emptyList = true;
            }
            else
            {
                currentWordId = 0;
                showTranslatedWord = true;
                emptyList = false;
                WordTranslate = listOfWords[currentWordId].Meaning.ToString();
            }
        }

        private void reloadWord()
        {            
            WordTranslate = listOfWords[currentWordId].Meaning.ToString();
            showTranslatedWord = false;
        }

        #endregion
    }
}
