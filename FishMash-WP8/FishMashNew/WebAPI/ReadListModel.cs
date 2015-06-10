using System;
using System.Collections.Generic;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace FishMash.WebAPI
{

    internal partial class ReadListModel
    {

        [JsonProperty("details")]
        public Details Values { get; set; }
    }

}
