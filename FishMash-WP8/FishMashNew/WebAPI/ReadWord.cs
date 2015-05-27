using System;
using System.Collections.Generic;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace FishMash.WebAPI
{

    internal partial class ReadWord
    {

        [JsonProperty("details")]
        public FishMash.WebAPI.ReadListModel.Details Values { get; set; }

        [JsonProperty("words")]
        public Word[] Words { get; set; }
    }

}
