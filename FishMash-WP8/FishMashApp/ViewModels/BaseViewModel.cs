using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace FishMashApp.ViewModels
{
    class BaseViewModel : INotifyPropertyChanged
    {
        public BaseViewModel()
        {

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
        protected virtual void OnPropertyChanged(string propertyName = null)
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
