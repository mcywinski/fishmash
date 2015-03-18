using FishMashApp.ViewModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FishMashApp.Models
{
    class Word : BaseViewModel
    {
        #region Binding

        private int id;

        public int Id
        {
            get { return id; }
            set 
            { 
                id = value;
                OnPropertyChanged();
            }
        }

        private string phrase;

        public string Phrase
        {
            get { return phrase; }
            set 
            { 
                phrase = value;
                OnPropertyChanged();
            }
        }

        private string meaning;

        public string Meaning
        {
            get { return meaning; }
            set 
            { 
                meaning = value;
                OnPropertyChanged();
            }
        }
        private int phraseLanguageId;

        public int PhraseLanguageId
        {
            get { return phraseLanguageId; }
            set 
            { 
                phraseLanguageId = value;
                OnPropertyChanged();
            }
        }
        private int meaningLanguageId;

        public int MeaningLanguageId
        {
            get { return meaningLanguageId; }
            set 
            { 
                meaningLanguageId = value;
                OnPropertyChanged();
            }
        }
        
        
        
        #endregion


    }
}
