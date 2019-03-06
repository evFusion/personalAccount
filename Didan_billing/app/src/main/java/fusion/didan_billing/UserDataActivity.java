/** This class manage to display all user information
 @author fusion
 @version 1.0
 */

package fusion.didan_billing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

import static fusion.didan_billing.R.id.balanceInfoNumber;
import static fusion.didan_billing.R.id.customerAccount;
import static fusion.didan_billing.R.id.customerAddress;
import static fusion.didan_billing.R.id.customerLogin;
import static fusion.didan_billing.R.id.customerTariff;

public class UserDataActivity extends AppCompatActivity {

    public static String LOG_TAG = "my_log";

    TextView tvFio, tvAcount, tvAddress, tvTariff, tvdeposit;
    Button btnPay, btnCredit;
    Intent iExp;
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdata);

        tvdeposit = findViewById(balanceInfoNumber);
        tvAddress = findViewById(customerAddress);
        tvAcount = findViewById(customerAccount);
        tvFio = findViewById(customerLogin);
        tvTariff = findViewById(customerTariff);
        btnPay = findViewById(R.id.btnPay);
        btnCredit = findViewById(R.id.btnCredit);
        DecimalFormat df = new DecimalFormat("0.00");

        sPref = getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        String uidPref = sPref.getString("uid", "");
        String idPref = sPref.getString("id", "");
        String passwordPref = sPref.getString("password", "");
        String fioPref = sPref.getString("fio", "");
        String streetPref = sPref.getString("address_street", "");
        String buildPref = sPref.getString("address_build", "");
        String flatPref = sPref.getString("address_flat", "");
        float depositPref = Float.valueOf(sPref.getString("deposit", ""));
        String creditPref = sPref.getString("credit", "");
        String credit_datePref = sPref.getString("credit_date", "");
        String tarifPref = sPref.getString("tarif", "");
        int day_feePref = sPref.getInt("day_fee", 1);


        tvdeposit.setText(df.format(depositPref) + " \u20BD");//₽
        //tvdeposit.setText(depositPref + " \u20BD");//₽
        tvAddress.setText(streetPref + " " + buildPref + ", " + flatPref);
        tvAcount.setText("Договор: " + uidPref);
        tvFio.setText(fioPref);
        tvTariff.setText(tarifPref + "\n" +  " (" + day_feePref*30 + " руб. / мес.)" + "\n" + " (" + day_feePref +  " руб. / день)");

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iExp = new Intent(new Intent(UserDataActivity.this, PaymentActivity.class));

                /*Bundle arguments = getIntent().getExtras();

                if(arguments != null) {
                    String id = arguments.get("id").toString();
                    String name = arguments.get("name").toString();
                    String surname = arguments.get("surname").toString();
                    String patronymic = arguments.get("patronymic").toString();
                    Log.d(LOG_TAG, id);
                    Log.d(LOG_TAG, name);
                    Log.d(LOG_TAG, surname);
                    Log.d(LOG_TAG, patronymic);
                    iExp.putExtra("id", id);
                    iExp.putExtra("name", name);
                    iExp.putExtra("surname", surname);
                    iExp.putExtra("patronymic", patronymic);
                }*/
                startActivity(iExp);
            }
        });
        btnCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}