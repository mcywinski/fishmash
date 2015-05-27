using FishMashNew.Models;
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
using FishMashNew.Models.LoginModels;
using System.Net;
using FishMashNew.Models.GetUserInfoModels;
using FishMashApp.Models;


namespace FishMashNew.WebAPI
{
    class WebService
    {
        private string userToken;

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

        public static async Task<TokenResponse> LoginToFishMash(string inputLogin, string inputPassword)
        {
            TokenResponse tokenResponse = new TokenResponse();

                User usr = new User()
                {
                    login = inputLogin,
                    password = inputPassword
                };

                UserPost user = new UserPost()
                {
                    user = usr
                };

                var client = new HttpClient();
                client.BaseAddress = new Uri("https://shrouded-fjord-4731.herokuapp.com");

                var response = await client.PostAsJsonAsync("/api/users/authenticate", user);
                if (!response.IsSuccessStatusCode)
                {
                    if (response.StatusCode == HttpStatusCode.Unauthorized)
                    {
                        throw new UnauthorizedAccessException();
                    }
                    else 
                    {
                        throw new HttpRequestException();
                    }
                }
                //Debug.WriteLine(response);

                var result = await response.Content.ReadAsStringAsync();

                var temp = JsonConvert.DeserializeObject<TokenResponse>(result);

                tokenResponse.id = temp.id;
                tokenResponse.token = temp.token;
                tokenResponse.user_id = temp.user_id;
                tokenResponse.created_at = temp.created_at;
                tokenResponse.updated_at = temp.updated_at;

                return tokenResponse;
        }

