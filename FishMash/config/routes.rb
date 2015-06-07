Rails.application.routes.draw do
  namespace :api do
  get 'languages/index'
  end

  get 'api' => 'application#api'
  namespace :api, defaults: {format: :json} do
    resources :exams, except: [:create, :delete, :update] do
      post 'start'
      post 'get_question'
      post 'answer'
      get 'summary'
    end
    resources :lists, only: [:index, :show] do
      post 'add'
      post 'remove'
    end
    resources :words, only: [:create]
    resources :languages, only: [:index]
    resources :users do
      collection do
        post 'authenticate'
      end
    end
  end

  resources :wordlists, controller: 'word_lists'
  resources :exams, only: [:index, :new, :create] do
    get 'begin'
    post 'start'
    get 'answer'
    post 'answer', to: 'exams#save_answer'
    get 'summary'
    get 'learn'
    get 'stats'
  end
  resources :users, only: [:show, :create] do
    collection do
      get 'login'
      post 'authenticate'
      get 'logout'
      get 'register'
      get 'profile'
    end

    post 'set_password'
  end
  resources 'classes', except: [:show] do
    post 'add_member'
    post 'remove_member'
  end
  root 'home#index'

  # The priority is based upon order of creation: first created -> highest priority.
  # See how all your routes lay out with "rake routes".

  # You can have the root of your site routed with "root"
  # root 'welcome#index'

  # Example of regular route:
  #   get 'products/:id' => 'catalog#view'

  # Example of named route that can be invoked with purchase_url(id: product.id)
  #   get 'products/:id/purchase' => 'catalog#purchase', as: :purchase

  # Example resource route (maps HTTP verbs to controller actions automatically):
  #   resources :products

  # Example resource route with options:
  #   resources :products do
  #     member do
  #       get 'short'
  #       post 'toggle'
  #     end
  #
  #     collection do
  #       get 'sold'
  #     end
  #   end

  # Example resource route with sub-resources:
  #   resources :products do
  #     resources :comments, :sales
  #     resource :seller
  #   end

  # Example resource route with more complex sub-resources:
  #   resources :products do
  #     resources :comments
  #     resources :sales do
  #       get 'recent', on: :collection
  #     end
  #   end

  # Example resource route with concerns:
  #   concern :toggleable do
  #     post 'toggle'
  #   end
  #   resources :posts, concerns: :toggleable
  #   resources :photos, concerns: :toggleable

  # Example resource route within a namespace:
  #   namespace :admin do
  #     # Directs /admin/products/* to Admin::ProductsController
  #     # (app/controllers/admin/products_controller.rb)
  #     resources :products
  #   end
end
