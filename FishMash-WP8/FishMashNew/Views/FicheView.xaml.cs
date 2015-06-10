using FishMashNew.Common;
using FishMashNew.ViewModels;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.Graphics.Display;
using Windows.UI.ViewManagement;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;
using FishMashNew.Common;
using System.Diagnostics;

// The Basic Page item template is documented at http://go.microsoft.com/fwlink/?LinkID=390556

namespace FishMashNew.Views
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class FicheView : Page
    {

        
        private FischeViewModel viewModel;

        public FicheView()
        {
            this.InitializeComponent();

            viewModel = new FischeViewModel(new NavigationService());
            this.DataContext = viewModel;
            HideStatusBar();
        }

        private async void HideStatusBar()
        {
            StatusBar statusBar = Windows.UI.ViewManagement.StatusBar.GetForCurrentView();
            await statusBar.HideAsync();
        }

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            if (e.Parameter.ToString() != "")
            {
                viewModel.FillList(Convert.ToInt32(e.Parameter.ToString()));
            }
            else
            {
                Frame.Navigate(typeof(BrowseWordsView));
            }
        }

        protected override void OnNavigatedFrom(NavigationEventArgs e)
        {
            
        }

        

        private void WordTranslate_Tapped(object sender, TappedRoutedEventArgs e)
        {
            viewModel.WordTranslateGridTapped();
        }
    }
}
