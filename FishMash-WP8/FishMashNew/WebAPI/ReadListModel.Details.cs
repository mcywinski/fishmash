using System;
using System.Collections.Generic;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace FishMash.WebAPI
{

    internal partial class ReadListModel
    {
        internal class Details
        {

            [JsonProperty("id")]
            public int Id { get; set; }

            [JsonProperty("name")]
            public string Name { get; set; }

            [JsonProperty("description")]
            public string Description { get; set; }

            [JsonProperty("main_language_id")]
            public int MainLanguageId { get; set; }

            [JsonProperty("foreign_language_id")]
            public int ForeignLanguageId { get; set; }

            [JsonProperty("updated_at")]
            public string UpdatedAt { get; set; }
        }
    }

}
