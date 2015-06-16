using FishMashNew.ViewModels;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace FishMashApp.Models.Exams
{
    public class ExamEntity : INotifyPropertyChanged
    {
        public int id { get; set; }

        public int Id
        {
            get { return id; }
            set
            {
                id = value;
                OnPropertyChanged();
            }
        }
        public string name { get; set; }

        public string Name
        {
            get { return name; }
            set
            {
                name = value;
                OnPropertyChanged();
            }
        }

        public string date_exam_start { get; set; }
        public string date_exam_finish { get; set; }

        public string DateExamFinish
        {
            get { return "to " + date_exam_finish; }
            set
            {
                date_exam_finish = value;
                OnPropertyChanged();
            }
        }
        public string date_practice_start { get; set; }
        public string date_practice_finish { get; set; }
        public int word_count { get; set; }
        public bool is_finished { get; set; }

        public bool IsFinished
        {
            get { return is_finished; }
            set
            {
                is_finished = value;
                OnPropertyChanged();
            }
        }

        private string buttonLabel;

        public string ButtonLabel
        {
            get 
            {
                if (IsFinished)
                    return "stats";
                else
                    return "exam";
            }
            set 
            { 
                buttonLabel = value;
                OnPropertyChanged();
            }
        }
        

        #region PropertyChanged
        protected delegate void OnUIThreadDelegate();
        protected async void OnUIThread(DispatchedHandler onUIThreadDelegate)
        {
            if (onUIThreadDelegate != null)
            {
                var dispatcher = CoreApplication.MainView.CoreWindow.Dispatcher;
                if (dispatcher.HasThreadAccess)
                {
                    onUIThreadDelegate();
                }
                else
                {
                    await dispatcher.RunAsync(CoreDispatcherPriority.Normal, onUIThreadDelegate);
                }
            }
        }
        public event PropertyChangedEventHandler PropertyChanged;
        protected virtual void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {

            OnUIThread(() =>
            {
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
                }
            });
        }
        #endregion
    }
}
