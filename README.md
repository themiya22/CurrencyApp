💱 CurrencyApp

A feature-rich Android application built in Java that provides three core functions: 
  -real-time currency conversion, a persistent conversion history log, and a curated financial news feed.
  -The application is built using Java, Volley for networking, and Firebase Firestore for database persistence.


💡 Key Features
The application is structured around three main activities, all accessible from the home screen.


1. Currency Conversion

    -Fetches real-time exchange rates from the "https://app.freecurrencyapi.com" API.
   
    -Allows users to input an amount and select "from" and "to" currencies.
   
    -Displays the converted result and saves the transaction to the history upon a successful conversion.
   
    -Additionally can clear the fields using "clear" and go back to home using "Home" buttons. 


3. Conversion History
    -Automatically saves every successful conversion (from/to currency, amounts) to a Firebase Firestore collection named "conversionHistory".
   
    -Displays the full list of past conversions in a RecyclerView, sorted by the most recent first.
   
    -Includes a "Clear History" button that uses a WriteBatch to delete all history documents from Firestore.
   
    -Uses a custom HistoryAdapter to format and display the items.


5. Financial News Feed
    -Fetches and displays a list of financial news articles from the "newsdata.io" API.
   
    -Uses the Picasso library to efficiently load and display article images in the RecyclerView.
   
    -Includes a "Read more" button for each article, which opens the original source URL in the user's web browser.

   

⚙️ Technical Stack


1. Platform -> Android

     -Targets API 36 (minSdk and targetSdk).
  
2. Language -> Java 11
   
     -Uses Java 11 for source and target compatibility.
  
3. Database -> Firebase Firestore
   
     -For storing and retrieving conversion history.
  
4. Networking -> Volley	

     -Used for all API requests to "freecurrencyapi.com" and "newsdata.io".
  
5. Image Loading ->	Picasso	

     -Handles loading images for news articles efficiently.
  
6. UI Components	-> RecyclerVie and ConstraintLayout	

     -Used for displaying the news and history feeds.

  

🚀 Installation & Setup 


  1. Prerequisites

     -Android Studio (Latest Stable Version)

     -Java Development Kit (JDK) 11+

     -Firebase Account (for History feature)

     -API Keys for:
        -freecurrencyapi.com
        -newsdata.io

2. Setup
    Clone the repository:
   
       -git clone https://github.com/YourUsername/CurrencyApp.git
       -cd CurrencyApp
   
    Open in Android Studio and wait for Gradle to sync.

4. Configuration
    Firebase Setup (History):
   
        1. Go to the Firebase Console and create a new project.
     
        2. Add an Android app to your Firebase project, using the package name com.example.currencyapp.
  
        3. Download the generated google-services.json file and place it in the app/ directory of this project.
     
        4. Enable Firestore Database in the Firebase console.

    API Keys (Required): The app uses hardcoded API keys for testing. You must replace them with your own.
   
        -Currency API: Open app/src/main/java/com/example/currencyapp/ConverterActivity.java and replace the API_KEY value.
     
        -News API: Open app/src/main/java/com/example/currencyapp/NewsActivity.java and replace the key in the api_url string.

   Happy coding! 🚀
   
