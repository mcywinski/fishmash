using System;
using System.Collections.Generic;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace FishMash.WebAPI
{

    internal partial class ReadWord
    {
        internal class Word
        {

            [JsonProperty("id")]
            public int Id { get; set; }

            [JsonProperty("phrase")]
            public string Phrase { get; set; }

            [JsonProperty("meaning")]
            public string Meaning { get; set; }
        }
    }

}
