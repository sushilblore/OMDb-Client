# OMDb-Client

This is a demo app to demonstrate the use of OMDb API.

# App Screens and Features

# MoviesList Screen

This is the first screen of the app. At the top of sreen is a search toolbar. User can search for movie names/keywords and when entered shows the list of movies for the searched item. 

Design : 

For this screen, I am fetching 10 items in one request. So, as user keeps scrolling the list, more items are fetched dynamically. More pages can be fethered at once, by changing the threshold limit in code (for now, I have set it to 1).

This screen works in both portrait and landscape mode and at point during intercation, screen can be rotated.

Edge Cases :

1. If no movie is found for the searched keyword, error message is shown.
2. If there is no internet or query fails on the server, appropriate error message is shown.
3. If while scrolling through the list of movies, so error happens (no internet/query fail), appropriate error message is shown

# MovieDetails Screen

On clicking any item in the list, MovieDetails screen is show. This screen has detail information for the movie item which is clicked. The screen can be scrolled if the information does not fit in the screen.

Design :

I am fetching the movie details information once user clicks on an item to get the information dynamically. This screen has different layouts in landscape and portrait mode unlike MovieList Screen.

Edge Cases:

Appropriate error messages would be shown in case of errors - No Network / Query failure.

# App Design:

# MVP

The app is based on MVP architecture (Model, View, Presenter) which is better than MVC when it comes to coupling. MVP makes the view code way cleaner than when using MVC, since the views, activities and the fragments will only have the code related to rendering the customizing the UI and no interactions with the controllers. MVP (Model View Presenter) is a pattern thats allows separate the presentation layer from the logic, so that everything about how the interface works is separated from how we represent it on screen.

# Dagger 2

The app uses Dependency Injection pattern using Dagger 2. Ths is used to inject the network service in the fragment which can then use it in its presenter class to fetch data from network. You can see more details about Dagger in http://square.github.io/dagger/

# Retrofit

The app uses Retrofit as REST client. Retrofit is a REST Client for Android and Java. It makes it relatively easy to retrieve and upload JSON. Retrofit uses the OkHttp library for HTTP requests.

# RXJava

The app also uses RxJava for making asynchronous event calls in a clean way. RxJava is a Java VM implementation of Reactive Extensions: a library for composing asynchronous and event-based programs by using observable sequences.

# RecyclerView and CardView

This app uses RecyclerView and CardView(Gridlayout) to show the items.

# Unit Tests

As the most imprtant api in this app is the call to backend server to fetch movie information, so I have added unit tests to test Network API and Network Service.

They can be found in following files:

NetworkApisUnitTest.java
NetworkServiceUnitTest.java

# Instrumentation Tests

I have a added an instrumentation test to test the search tool bar.

They can be found in following file:

SearchViewTest.java

# Prerequisites

JDK 1.8
Android SDK.
Supports Jelly Bean (API 16) - Android N (API 25) .
Latest Android SDK Tools and build tools.

# Authors

Sushil Kumar Jha
