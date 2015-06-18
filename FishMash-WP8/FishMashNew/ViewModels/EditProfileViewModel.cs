using FishMashNew.Common;
using FishMashNew.Models.GetUserInfoModels;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FishMashNew.ViewModels
{
    class EditProfileViewModel : BaseViewModel
    {
        #region Binding
        private string studyingSince;
	    public string StudyingSince
	    {
		    get { return studyingSince;}
		    set 
            { 
                studyingSince = value;
                OnPropertyChanged();
            }
	    }
	
        private string lastChange;

	    public string LastChange
	    {
		    get { return lastChange;}
		    set 
            { 
                lastChange = value;
                OnPropertyChanged();
            }
	    }
	
        

        private string login;

        public string Login
        {
            get { return login; }
            set 
            { 
                login = value;
                OnPropertyChanged();
            }
        }

        private string  email;

        public string  Email
        {
            get { return email; }
            set 
            { 
                email = value;
                OnPropertyChanged();
            }
        }
        
        
        #endregion

        public EditProfileViewModel(INavigationService iNavigation) 
        {
            this.navigationService = iNavigation;
            GetUserInfo();
        }

        private async void GetUserInfo()
        {
            UserInfo temp = await WebAPI.WebService.GetUserInfo(Settings.Instance.Cache.GetToken(), Convert.ToInt32(Settings.Instance.Cache.GetUserID()));
            StudyingSince = temp.created_at;
            LastChange = temp.updated_at;
            Login = temp.login;
            Email = temp.email;
        }
    }
}
