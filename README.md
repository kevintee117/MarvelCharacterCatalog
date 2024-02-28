# Marvel Comic Catalog Display

This app is a small little side project aimed at getting some data (Title, description and image) from the marvel comics developer API and displaying it in an Android app. 
By default it displays comic ID 109961 which is the comic Spider Man(2022) #1.

## Usage:

1) git clone the project
2) import into android studio
3) Add your own API keys from the marvel developer website in the local.properties file. By default it is named MARVEL_PUBLIC_KEY and MARVEL_PRIVATE_KEY.
5) Run in an emulator or your device of choice



## Libraries used:

* JUnit for unit tests
* Jetpack Compose for UI and instrumented test cases
* Retrofit for REST api calls
* GSON for rest api calls
* Coil for downloading and displaying images from a given URL

