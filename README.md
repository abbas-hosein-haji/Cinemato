# Cinemato
Cinemato is a fully functional android app which I made from scratch.It is the fastest, easiest way to find and discover movies, actors and ratings.


### Getting Started

App uses The Movie Database API. You have to enter your API key in order to run the app. You can create your own one very easy! https://www.themoviedb.org/account/signup?language=en-EN. When you get it, just set it here:  
  ```
app/gradle.properties
```  

<img src="image/gradle.png" width="600">  
  
  
## Features

* Discover now playing, the most popular, the most rated or the upcoming movies
* Watch movie trailers and teasers
* Read reviews from other users
* Search for any movie to find detailed information, including cast, trailers, similar movies and more
* Search for any actor to see their bio, filmography, images and more
* Material Design  
  
**Download:**

You can download APK [on releases page][releases].  
  
  
## Screens  

<img src="image/Screenshot_1.png" width="240">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
<img src="image/Screenshot_3.png" width="240">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="image/Screenshot_4.png" width="240">    
<br><br>
 <img src="image/Screenshot_5.png" width="240">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 <img src="image/Screenshot_6.png" width="240">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 <img src="image/Screenshot_7.png" width="240">  
   
   
## Libraries

* [Retrofit](https://github.com/square/retrofit)
* [RxJava](https://github.com/ReactiveX/RxJava)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [Glide](https://github.com/bumptech/glide)
* [ButterKnife](https://github.com/JakeWharton/butterknife)  
  
  
## Model-View-ViewModel Architecture (MVVM)  

The project contains an exemplification of the Model-View-ViewModel pattern used together with RxJava.  
The `DataModel` provides the data that the app needs from TMDB api.  
The `ViewModel` exposes the response as stream of events through [RxJava Observables][observables].  
The `View` is all the Activities that contain movie and actor data.

<img src="image/mvvm.png" width="600">

[observables]: <http://reactivex.io/documentation/observable.html>
[releases]: https://github.com/abbas-hosein-haji/Cinemato/releases
