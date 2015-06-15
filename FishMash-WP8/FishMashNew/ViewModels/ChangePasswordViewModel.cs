using FishMashNew.Common;
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
        #region ICommand
        public ICommand ChangePasswordClick
        {
            get
            {
                return null ??
                    new RelayCommand(o =>
                    {
                        Debug.WriteLine("change password click");
                        ErrorInfoUserControlVisibility = SetVisibility(true);
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
            ErrorInfoUserControlVisibility = SetVisibility(false);
        }
    }
}
