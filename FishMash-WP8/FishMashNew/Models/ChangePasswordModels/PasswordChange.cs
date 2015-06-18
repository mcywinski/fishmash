using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FishMashNew.Models.ChangePasswordModels
{
    class PasswordChange
    {
        public string password_old { get; set; }
        public string password { get; set; }
        public string password_confirmation { get; set; }
    }
}
