using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FishMashApp.Models
{
    public class AnswerPostBody
    {
        public string token { get; set; }

        public AnswerBody answer { get; set; }
    }
}
