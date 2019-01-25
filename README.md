# personalAccount
Mobile personal account for internet-provider users
***
***This app is a personal account for every user any internet provider. With this app you can:***
- check your balance;
- deposit funds in several ways;
- find on map main office address.
***
### Key moments

All work with php scripts which are take data from MySQL DB. Information returns in JSON and after that this data parsing in Java.
***
### How It Works

First activity - entry point and authentication center. 
Using customer login and password you will be redirect on activity with user data. 
On this way there is a possibility go to deposit funds or to activate the service on credit (this functionality in feature plans).
From every activity you can go back with data saving.

### in process rebuilding:
- activate the service on credit;
- store data on local DB (now in shared preferences);
- deposit funds with special user voucher.
