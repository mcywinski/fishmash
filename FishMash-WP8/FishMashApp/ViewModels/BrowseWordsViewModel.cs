using FishMashApp.Common;
using FishMashApp.Models;
using FishMashApp.Views;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace FishMashApp.ViewModels
{
    class BrowseWordsViewModel : BaseViewModel
    {
    #region Properties
        #region Binding
        public ObservableCollection<BaseViewModel> ListOfList
        {
            get;
            set;
        }
        
        #endregion

        public ICommand Click
        {
            get
            {
                return null ??
                    new RelayCommand(o =>
                    {
                        this.navigationService.Naviagte(typeof(WordView));
                    });
            }
        }
    #endregion

        public BrowseWordsViewModel(INavigationService navigationService)
        {
            this.navigationService = navigationService;
            ListOfList = new ObservableCollection<BaseViewModel>();
            FillList();
        }

        public async void FillList()
        {            
            List<ListOfLists> temp = await WebAPI.WebService.GetListOfListAsync();
            foreach (ListOfLists t in temp)
            {
                ListOfList.Add(t);
            }
        }

        public void goToNextPage()
        {
            this.navigationService.Naviagte(typeof(WordView));
        }
    }
}
