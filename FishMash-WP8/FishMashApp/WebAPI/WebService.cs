using FishMashApp.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using Newtonsoft.Json.Converters;
using System.Diagnostics;
using FishMash.WebAPI;
using System.Globalization;


namespace FishMashApp.WebAPI
{
    class WebService
    {
        async static public Task<List<ReadWord.Word>> GetWordsOfListAsync(int id)
        {
            List<ReadWord.Word> list = new List<ReadWord.Word>();
            try
            {
                string url = "https://shrouded-fjord-4731.herokuapp.com/api/lists/" +
                    id.ToString("0", CultureInfo.InvariantCulture);
                var u = new Uri(url);

                var client = new HttpClient();
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                var response = await client.GetAsync(u);
                if (!response.IsSuccessStatusCode)
                {
                    Debug.WriteLine("Wysłanie zapytania nie powiodło się!");
                    throw new Exception();
                }
                //Debug.WriteLine(response);

                var result = await response.Content.ReadAsStringAsync();
                //Debug.WriteLine(result);

                var temp = JsonConvert.DeserializeObject<ReadWord>(result);

                for (int i = 0; i < temp.Words.Count(); i++)
                {
                    list.Add(new ReadWord.Word
                    {
                        Id = Convert.ToInt32(temp.Words[i].Id),
                        Meaning = temp.Words[i].Meaning.ToString(),
                        Phrase = temp.Words[i].Phrase.ToString()
                    });
                }
                return list;
            }
            catch (JsonSerializationException jsonerr)
            {
                Debug.WriteLine(jsonerr.ToString());
                Debug.WriteLine("Brak dostępu do internetu.");
            }
            catch (Exception e)
            {
                Debug.WriteLine(e.ToString());
                throw new Exception("Brak internetu");
            }
            return list;
        }

        async static public Task<List<ListOfLists>> GetListOfListAsync()
        {
            List<ListOfLists> list = new List<ListOfLists>();
            try
            {
                string url = "https://shrouded-fjord-4731.herokuapp.com/api/lists"; 
                var u = new Uri(url);

                var client = new HttpClient();
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                var response = await client.GetAsync(u);
                if (!response.IsSuccessStatusCode)
                {
                    Debug.WriteLine("Wysłanie zapytania nie powiodło się!");
                    throw new Exception();
                }
                //Debug.WriteLine(response);

                var result = await response.Content.ReadAsStringAsync();
                //Debug.WriteLine(result);

                var temp = JsonConvert.DeserializeObject<List<ReadListModel>>(result);
                foreach (ReadListModel t in temp)
                {
                    list.Add(new ListOfLists
                    {
                        Id = Convert.ToInt32(t.Values.Id),
                        Name = t.Values.Name.ToString(),
                        Description = t.Values.Description.ToString(),
                        MainLanguageId = Convert.ToInt32(t.Values.MainLanguageId),
                        ForeignLanguageId = Convert.ToInt32(t.Values.ForeignLanguageId),
                        DateUpdatedAt = Convert.ToDateTime(t.Values.UpdatedAt)
                    }
                    );
                }
                return list;
            }
            catch (JsonSerializationException jsonerr)
            {
                Debug.WriteLine(jsonerr.ToString());
                Debug.WriteLine("Brak dostępu do internetu.");
            }
            catch (Exception e)
            {
                Debug.WriteLine(e.ToString());
                throw new Exception("Brak internetu");
            }
            return list;
        }

        public static async Task<List<ListOfLists>> GetListOfListOfflineAsync()
        {
            List<ListOfLists> result = new List<ListOfLists>
            {
                new ListOfLists
                {
                    Id = 1,
                    Name = "Jedzenie/Angielski",
                    Description = "Lista słówek dotyczących jedzenia po angielsku",
                    MainLanguageId = 1,
                    ForeignLanguageId = 2,
                    DateCreatedAt = DateTime.Now,
                    DateUpdatedAt = DateTime.Now
                },
 
                new ListOfLists
                {
                    Id = 2,
                    Name = "Ubrania/Angielski",
                    Description = "Lista słówek dotyczących ubrań po angielsku",
                    MainLanguageId = 1,
                    ForeignLanguageId = 2,
                    DateCreatedAt = DateTime.Now,
                    DateUpdatedAt = DateTime.Now
                },
 
                new ListOfLists
                {
                    Id = 3,
                    Name = "Pory roku/Niemiecki",
                    Description = "Lista słówek dotyczących pór roku po niemiecku",
                    MainLanguageId = 1,
                    ForeignLanguageId = 3,
                    DateCreatedAt = DateTime.Now,
                    DateUpdatedAt = DateTime.Now
                },
 
            };
            return result;
        }
    }
}
