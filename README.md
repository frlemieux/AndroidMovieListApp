# Movies List from TMDB

https://www.themoviedb.org/


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
