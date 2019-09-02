# Stox

### General
This application keeps track of your chosen stocks. Inspired by Apple's application(The one you get with the phone straight away).
The main purpose of creating this app was practicing the connection of the important elements of: Multi-threading/concurrency, API, JSON,
Database integration and a login system.
The GUI framework is JavaFX 13.
Data of the stocks is consumed by AlphaAdvantage API. I am using the free API which is confined to 5 calls per minute and 500 per day.
Database used is MySQL(JDBC).

### Architecture
This application consits of 4 sub-applications:
1. Client: the client side. Includes GUI for the user and such.
2. Server: Mainly the server is the app that connects to the database and retrieving data from it.
3. Passport: more of sub-app. An app that handles the login/register flow. minimal GUI and data from user is required due to time shortage. I might upgrade this one in the future.
4. CentralLogger: An application which all it does is logging logs into a database. It is a full application which I have made and can be viewed here: https://github.com/noamgrinch/CentralLogger.

### Runtime

When the program is run, it first runs the Server and the CentralLogger. both of the applications needs to connect to the database first.
After that, the passport application is booted up. The user needs to log in or regsiter, you know, the usual stuff.
The users data is stored in the database in a table called "Users". The data which the database saves regarding every user is:
UserID(auto-generated), Username, Password, Email, Stocks(Will be elabroted later on), Registration Date, Last login date, Country(currently not activated).
After the login stage, the user is logged in and main window is showing. if the user is not new and he already saved stocks to follow, the Passport app retrieves the user to the client app so it loads the stocks he choose to follow from the database.
When a user changes his stocks list during the session it affects only on the app's cache. Only when the user is logging out the app is updating the database regarding his latest changes. In V4, I will change it so everychange is saved directly to the database so if the app is crashing it is all saved.
There is a thread which is fired every 20 seconds to update the stocks statistics.


### Login/Registeration
The regisration flow is a basic flow. It only checks that the Email is in a valid form and the Username and the Password is not empty.
If those are ok, you are good to go.

### Database
The environment that this app was tested is on my computer, that means that the MySql server is running on the localhost:3036. 
Both the Server and CentralLogger app's can be configured to connected to a different database.

### StockReader
The stock reader is a sub-app which gets a stock's ticker as an input and returns a Stock data structure. It users the AlphaAdvantage API: https://www.alphavantage.co/.
It gets back a JSON file from the API and parses it with the exetrnal libaray: https://github.com/stleary/JSON-java.


### GUI

Main window: You can see your tracked stocks and their live statistics. The blue square shows the stocks live statistics. The pink square shows its name and ticker and the yellow circle
navigates to the edit window.

![alt text](https://github.com/noamgrinch/Stox/blob/master/README/Stats.jpg)

Edit window: This is the window you can edit which stocks you want to follow. The blue circle show your the search bar, the pink circle is the button to remove the stock from your list
and the yellow square is to save the changes and go back to the main window. When you search for a stock, it's box will show a green button with "+" in it instead of the red "X" button.

![alt text](https://github.com/noamgrinch/Stox/blob/master/README/edit.jpg)

