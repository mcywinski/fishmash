using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace FishMashApp.Models
{
    public class SummaryEntity
    {
        public int id { get; set; }
        public string answer { get; set; }

        public string Answer
        {
            get { return answer; }
            set
            {
                answer = value;
                OnPropertyChanged();
            }
        }

        public bool finished { get; set; }
        public bool passed { get; set; }

        public bool Passed
        {
            get { return passed; }
            set
            {
                passed = value;
                OnPropertyChanged();
            }
        }

        public string meaning { get; set; }

        public string Meaning
        {
            get { return meaning; }
            set
            {
                meaning = value;
                OnPropertyChanged();
            }
        }
        public bool exam_finished { get; set; }
        public string phrase { get; set; }

        public string Phrase
        {
            get { return phrase; }
            set
            {
                phrase = value;
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
