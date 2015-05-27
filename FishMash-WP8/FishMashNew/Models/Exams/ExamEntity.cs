using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FishMashApp.Models
{
    public class ExamEntity
    {
        public int id { get; set; }
        public string name { get; set; }
        public string date_exam_start { get; set; }
        public string date_exam_finish { get; set; }
        public string date_practice_start { get; set; }
        public string date_practice_finish { get; set; }
        public int word_count { get; set; }
        public bool is_finished { get; set; }
    }
}
