using FishMashNew.Common;
using FishMashNew.Models;
using FishMashNew.Views;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using FishMashNew.WebAPI;

namespace FishMashNew.ViewModels
{
    class BrowseWordsViewModel : BaseViewModel
    {
    #region Properties
        #region Binding
        public ObservableCollection<ListOfLists> ListOfList
        {
            get;
            set;
        }

        private string progressBarVisibility;

	    public string ProgressBarVisibility
	    {
		    get { return progressBarVisibility;}
		    set 
            { 
                progressBarVisibility = value;
                OnPropertyChanged();
            }
	    }
	
        #endregion

        //public ICommand Click
        //{
        //    get
        //    {
        //        return null ??
        //            new RelayCommand(o =>
        //            {
        //                this.navigationService.Naviagte(typeof(WordView));
        //            });
        //    }
        //}
    #endregion

        public BrowseWordsViewModel(INavigationService navigationService)
        {
            this.navigationService = navigationService;
            ListOfList = new ObservableCollection<ListOfLists>();
            ProgressBarVisibility = SetVisibility(true);
            FillList(); //only for temp
            
        }

        public async void FillList()
        {
            List<ListOfLists> x = await WebService.GetListOfListAsync();
            OnUIThread(() =>
            {
                foreach (ListOfLists t in x)
                {
                    ListOfList.Add(t);
                }
            });
            ProgressBarVisibility = SetVisibility(false);
            
        }

        public void GoToLoginPage()
        {
            this.navigationService.Navigate(typeof(FicheView));
        }
    }
}
