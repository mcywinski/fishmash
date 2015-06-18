using FishMashNew.Common;
using FishMashNew.ViewModels;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.Graphics.Display;
using Windows.Storage;
using Windows.UI.Core;
using Windows.UI.ViewManagement;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// The Basic Page item template is documented at http://go.microsoft.com/fwlink/?LinkID=390556

namespace FishMashNew.Views
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class BrowseWordsView : Page
    {
        private BrowseWordsViewModel viewModel;

        public BrowseWordsView()
        {
            this.InitializeComponent();

            viewModel = new BrowseWordsViewModel(new NavigationService());
            this.DataContext = viewModel;

            
            Common.Settings.Instance.navigationService = new Common.NavigationService();

            
        }

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            HideStatusBar();
            Frame.BackStack.Clear();
            string token = Settings.Instance.Cache.GetToken();
            if (token == "")
            {
                CoreDispatcher dispatcher = CoreWindow.GetForCurrentThread().Dispatcher;
                 dispatcher.RunAsync(CoreDispatcherPriority.Normal, () => {
                    Frame.Navigate(typeof(LoginView));
                });
            }
        }
        
        private async void HideStatusBar()
        {
            StatusBar statusBar = Windows.UI.ViewManagement.StatusBar.GetForCurrentView();
            await statusBar.HideAsync();
        }
        protected override void OnNavigatedFrom(NavigationEventArgs e)
        {
            
        }


        private void LogInButton_Click(object sender, RoutedEventArgs e)
        {
            Frame.Navigate(typeof(LoginView));
        }

        private void LogOutButton_Click(object sender, RoutedEventArgs e)
        {
            Settings.Instance.Cache.ClearToken();
            Frame.Navigate(typeof(LoginView));
        }



        private void ProfileButton_Click(object sender, RoutedEventArgs e)
        {
            Frame.Navigate(typeof(EditProfileView));
        }



    }
}
