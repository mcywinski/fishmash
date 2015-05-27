using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FishMashNew.Models
{
    public class TokenResponse
    {
        public int id { get; set; }
        public string token { get; set; }
        public int user_id { get; set; }
        public string created_at { get; set; }
        public string updated_at { get; set; }
    }
}
