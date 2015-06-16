using FishMashApp.Models;
using FishMashNew.Common;
using FishMashNew.Models.StartExamModels;
using FishMashNew.Views;
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
        private string progressBarVisibility;

        public string ProgressBarVisibility
        {
            get { return progressBarVisibility; }
            set 
            { 
                progressBarVisibility = value;
                OnPropertyChanged();
            }
        }
        

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
                        GetNextQuestion();
                    });
            }
        }
        #endregion
        private int examID;

        private int currentQuestionID { get; set; }
        public ExamViewModel(INavigationService iNavigation) 
        {
            examID = -1;
            this.navigationService = iNavigation;
            ProgressBarVisibility = SetVisibility(false);
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
            ProgressBarVisibility = SetVisibility(true);
            StartedRoot startedRoot = await WebService.StartExam(examID, Settings.Instance.Cache.GetToken());
            ProgressBarVisibility = SetVisibility(false);
            if(startedRoot.message != "END")
            {
                GetQuestion();
            }
            else
            {
                // nawiguj to strony, ze egzamin zakonczony
                object t = this.examID.ToString();
                this.navigationService.Navigate(typeof(ExamSummaryView), t);
            }
        }
        private async void GetQuestion()
        {
            ProgressBarVisibility = SetVisibility(true);
            currentQuestion = await WebService.GetQuestionToAnswer(examID, Settings.Instance.Cache.GetToken());
            ProgressBarVisibility = SetVisibility(false);
            currentQuestionID = currentQuestion.id;
            Question = currentQuestion.meaning.ToString(); // w to pole ma trafić pytanie
        }

        private async void AnswerQuestion(string answer)
        {
            ProgressBarVisibility = SetVisibility(true);
            AnswerEntity answerResult = await WebService.AnswerQuestion(currentQuestionID, answer, examID, Settings.Instance.Cache.GetToken());
            ProgressBarVisibility = SetVisibility(false);
            if(answerResult.saved == true)
            {
                Answer = string.Empty;
            }
        }

        private async Task GetNextQuestion()
        {
            ProgressBarVisibility = SetVisibility(true);
            AnswerEntity answerResult = await WebService.AnswerQuestion(currentQuestionID, answer, examID, Settings.Instance.Cache.GetToken());
            ProgressBarVisibility = SetVisibility(false);
            if (answerResult.saved == true)
            {
                Answer = string.Empty;
                Question = string.Empty;
                ProgressBarVisibility = SetVisibility(true);
                currentQuestion = await WebService.GetQuestionToAnswer(examID, Settings.Instance.Cache.GetToken());
                ProgressBarVisibility = SetVisibility(false);
                if(currentQuestion.exam_finished)
                {
                    object t = this.examID.ToString();
                    this.navigationService.Navigate(typeof(ExamSummaryView), t);
                }
                else
                {
                    //ustaw wartość publicznej zmiennej z id pobranego pytania dla przekazania do odpowiedzi
                    currentQuestionID = currentQuestion.id;
                    //ustaw znaczenie słowa
                    Question = currentQuestion.meaning.ToString();
                }
            }
        }
        #endregion

    }
}
