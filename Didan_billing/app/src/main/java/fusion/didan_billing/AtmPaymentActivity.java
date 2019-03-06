/** This class displays the user id for atm
 @author fusion
 @version 1.0
 */

package fusion.didan_billing;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AtmPaymentActivity  extends AppCompatActivity {
    public static String LOG_TAG = "my_log";
    TextView tViewId, tViewAddress, tViewFio;
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm_payment);
        tViewId = findViewById(R.id.tViewId);
        tViewAddress = findViewById(R.id.tViewAddress);
        tViewFio = findViewById(R.id.tViewFio);

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

            tViewId.setText(id);
            tViewFio.setText(name +" "+ surname +" "+ patronymic);
        }*/
        sPref = getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        String idPref = sPref.getString("id", "");
        String namePref = sPref.getString("name", "");
        String surnamePref = sPref.getString("surname", "");
        String patronymicPref = sPref.getString("patronymic", "");
        tViewId.setText(idPref);
        tViewFio.setText(namePref +" "+ surnamePref +" "+ patronymicPref);
    }
}