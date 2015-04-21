using FishMashApp.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace FishMashApp.ViewModels.LoginAndRegistation
{
    class LoginViewModel : BaseViewModel
    {
        #region Binding
        #region Properties
        private string user;
        public string User
        {
            get { return user; }
            set 
            { 
                user = value;
                OnPropertyChanged();
            }
        }


        private string password;
        public string Password
        {
            get { return password; }
            set 
            { 
                password = value;
                OnPropertyChanged();
            }
        }


        private bool checkBox;
        public bool CheckBox
        {
            get { return checkBox; }
            set 
            { 
                checkBox = value;
                OnPropertyChanged();
            }
        }

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

        #endregion

        #region ICommand
        public ICommand LogInClick
        {
            get
            {
                return null ??
                    new RelayCommand(o =>
                    {
                        Login();
                    });
            }
        }
        #endregion
        #endregion

        public LoginViewModel()
        {
            ProgressBarVisibility = SetVisibility(false);
        }

        /// <summary>
        /// Call when user click "Zaloguj" button.
        /// </summary>
        private void Login()
        {
            ProgressBarVisibility = SetVisibility(!CheckVisibility(ProgressBarVisibility));
        }
    }
}
