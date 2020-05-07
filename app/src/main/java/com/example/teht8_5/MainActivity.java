package com.example.teht8_5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView infoView, moneyView, amountView;
    Button moneyButton, changeButton, buyButton, receiptButton;
    SeekBar amountBar;
    Spinner productSpinner;

    Context context = null;

    String botName, botSize;

    BottleDispenser dispenser = BottleDispenser.getInstance();

    static MainActivity mAct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        mAct = this;
        infoView = (TextView) findViewById(R.id.infoView);
        moneyView = (TextView) findViewById(R.id.moneyView);
        amountView = (TextView) findViewById(R.id.amountView);
        moneyButton = (Button) findViewById(R.id.moneyButton);
        changeButton = (Button) findViewById(R.id.changeButton);
        buyButton = (Button) findViewById(R.id.buyButton);
        receiptButton = (Button) findViewById(R.id.receiptButton);
        amountBar = (SeekBar) findViewById(R.id.amountBar);
        productSpinner = (Spinner) findViewById(R.id.productSpinner);

        amountBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue;

            //@Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressValue = progress;
                moneyView.setText("Lisättävä rahamäärä: " + progressValue + "€");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                moneyView.setText("Lisättävä rahamäärä: " + progressValue + "€");
            }
        });

        listBottles();
    }

    public void listBottles() {
        ArrayAdapter<String> bottles = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dispenser.publicInv);

        bottles.setDropDownViewResource(android.R.layout.simple_spinner_item);

        productSpinner.setAdapter(bottles);

        productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Valitse pullo")) {

                } else {
                    String choice = parent.getItemAtPosition(position).toString();
                    String[] choices = choice.split(" ");

                    botName = choices[0].trim() + " " + choices[1].trim();
                    botSize = choices[2].trim();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void addMoney(View v) {
        int addableAmount = amountBar.getProgress();
        dispenser.addMoney(addableAmount);
        amountBar.setProgress(0);
        moneyView.setText("Säädä liukurilla, paljonko rahaa haluat lisätä koneeseen");
    }

    public void getChange(View v) {
        dispenser.returnMoney();
    }

    public void buyBottle(View v) {
        dispenser.buyBottle(botName, botSize);
    }

    public void printReceipt(View v) {
        dispenser.writeReceipt(context);
    }
}
