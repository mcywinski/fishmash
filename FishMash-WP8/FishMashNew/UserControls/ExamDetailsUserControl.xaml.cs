using FishMashNew.Common;
using FishMashNew.Views;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// The User Control item template is documented at http://go.microsoft.com/fwlink/?LinkId=234236

namespace FishMashNew.UserControls
{
    public sealed partial class ExamDetailsUserControl : UserControl
    {
        public ExamDetailsUserControl()
        {
            this.InitializeComponent();
        }

        private void Grid_Tapped(object sender, TappedRoutedEventArgs e)
        {
            object t = this.ListId.Text.ToString();
            Settings.Instance.Cache.SetExamName(this.Name.Text.ToString());
            if (Label.Content.ToString() == "stats")
            {
                Settings.Instance.navigationService.Navigate(typeof(ExamSummaryView), t);
            }
            else
            {
                Settings.Instance.navigationService.Navigate(typeof(ExamView), t);
            }
        }
    }
}
