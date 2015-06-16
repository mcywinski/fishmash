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
            if(startedRoot.message != "END")
            {
                GetQuestion();
            }
            else
            {
                // nawiguj to strony, ze egzamin zakonczony
            }
        }
        private async void GetQuestion()
        {
            currentQuestion = await WebService.GetQuestionToAnswer(examID, Settings.Instance.Cache.GetToken());
            currentQuestionID = currentQuestion.id;
            Question = currentQuestion.meaning.ToString(); // w to pole ma trafić pytanie
        }

        private async void AnswerQuestion(string answer)
        {
            AnswerEntity answerResult = await WebService.AnswerQuestion(currentQuestionID, answer, examID, Settings.Instance.Cache.GetToken());
            if(answerResult.saved == true)
            {
                Answer = string.Empty;
            }
        }

        private async Task GetNextQuestion()
        {
            AnswerEntity answerResult = await WebService.AnswerQuestion(currentQuestionID, answer, examID, Settings.Instance.Cache.GetToken());
            if (answerResult.saved == true)
            {
                Answer = string.Empty;
                currentQuestion = await WebService.GetQuestionToAnswer(examID, Settings.Instance.Cache.GetToken());
                if(currentQuestion.exam_finished)
                {
                    // nawiguj do strony z wynikami egzaminu
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
