using FishMash.WebAPI;
using FishMashNew.Common;
using FishMashNew.Views;
using FishMashNew.WebAPI;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using Windows.UI.Input;

namespace FishMashNew.ViewModels
{
    class WordViewModel : BaseViewModel
    {
        #region Binding
        
        public ObservableCollection<ReadWord.Word> ListOfWords
        {
            get;
            set;
        }

        public ICommand ButtonBack
        {
            get
            {
                return null ??
                    new RelayCommand(o =>
                    {
                        this.navigationService.Navigate(typeof(BrowseWordsView));
                    });
            }
        }
        #endregion

        public WordViewModel(INavigationService navigationService)
        {
            this.navigationService = navigationService;
            ListOfWords = new ObservableCollection<ReadWord.Word>();
            //FillList(); //only for temp
        }

        public async void FillList(int i)
        {
            List<ReadWord.Word> x = await WebService.GetWordsOfListAsync(i);
            OnUIThread(() =>
            {
                foreach (ReadWord.Word t in x)
                {
                    ListOfWords.Add(t);
                }
            });
        }
    }
}
