using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FishMashApp.Common
{
    class Settings
    {
        public INavigationService navigationService;

        #region Constructor
        private static Settings instance;
        private Settings()
        {

        }
        #region Singleton
        public static Settings Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new Settings();
                }
                return instance;
            }
        }
        #endregion
        #endregion


    }
}