        public static async Task<UserInfo> GetUserInfo(string token, int id)
        {
            UserInfo info = new UserInfo();
            try
            {
                string url = "https://shrouded-fjord-4731.herokuapp.com/api/users" +
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
                var result = await response.Content.ReadAsStringAsync();

                var temp = JsonConvert.DeserializeObject<UserInfo>(result);

                info.created_at = temp.created_at;
                info.email = temp.email;
                info.login = temp.login;
                info.updated_at = temp.updated_at;
                info.user_type = temp.user_type;

                return info;

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
            return info;

        }
        public static async Task<List<ExamEntity>> GetAllExams(string userToken)
        {

            List<ExamEntity> Exams = new List<ExamEntity>();
            try
            {
                string url = "https://shrouded-fjord-4731.herokuapp.com/api/users" +
                    string.Format("?api_token={0}", userToken);
                var u = new Uri(url);

                var client = new HttpClient();
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                var response = await client.GetAsync(u);
                if (!response.IsSuccessStatusCode)
                {
                    if (response.StatusCode == HttpStatusCode.Unauthorized)
                    {
                        throw new UnauthorizedAccessException();
                    }
                    else
                    {
                        throw new HttpRequestException();
                    }
                }
                var result = await response.Content.ReadAsStringAsync();

                var temp = JsonConvert.DeserializeObject<List<ExamEntity>>(result);
                foreach (ExamEntity t in temp)
                {
                    Exams.Add(new ExamEntity
                    {
                        id = t.id,
                        is_finished = t.is_finished,
                        name = t.name,
                        word_count = t.word_count,
                        date_practice_start = t.date_practice_start,
                        date_practice_finish = t.date_practice_finish,
                        date_exam_start = t.date_exam_start,
                        date_exam_finish = t.date_exam_finish
                    });
                }
                return Exams;

            }
            catch (JsonSerializationException jsonerr)
            {
                Debug.WriteLine(jsonerr.ToString());
                Debug.WriteLine("Brak dostępu do internetu.");
            }
            catch (Exception e)
            {
                Debug.WriteLine(e.ToString());
                throw new Exception("Brak dostępu do internetu.");
            }
            return Exams;

        }

        public static async Task<ExamEntity> GetExam(int examId, string userToken)
        {

            ExamEntity Exam = new ExamEntity();
            try
            {
                string url = "https://shrouded-fjord-4731.herokuapp.com/api/exams" +
                    examId +
                    string.Format("?api_token={0}", userToken);
                var u = new Uri(url);

                var client = new HttpClient();
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                var response = await client.GetAsync(u);
                if (!response.IsSuccessStatusCode)
                {
                    if (response.StatusCode == HttpStatusCode.Unauthorized)
                    {
                        throw new UnauthorizedAccessException();
                    }
                    else
                    {
                        throw new HttpRequestException();
                    }
                }
                var result = await response.Content.ReadAsStringAsync();

                var temp = JsonConvert.DeserializeObject<ExamEntity>(result);

                Exam.date_exam_finish = temp.date_exam_finish;
                Exam.date_exam_start = temp.date_exam_start;
                Exam.date_practice_finish = temp.date_practice_finish;
                Exam.date_practice_start = temp.date_practice_start;
                Exam.id = temp.id;
                Exam.is_finished = temp.is_finished;
                Exam.name = temp.name;
                Exam.word_count = temp.word_count;

                return Exam;

            }
            catch (JsonSerializationException jsonerr)
            {
                Debug.WriteLine(jsonerr.ToString());
                Debug.WriteLine("Brak dostępu do internetu.");
            }
            catch (Exception e)
            {
                Debug.WriteLine(e.ToString());
                throw new Exception("Brak dostępu do internetu.");
            }
            return Exam;
        }

        public static async Task<QuestionEntity> GetQuestionToAnswer(int examId)
        {
            QuestionEntity questionResponse = new QuestionEntity();

            string url = string.Format("{0}{1}{2}", "https://shrouded-fjord-4731.herokuapp.com/api/exams/", examId, "/get_question");
            var u = new Uri(url);

            var client = new HttpClient();

            var response = await client.PostAsync(u, null);
            if (!response.IsSuccessStatusCode)
            {
                if (response.StatusCode == HttpStatusCode.Unauthorized)
                {
                    throw new UnauthorizedAccessException();
                }
                else
                {
                    throw new HttpRequestException();
                }
            }

            var result = await response.Content.ReadAsStringAsync();

            var temp = JsonConvert.DeserializeObject<QuestionEntity>(result);

            questionResponse.id = temp.id;
            questionResponse.meaning = temp.meaning;
            questionResponse.passed = temp.passed;
            questionResponse.finished = temp.finished;
            questionResponse.exam_finished = temp.exam_finished;
            questionResponse.answer = temp.answer;

            return questionResponse;
        }

        public static async Task<AnswerEntity> AnswerQuestion(int question_id, string answer, int examId, string userToken)
        {
            AnswerEntity answerEntity = new AnswerEntity();

            AnswerBody answerBody = new AnswerBody()
            {
                answer_id = question_id,
                answer_text = answer
            };

            AnswerPostBody answerPostBody = new AnswerPostBody()
            {
                token = userToken,
                answer = answerBody
            };

            var client = new HttpClient();
            client.BaseAddress = new Uri("https://shrouded-fjord-4731.herokuapp.com");
            var apiCallAdress = string.Format("{0}{1}{2}", "/api/exams/", examId, "/answer");

            var response = await client.PostAsJsonAsync(apiCallAdress, answerPostBody);
            if (!response.IsSuccessStatusCode)
            {
                if (response.StatusCode == HttpStatusCode.Unauthorized)
                {
                    throw new UnauthorizedAccessException();
                }
                else
                {
                    throw new HttpRequestException();
                }
            }

            var result = await response.Content.ReadAsStringAsync();

            var temp = JsonConvert.DeserializeObject<AnswerEntity>(result);

            answerEntity.messsage = temp.messsage;
            answerEntity.saved = temp.saved;

            return answerEntity;
        }

        public static async Task<SummaryEntity> GetExamSummary(int examId, string userToken)
        {
            SummaryEntity summary = new SummaryEntity();
            try
            {
                string url = "https://shrouded-fjord-4731.herokuapp.com/api/exams/" +
                    examId + "/summary/" +
                    string.Format("?api_token={0}", userToken);
                var u = new Uri(url);

                var client = new HttpClient();
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                var response = await client.GetAsync(u);
                if (!response.IsSuccessStatusCode)
                {
                    if (response.StatusCode == HttpStatusCode.Unauthorized)
                    {
                        throw new UnauthorizedAccessException();
                    }
                    else
                    {
                        throw new HttpRequestException();
                    }
                }
                var result = await response.Content.ReadAsStringAsync();

                var temp = JsonConvert.DeserializeObject<SummaryEntity>(result);

                summary.answer = temp.answer;
                summary.exam_finished = temp.exam_finished;
                summary.finished = temp.finished;
                summary.id = temp.id;
                summary.meaning = temp.meaning;
                summary.passed = temp.passed;
                summary.phrase = temp.phrase;

                return summary;

            }
            catch (JsonSerializationException jsonerr)
            {
                Debug.WriteLine(jsonerr.ToString());
                Debug.WriteLine("Brak dostępu do internetu.");
            }
            catch (Exception e)
            {
                Debug.WriteLine(e.ToString());
                throw new Exception("Brak dostępu do internetu.");
            }
            return summary;
        }
    }
}
