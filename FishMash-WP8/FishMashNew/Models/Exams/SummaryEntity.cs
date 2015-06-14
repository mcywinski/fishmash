using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FishMashApp.Models
{
    public class SummaryEntity
    {
        public int id { get; set; }
        public string answer { get; set; }
        public bool finished { get; set; }
        public bool passed { get; set; }
        public string meaning { get; set; }
        public bool exam_finished { get; set; }
        public string phrase { get; set; }
    }
}
