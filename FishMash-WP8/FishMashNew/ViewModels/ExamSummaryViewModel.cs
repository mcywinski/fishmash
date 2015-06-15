using FishMashNew.Common;
using FishMashNew.Views;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace FishMashNew.ViewModels
{
    class ExamSummaryViewModel : BaseViewModel
    {
        #region Binding
        #region ICommand
        public ICommand BackButtonClick
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

        #endregion

        public ExamSummaryViewModel(INavigationService iNavigation) 
        {
            this.navigationService = iNavigation;
        }
    }
}
