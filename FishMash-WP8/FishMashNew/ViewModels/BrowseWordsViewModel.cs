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
using FishMashApp.Models;
using FishMashApp.Models.Exams;

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

        public ObservableCollection<ExamEntity> ListOfExams
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

    #endregion

        public BrowseWordsViewModel(INavigationService navigationService)
        {
            this.navigationService = navigationService;
            ListOfList = new ObservableCollection<ListOfLists>();
            ListOfExams = new ObservableCollection<ExamEntity>();
            ProgressBarVisibility = SetVisibility(true);
            FillList(); //only for temp
            
        }

        public async void FillList()
        {
            List<ListOfLists> learnLists = await WebService.GetListOfListAsync();
            List<ExamEntity> exams = await WebService.GetAllExams(Settings.Instance.Cache.GetToken());
            OnUIThread(() =>
            {
                foreach (ListOfLists t in learnLists)
                {
                    ListOfList.Add(t);
                }
                foreach (ExamEntity t in exams)
                {
                    ListOfExams.Add(t);
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
