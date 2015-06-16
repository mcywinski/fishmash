using FishMashApp.Models;
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

namespace FishMashNew.ViewModels
{
    class ExamSummaryViewModel : BaseViewModel
    {
        #region Binding
        public ObservableCollection<SummaryEntity> Summary
        {
            get;
            set;
        }
        
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

        private int ExamID;
        #endregion

        public ExamSummaryViewModel(INavigationService iNavigation) 
        {
            this.navigationService = iNavigation;
            ExamID = -1;
        }

        public void RegisterExamID(int ID)
        {
            ExamID = ID;
            GetSummary();
        }

        private async void GetSummary() 
        {
            List<SummaryEntity> temp = await WebService.GetExamSummary(ExamID, Settings.Instance.Cache.GetToken());

            foreach (SummaryEntity t in temp)
            {
                OnUIThread(() =>
                {
                    Summary.Add(t);
                });
            }
        }
    }
}
