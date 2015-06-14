using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FishMashNew.WebAPI
{
    public class ReadListWord
    {
        [JsonProperty("id")]
        public int Id { get; set; }

        [JsonProperty("phrase")]
        public string Phrase { get; set; }

        [JsonProperty("meaning")]
        public string Meaning { get; set; }
    }
}
