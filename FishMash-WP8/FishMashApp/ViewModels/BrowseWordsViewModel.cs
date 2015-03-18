using FishMashApp.Models;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

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

    #endregion

        public BrowseWordsViewModel()
        {
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
    }
}
