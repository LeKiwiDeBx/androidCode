package com.example.jean.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    /*
    widgets
     */
    Button btnCalculate = null;
    Button btnRaz = null;
    EditText edtSize = null;
    EditText edtWeight = null;
    RadioGroup grpUnit = null;
    TextView txtResult = null;
    CheckBox chkFormula = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* recuperate widget */
        btnCalculate = (Button) findViewById(R.id.btnCalcul);
        btnRaz = (Button) findViewById(R.id.btnRaz);
        edtSize = (EditText) findViewById(R.id.size);
        edtWeight = (EditText) findViewById(R.id.weight);
        grpUnit = (RadioGroup) findViewById(R.id.group);
        txtResult = (TextView) findViewById(R.id.result);
        chkFormula = (CheckBox) findViewById(R.id.checkFormula);

        /* set les listener */
        btnCalculate.setOnClickListener(calculateListener);
        btnRaz.setOnClickListener(razListener);
        edtSize.addTextChangedListener(textWatcherListener);
        edtWeight.addTextChangedListener(textWatcherListener);
        chkFormula.setOnClickListener(formulaListener);

    }

    /* define listener */
    private View.OnClickListener calculateListener;

    {
      calculateListener =
          new View
              .OnClickListener() {
                @Override
                public void onClick(View v) {
                  int fact = 1;
                  float vSize = 0;
                  float vWeight = 0;

                  try {
                    vSize = Float.valueOf(edtSize.getText().toString());
                    vWeight = Float.valueOf(edtWeight.getText().toString());
                  } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this,R.string.string_numberFormatException, Toast.LENGTH_SHORT).show();
                    vSize = 0;
                    vWeight = 0;
                  }
                  if (vSize > 0 && vWeight > 0) {
                    /*calculate IMC */
                    if (grpUnit.getCheckedRadioButtonId() == R.id.radio2)
                      fact = 100;
                    float BMI = vWeight / (float) Math.pow(vSize / fact, 2);
                    String BMIvalue = getResources().getString(R.string.string_BMI_value, BMI);
                    //txtResult.setText(String.valueOf(BMI));
                    txtResult.setText(BMIvalue);
                    colorRow(BMI) ;

                  } else /*error*/ {
                    if (vSize < 1 || vWeight < 1)
                      Toast
                          .makeText(MainActivity.this,
                                    R.string.string_errorData,
                                    Toast.LENGTH_SHORT)
                          .show();
                    txtResult.setText(R.string.string_errorData);
                  }
                }
              };
    }

    private View.OnClickListener razListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            txtResult.setText(R.string.string_formula);
            edtSize.getText().clear();
            edtWeight.getText().clear();
            chkFormula.setChecked(false);
        }
    };

    private TextWatcher textWatcherListener;

    {
        textWatcherListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtResult.setText(R.string.string_obtainResult);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    private View.OnClickListener formulaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (chkFormula.isChecked())
                Toast.makeText(MainActivity.this, R.string.string_bmiComment, Toast.LENGTH_SHORT).show();
        }
    };

    private boolean colorRow(float bmi){
        boolean isOk = false ;
        TableLayout table = (TableLayout) findViewById(R.id.idTableLayout) ;
        for (int i = 0; i < table.getChildCount();i++){

            View child = table.getChildAt(i) ;
            if(child != null && child instanceof TableRow) {
                TableRow row = (TableRow) child;
                isOk = true ;
                row.setBackgroundColor(Color.WHITE);
            if (i==1 && bmi < 18.5)
                row.setBackgroundColor(Color.CYAN);
            else if (i==2 && bmi >= 18.5 && bmi <= 24.9 )
                row.setBackgroundColor(Color.GREEN);
            else if (i==3 && bmi >24.9 && bmi <= 29.9)
                row.setBackgroundColor(Color.RED);
            else if (i==4 && bmi >29.9)
                row.setBackgroundColor(Color.MAGENTA);

            }
        }
        return isOk ;
    }
}
