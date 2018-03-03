package civil.ultimate_bearing_capacity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;
import android.view.View.OnClickListener;
import android.util.Log;



public class MainActivity extends AppCompatActivity {

    public double sC,sQ,sY,dQ,dY,W;
    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();

    }

    public void addListenerOnButton() {
        final Button submit = (Button) findViewById(R.id.button1);
        final EditText sptValueOfN = (EditText) findViewById(R.id.editText);
        final EditText depthOfFootingDoor = (EditText) findViewById(R.id.editText2);
        final EditText widthOfFootingDoor = (EditText) findViewById(R.id.editText3);
        final EditText lengthOfFootingDoor = (EditText) findViewById(R.id.editText5);
        final EditText cohesion = (EditText) findViewById(R.id.editText6);
        final EditText angleOfInclenation = (EditText) findViewById(R.id.editText7);
        final Spinner footingType = (Spinner) findViewById(R.id.spinner1);
        final EditText depthOfWaterTable = (EditText) findViewById(R.id.editText9);
        final EditText unitWeightGAMA = (EditText) findViewById(R.id.editText11);
        final EditText result = (EditText) findViewById(R.id.editText10);


        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                   Log.d("sptValueOfN", sptValueOfN.getText().toString());
                   Log.d("depthOfFootingDoor", depthOfFootingDoor.getText().toString());
                   Log.d("widthOfFootingDoor", widthOfFootingDoor.getText().toString());
                   Log.d("lengthOfFootingDoor", lengthOfFootingDoor.getText().toString());
                   Log.d("cohesion", cohesion.getText().toString());
                   Log.d("angleOfInclenation", angleOfInclenation.getText().toString());
                   Log.d("footingType", footingType.getSelectedItem().toString());
                   Log.d("depthOfWaterTable", depthOfWaterTable.getText().toString());
                   Log.d("unitWeightGAMA", unitWeightGAMA.getText().toString());


                if(footingType.getSelectedItem().toString().equals("Strip")) {
                    sC = 1.0;
                    sQ = 1.0;
                    sY = 1.0;
                }
                if(footingType.getSelectedItem().toString().equals("Regular")) {
                    sC = 1.0 + (0.2 * (Double.parseDouble(widthOfFootingDoor.getText().toString()))/(Double.parseDouble(lengthOfFootingDoor.getText().toString())));
                    sQ = 1.0 + (0.2 * (Double.parseDouble(widthOfFootingDoor.getText().toString()))/(Double.parseDouble(lengthOfFootingDoor.getText().toString())));
                    sY = 1.0 + (0.2 * (Double.parseDouble(widthOfFootingDoor.getText().toString()))/(Double.parseDouble(lengthOfFootingDoor.getText().toString())));
                }
                if(footingType.getSelectedItem().toString().equals("Square")) {
                    sC = 1.3;
                    sQ = 1.2;
                    sY = 0.8;
                }
                if(footingType.getSelectedItem().toString().equals("Circle")) {
                    sC = 1.3;
                    sQ = 1.2;
                    sY = 0.6;
                }
                Log.d("sCout",  Double.toString(sC));
                Log.d("sQout",  Double.toString(sQ));
                Log.d("sYout",  Double.toString(sY));

                // what is Y(gama add unit weight) in n1 forumala
                double n1 = (0.77) * (Math.log(2000/(Double.parseDouble(depthOfFootingDoor.getText().toString())) * (Double.parseDouble(unitWeightGAMA.getText().toString())) )) * (Double.parseDouble(sptValueOfN.getText().toString()));
                Log.d("n1",  Double.toString(n1));

                double n2 = 15 + 0.5 * (n1 - 15);
                Log.d("n2",  Double.toString(n2));

                double fai = (-0.001 * Math.pow((Double.parseDouble(sptValueOfN.getText().toString())),2)) + (0.345 * (Double.parseDouble(sptValueOfN.getText().toString()))) + 26.71;
                Log.d("fai",  Double.toString(fai));

                double nC = (0.021 * Math.pow(fai,2)) + (0.049 * fai) + 5.527;
                Log.d("nC",  Double.toString(nC));

                double nQ = (0.017 * Math.pow(fai,2)) - (0.077 * fai) + 1.360;
                Log.d("nQ",  Double.toString(nQ));

                double nY = (0.001 * Math.pow(fai,3)) - (0.015 * Math.pow(fai,2)) + (0.187 * fai) - 0.137;
                Log.d("nY",  Double.toString(nY));

                double iCoRiQ = Math.pow(1 - (Double.parseDouble(angleOfInclenation.getText().toString()))/90, 2);
                Log.d("iCoRiQ",  Double.toString(iCoRiQ));

                double iY = Math.pow(1 - (Double.parseDouble(angleOfInclenation.getText().toString()))/fai, 2);
                Log.d("iY",  Double.toString(iY));

                double dC = 1 + (0.2 *  ((Double.parseDouble(depthOfFootingDoor.getText().toString()))/(Double.parseDouble(widthOfFootingDoor.getText().toString()))) * (Math.sqrt(Math.tan(Math.toRadians(45 + fai/2)) * (Math.tan(Math.toRadians(45 + fai/2))))));
                Log.d("dC",  Double.toString(dC));

                if(fai < 10) {
                    dQ = dY = 1;
                    Log.d("dqLessThen10",  Double.toString(dC));
                } else {
                    dQ = dY = 1 + (0.1 *  ((Double.parseDouble(depthOfFootingDoor.getText().toString()))/(Double.parseDouble(widthOfFootingDoor.getText().toString()))) * (Math.sqrt(Math.tan(Math.toRadians(45 + fai/2)) * (Math.tan(Math.toRadians(45 + fai/2))))));
                    Log.d("dqGraterThen10",  Double.toString(dC));
                }
                if((Double.parseDouble(depthOfWaterTable.getText().toString())) >  (10 + (Double.parseDouble(widthOfFootingDoor.getText().toString())))) {
                    W = 1;
                } else {
                    W = 0.5 * (1 + ((Double.parseDouble(depthOfWaterTable.getText().toString())) - (Double.parseDouble(depthOfFootingDoor.getText().toString())))/(Double.parseDouble(widthOfFootingDoor.getText().toString())));
                }
                Log.d("W",  Double.toString(W));

             double finalResult = ((Double.parseDouble(cohesion.getText().toString())) * nC * sC * dC * iCoRiQ) + ((Double.parseDouble(unitWeightGAMA.getText().toString())) * (Double.parseDouble(depthOfFootingDoor.getText().toString())) * (nQ - 1) * sQ * dQ * iCoRiQ) + (0.05 *(Double.parseDouble(widthOfFootingDoor.getText().toString())) * nY * sY * dY * iY * W);
                String resultString =  Double.toString(finalResult).substring(0, 4);
                result.setText(resultString);
                Log.d("Final result",  resultString);
            }
        });

    }
}
