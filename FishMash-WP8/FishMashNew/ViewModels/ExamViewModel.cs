using FishMashApp.Models;
using FishMashNew.Common;
using FishMashNew.WebAPI;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace FishMashNew.ViewModels
{
    class ExamViewModel : BaseViewModel
    {
        #region Binding
        private string question;

        public string Question
        {
            get { return question; }
            set 
            { 
                question = value;
                OnPropertyChanged();
            }
        }
        private QuestionEntity currentQuestion;

        #endregion
        #region ICommand
        public ICommand NextButtonClick
        {
            get
            {
                return null ??
                    new RelayCommand(o =>
                    {
                        
                    });
            }
        }
        #endregion
        private int examID;
        public ExamViewModel(INavigationService iNavigation) 
        {
            examID = -1;
            this.navigationService = iNavigation;
        }

        #region Register and check exam ID
        public void RegisterExamID(int id)
        {
            examID = id;
            if (!string.IsNullOrWhiteSpace(Settings.Instance.Cache.GetToken()))
                StartExam();
        }

        public bool IsExamIdCorrect()
        {
            if (examID != -1)
                return true;
            else 
                return false;
        }
        #endregion

        #region WebService
        private async void StartExam()
        {
            //rozpoczęcie egzaminu + 
            //GetQuestion();
        }
        private async void GetQuestion()
        {
            currentQuestion = await WebService.GetQuestionToAnswer(examID, Settings.Instance.Cache.GetToken());
            Question = currentQuestion.meaning.ToString();
        }

        private async void AnswerQuestion(string answer)
        {

        }

        #endregion

    }
}
