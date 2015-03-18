using FishMashApp.ViewModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FishMashApp.Models
{
    class ListOfLists : BaseViewModel
    {
        private int id;
        private string name;
        private string description;
        private int mainLanguageId;
        private int foreignLanguageId;
        private DateTime dateCreatedAt;
        private DateTime dateUpdatedAt;

        public int Id
        {
            get { return id; }
            set
            {
                id = value;
                OnPropertyChanged("Id");
            }
        }
        public string Name
        {
            get { return name; }
            set
            {
                name = value;
                OnPropertyChanged("Name");
            }
        }
        public string Description
        {
            get
            {
                return description;
            }
            set
            {
                description = value;
                OnPropertyChanged("Description");
            }
        }

        public int MainLanguageId
        {
            get
            {
                return mainLanguageId;
            }
            set
            {
                mainLanguageId = value;
                OnPropertyChanged("MainLanguageId");
            }
        }

        public int ForeignLanguageId {
            get
            {
                return foreignLanguageId;
            }
            set
            {
                foreignLanguageId = value;
                OnPropertyChanged("ForeginLanguageId");
            }
        }

        public DateTime DateCreatedAt {
            get
            {
                return dateCreatedAt;
            }
            set
            {
                dateCreatedAt = value;
                OnPropertyChanged("DateCreatedAt");
            }
        }

        public DateTime DateUpdatedAt {
            get
            {
                return dateUpdatedAt;
            }
            set
            {
                dateUpdatedAt = value;
                OnPropertyChanged("DateUpdatedAt");
            }
        }
    }
}
