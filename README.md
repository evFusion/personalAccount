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

![Login screen](https://github.com/evFusion/personalAccount/blob/master/login-screen.png)

First activity - entry point and authentication center. 
Using customer login and password you will be redirect on activity with user data. 
Getting user data and compare login/pass pair do two php scripts (db_conn.php and get_info.php).
Entered login/pass send and compare with data in remote MySQL DB.

![User data](https://github.com/evFusion/personalAccount/blob/master/user-data-screen.png)

On this way there is a possibility go to deposit funds or to activate the service on credit (this functionality in feature plans).

![Payment methods](https://github.com/evFusion/personalAccount/blob/master/payment-methods.png)

From every activity you can go back with data saving.

### in process rebuilding:
- activate the service on credit;
- store data on local DB (now in shared preferences);
- deposit funds with special user voucher.
