using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.Storage;

namespace FishMashNew.Common
{
    class Settings
    {
        public INavigationService navigationService;

        #region Constructor
        private static Settings instance;
        public Cache Cache;
        private Settings()
        {
            Cache = new Cache();
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
        public ApplicationDataContainer getLocalSettings()
        {
            return Cache.getLocalSettings();
        }
        #endregion
        #endregion


    }
}
