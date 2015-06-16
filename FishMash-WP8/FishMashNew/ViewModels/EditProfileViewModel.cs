using FishMashNew.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FishMashNew.ViewModels
{
    class EditProfileViewModel : BaseViewModel
    {
        #region Binding

        #endregion

        public EditProfileViewModel(INavigationService iNavigation) 
        {
            this.navigationService = iNavigation;
        }
    }
}
