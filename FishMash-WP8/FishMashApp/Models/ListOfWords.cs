using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FishMashApp.Models
{
    class ListOfWords
    {
        public int Id { get; set; }

        public string Name { get; set; }

        public string Description { get; set; }

        public int MainLanguageId { get; set; }

        public int ForeignLanguageId { get; set; }

        public DateTime DateCreatedAt { get; set; }

        public DateTime DateUpdatedAt { get; set; }
    }
}
