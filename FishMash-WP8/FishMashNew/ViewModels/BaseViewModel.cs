using FishMashNew.Common;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace FishMashNew.ViewModels
{
    class BaseViewModel : INotifyPropertyChanged
    {
        protected INavigationService navigationService;
        public BaseViewModel()
        {

        }

        /// <summary>
        /// Set Visibility on WPF elements.
        /// </summary>
        /// <param name="value">If true it return Visible, else return Collapsed.</param>
        /// <returns></returns>
        protected string SetVisibility(bool value)
        {
            if (!value)
                return "Collapsed";
            else
                return "Visible";
        }

        /// <summary>
        /// Check Visibility on string.
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        protected bool CheckVisibility(string value)
        {
            if (value == "Visible")
                return true;
            else
                return false;
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
