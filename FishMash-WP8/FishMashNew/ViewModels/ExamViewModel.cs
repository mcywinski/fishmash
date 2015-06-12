using FishMashApp.Models;
using FishMashNew.Common;
using FishMashNew.Models.StartExamModels;
using FishMashNew.WebAPI;
using System;
using System.Collections.Generic;
using System.Diagnostics;
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

        private string answer;

        public string Answer
        {
            get { return answer; }
            set 
            { 
                answer = value;
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
                        
                        //if Answer != String.Empty
                        // AnswerQuestion(Answer);
                        GetQuestion();
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
            StartedRoot startedRoot = await WebService.StartExam(examID, Settings.Instance.Cache.GetToken());
            Debug.WriteLine(startedRoot.message.ToString());
            // if(startedRoot == ok) 
            // GetQuestion();
        }
        private async void GetQuestion()
        {
            //sprawdzic czy nie nadeszło już ostatnie pytanie i czy można zapytać o następne!
            currentQuestion = await WebService.GetQuestionToAnswer(examID, Settings.Instance.Cache.GetToken());
            Question = currentQuestion.meaning.ToString(); // w to pole ma trafić pytanie
        }

        private async void AnswerQuestion(string answer)
        {
            //wysłać odpowiedz 
            //sprawdzić czy została zapisana
           
        }

        #endregion

    }
}
