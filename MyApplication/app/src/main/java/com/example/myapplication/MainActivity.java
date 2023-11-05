package com.example.myapplication;

import static android.provider.BaseColumns._ID;
import static com.example.myapplication.Constants.BMI;
import static com.example.myapplication.Constants.Status;
import static com.example.myapplication.Constants.TABLE_NAME;
import static com.example.myapplication.Constants.date;
import static com.example.myapplication.Constants.weight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    DBHelper dbh;
    EditText eWeight;
    Button btnViewAllData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText edt1  = findViewById(R.id.height);
        eWeight= findViewById(R.id.weight);
        btnViewAllData=findViewById(R.id.btnView);
        dbh = new DBHelper(this);


        edt1.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        EditText edt2  = findViewById(R.id.weight);
        edt2.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
    }


    private Cursor getEvents(){
        String[] FROM= {_ID, date, weight,  BMI, Status};
        String ORDER_BY = date + " DESC";
        SQLiteDatabase db = dbh.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
        return cursor;
    }

    private long getLastId(){
        long id =0;
        SQLiteDatabase db = dbh.getWritableDatabase();
        String[] FROM = {_ID};
        String ORDER_BY= date + " DESC";
        Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY, "1");
        while (cursor.moveToNext()){
            id = cursor.getLong(0);
        }
        return id;
    }



    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        float fontsize = newConfig.fontScale*30;
        final TextView txt0 = findViewById(R.id.TV0);
        final TextView txt1 = findViewById(R.id.TV1);
        final TextView txt2 = findViewById(R.id.TV2);
        final TextView BMI = findViewById(R.id.BMI);
        final TextView Status = findViewById(R.id.Status);
        final TextView button = findViewById(R.id.submit);
        txt0.setTextSize(fontsize);
        txt1.setTextSize(fontsize);
        txt2.setTextSize(fontsize);
        BMI.setTextSize(fontsize);
        Status.setTextSize(fontsize);
        button.setTextSize(fontsize-5);
    }



    public void clickToCalculate(View view){
        final EditText In_height = findViewById(R.id.height);
        final EditText In_weight = findViewById(R.id.weight);
        final TextView BMI_txt = findViewById(R.id.BMI);
        final TextView Status_txt = findViewById(R.id.Status);
        float Height = Float.parseFloat(In_height.getText().toString())/100;
        float Weight = Float.parseFloat(In_weight.getText().toString());
        float bmiValue = (float) (Weight / (Height * Height));
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        String resultBmi = ""+formatter.format(bmiValue);
        BMI_txt.setText("BMI = "+resultBmi);




        if (bmiValue < 16) {
            Status_txt.setText(R.string.severethinness);
            Status_txt.setTextColor(getColor(R.color.severethinness));
        }else if (bmiValue > 16 && bmiValue<17) {
            Status_txt.setText(R.string.ModerateThinness);
            Status_txt.setTextColor(getColor(R.color.ModerateThinness));
        }else if (bmiValue > 17 && bmiValue<18.5) {
            Status_txt.setText(R.string.MildThinness);
            Status_txt.setTextColor(getColor(R.color.MildThinness));
        } else if (bmiValue >18.5&&bmiValue<25) {
            Status_txt.setText(R.string.normal);
            Status_txt.setTextColor(getColor(R.color.normal));
        } else if (bmiValue>25&&bmiValue < 30) {
            Status_txt.setTextColor(getColor(R.color.overweight));
            Status_txt.setText(R.string.overweight);
        } else if(bmiValue>30&&bmiValue<35) {
            Status_txt.setText(R.string.obese);
            Status_txt.setTextColor(getColor(R.color.obese));
        }  else if(bmiValue>35&&bmiValue<40) {
            Status_txt.setText(R.string.obese2);
            Status_txt.setTextColor(getColor(R.color.obese2));
        }  else if(bmiValue>40) {
            Status_txt.setText(R.string.obese3);
            Status_txt.setTextColor(getColor(R.color.obese3));
        }


    }
}
class DecimalDigitsInputFilter implements InputFilter {
    private Pattern mPattern;
    DecimalDigitsInputFilter(int digits, int digitsAfterZero) {
        mPattern = Pattern.compile("[0-9]{0," + (digits - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) +
                "})?)||(\\.)?");
    }
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher matcher = mPattern.matcher(dest);
        if (!matcher.matches())
            return "";
        return null;
    }
}
