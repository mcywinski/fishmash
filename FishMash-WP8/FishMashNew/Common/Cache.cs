﻿using FishMashNew.Models;
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
        public Cache()
        {
            localSettings = ApplicationData.Current.LocalSettings;
            initProperties();
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
    }
}
