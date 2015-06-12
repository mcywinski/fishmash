using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FishMashNew.Models.StartExamModels
{
    class Started
    {
        public int id { get; set; }
        public int exam_id { get; set; }
        public int user_id { get; set; }
        public string time_started { get; set; }
        public string created_at { get; set; }
        public string updated_at { get; set; }
        public object finished { get; set; }
    }
}
