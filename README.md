# Movies List from TMDB

https://www.themoviedb.org/

1. The project is using this api above
2. the app architecture is multi modules separate in data, domain, features (feed and details)
3. I used a specific container call ListDetailPaneScaffold, it s handling the different screen size 
4. I m using retrofit, hilt, compose, coroutine, paging3
5. Unit and integration test, there is implementation of tests in data and feature feed module
    - MovieItemKtTest
    - MovieRepositoryImplTest
    - MovieListViewModelTest
6. One class in android test to check the RemoteMediator
    - MovieRemoteMediatorTest

The tests needs to run with --refresh-dependencies as they use dependencies from different module



## Simple list of card with paging 

|Dark Theme | Light Theme|
|-|-|
|<img src="screenshots%2Fdark.png" width="300" />|<img src="screenshots%2Flight.png" width="300" />|


# Usage of secret gradle plugin

https://github.com/google/secrets-gradle-plugin<br>

## Allow to not share the tmdb api key in the versioning tool.

1. Add plugin dependencies on project level
2. Add plugin dependencies on module level
3. Add in BuildFeatures bracket  { BuildConfig = true } // below compose = true
4. Add your variable in your local.properties file
5. You can use anywhere in your module BuildConfig.apiKey
   

<img src="screenshots%2FlocalProperties.png" width="900" />
