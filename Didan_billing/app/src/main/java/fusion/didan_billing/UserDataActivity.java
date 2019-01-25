package fusion.didan_billing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class UserData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdata);
        /*TextView textView = new TextView(this);
        textView.setTextSize(20);
        textView.setPadding(16, 16, 16, 16);

        Bundle arguments = getIntent().getExtras();

        if(arguments != null){
            String name = arguments.get("name").toString();
            //String login = arguments.getString("login");
            String balance = arguments.getString("balance");
            String surname = arguments.getString("surname");
            String patronymic = arguments.getString("patronymic");
            String tarif = arguments.getString("tarif");
            textView.setText("Имя: " + name +
                             "\nФамилия: " + surname +
                             "\nОтчество: " + patronymic+
                             "\nТариф: " + tarif +
                             "\nБаланс: " + balance);
        }

        setContentView(textView);*/
    }
}