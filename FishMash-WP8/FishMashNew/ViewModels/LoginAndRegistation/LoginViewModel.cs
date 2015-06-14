using FishMashNew.Common;
using FishMashNew.Models;
using FishMashNew.Views;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace FishMashNew.ViewModels.LoginAndRegistation
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
            get 
            { 
                return progressBarVisibility; 
            }
            set 
            { 
                progressBarVisibility = value;
                OnPropertyChanged();
            }
        }

        private string errorUserControlText;

        public string ErrorUserControlText
        {
            get { return errorUserControlText; }
            set 
            { 
                errorUserControlText = value;
                OnPropertyChanged();
            }
        }
        

        #endregion

        #region UI
        private string incorrectPasswordUserControlVisibility;

        public string IncorrectPasswordUserControlVisibility
        {
            get { return incorrectPasswordUserControlVisibility; }
            set 
            { 
                incorrectPasswordUserControlVisibility = value;
                OnPropertyChanged();
            }
        }
        
        #endregion

        #region ICommand
        public ICommand OkButtonOnIncorrectPasswordUserControl
        {
            get
            {
                return null ??
                    new RelayCommand(o =>
                    {
                        IncorrectPasswordButtonClick();
                    });
            }
        }
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

        public LoginViewModel(INavigationService navigationService)
        {
            this.navigationService = Common.Settings.Instance.navigationService;
            ProgressBarVisibility = SetVisibility(false);
            IncorrectPasswordUserControlVisibility = SetVisibility(false);
        }

        /// <summary>
        /// Call when user click "Zaloguj" button.
        /// </summary>
        private async void Login()
        {
            ProgressBarVisibility = SetVisibility(true);
            
            TokenResponse token;
            try 
            {
                token = await WebAPI.WebService.LoginToFishMash(User, Password);
                Settings.Instance.Cache.SetToken(token);
                GoToMainPage();
            }
            catch(UnauthorizedAccessException e) 
            {
                ErrorUserControlText = "Wrong login or password.";
                IncorrectPasswordUserControlVisibility = SetVisibility(true);
                
            }
            catch (HttpRequestException e)
            {
                ErrorUserControlText = "Internet connection error.";
                IncorrectPasswordUserControlVisibility = SetVisibility(true);
            }
            
            ProgressBarVisibility = SetVisibility(false);
        }

        private void IncorrectPasswordButtonClick()
        {
            IncorrectPasswordUserControlVisibility = SetVisibility(false);
        }

        public void GoToMainPage()
        {
            this.navigationService.Navigate(typeof(BrowseWordsView));
        }
    }
}
