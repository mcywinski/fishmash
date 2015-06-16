using FishMashNew.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.Storage;

namespace FishMashNew.Common
{
    class Cache
    {
        ApplicationDataContainer localSettings;
        private List<Language> LanguageList;
        public Cache()
        {
            localSettings = ApplicationData.Current.LocalSettings;
            initProperties();
            LanguageList = new List<Language>();
        }
        public ApplicationDataContainer getLocalSettings()
        {
            return localSettings;
        }

        private void initProperties()
        {
            if (localSettings.Values["Token"] == null)
            {
                localSettings.Values["Token"] = "";
            }
            if (localSettings.Values["UserName"] == null)
            {
                localSettings.Values["UserName"] = "";
            }
            if (localSettings.Values["Password"] == null)
            {
                localSettings.Values["Password"] = "";
            }
            if (localSettings.Values["UserID"] == null)
            {
                localSettings.Values["UserID"] = "";
            }

        }

        public void SetToken(TokenResponse inputTokenResponse)
        {
            localSettings.Values["Token"] = inputTokenResponse.token;
        }

        public void ClearToken()
        {
            localSettings.Values["Token"] = "";
        }

        public string GetToken()
        {
            return localSettings.Values["Token"].ToString();
        }

        public string GetUserID()
        {
            return localSettings.Values["UserID"].ToString();
        }

        public void SetUserID(int id)
        {
            localSettings.Values["UserID"] = id;
        }

        public void SetUserID(string id)
        {
            localSettings.Values["UserID"] = id;
        }
        public List<Language> GetLanguages()
        {
            return LanguageList;
        }

        public void SetLanguages(List<Language> languagesList)
        {
            LanguageList = languagesList;
        }
    }
}
