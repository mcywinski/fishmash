using FishMashApp.Models.Exams;
using FishMashNew.Common;
using FishMashNew.Models;
using FishMashNew.Models.ChangePasswordModels;
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
    class ChangePasswordViewModel : BaseViewModel
    {
        #region Binding
        private string oldPassword;
        public string OldPassword
        {
            get { return oldPassword; }
            set 
            {
                oldPassword = value;
                OnPropertyChanged(); 
            }
        }
        private string password1;
        public string Password1
        {
            get { return password1; }
            set
            {
                password1 = value;
                OnPropertyChanged();
            }
        }
        private string password2;
        public string Password2
        {
            get { return password2; }
            set
            {
                password2 = value;
                OnPropertyChanged();
            }
        }

        #region ICommand
        public ICommand ChangePasswordClick
        {
            get
            {
                return null ??
                    new RelayCommand(o =>
                    {
                        if (OldPassword != "" && Password1 != "" && Password1 == Password2)
                        {
                            ChangePassword();
                        }
                        else
                        {
                            if (Password1 != Password2)
                                ErrorUserControlText = "New passwords must be identical!";
                            if(OldPassword == "")
                                ErrorUserControlText = "Old password is empty!";

                            ErrorInfoUserControlVisibility = SetVisibility(true);
                        }
                    });
            }
        }
        #endregion

        #region ErrorInfo
        public ICommand OkButtonOnIncorrectPasswordUserControl
        {
            get
            {
                return null ??
                    new RelayCommand(o =>
                    {
                        ErrorInfoUserControlVisibility = SetVisibility(false);
                        
                    });
            }
        }

        private string errorUserControlText;

	    public string ErrorUserControlText
	    {
		    get { return errorUserControlText;}
		    set 
            { 
                errorUserControlText = value;
                OnPropertyChanged();
            }
	    }


        private string errorInfoUserControlVisibility;

        public string ErrorInfoUserControlVisibility
        {
            get { return errorInfoUserControlVisibility; }
            set
            {
                errorInfoUserControlVisibility = value;
                OnPropertyChanged();
            }
        }

        #endregion

        #endregion

        public ChangePasswordViewModel(INavigationService iNavigation) 
        {
            this.navigationService = iNavigation;
            Password1 = "";
            ErrorInfoUserControlVisibility = SetVisibility(false);
        }

        private async void ChangePassword()
        {
            PasswordChangeResult temp = await WebAPI.WebService.ChangePassword(OldPassword, Password1, Password2, Settings.Instance.Cache.GetToken());
            Debug.WriteLine(temp.result_status.ToString());
            if (temp.success)
            {
                Debug.WriteLine("Hasło zmienione");
                this.navigationService.Navigate(typeof(BrowseWordsView));
            }
            else
            {
                if (temp.result_status == -2)
                {
                    ErrorUserControlText = "Old password is incorrect!";
                    ErrorInfoUserControlVisibility = SetVisibility(true);
                }
            }
        }
    }
}
