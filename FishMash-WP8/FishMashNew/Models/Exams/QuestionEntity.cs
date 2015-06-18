using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FishMashApp.Models
{
    public class QuestionEntity
    {
        public int id { get; set; }
        public object answer { get; set; } 
        public object finished { get; set; }
        public object passed { get; set; }
        public string meaning { get; set; } //question
        public bool exam_finished { get; set; }
    }
}
