using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FishMashNew.Models.GetUserInfoModels
{
    public class UserInfo
    {
            public string created_at { get; set; }
            public string email { get; set; }
            public string login { get; set; }
            public string updated_at { get; set; }
            public object user_type { get; set; }
    }
}
