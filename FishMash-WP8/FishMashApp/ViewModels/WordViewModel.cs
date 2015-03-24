using FishMash.WebAPI;
using FishMashApp.Common;
using FishMashApp.Views;
using FishMashApp.WebAPI;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace FishMashApp.ViewModels
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
                        this.navigationService.Naviagte(typeof(BrowseWordsView));
                    });
            }
        }
        #endregion

        public WordViewModel(INavigationService navigationService)
        {
            this.navigationService = navigationService;
            ListOfWords = new ObservableCollection<ReadWord.Word>();
            FillList(); //only for temp
        }

        public async void FillList()
        {
            List<ReadWord.Word> x = await WebService.GetWordsOfListAsync(1);
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
