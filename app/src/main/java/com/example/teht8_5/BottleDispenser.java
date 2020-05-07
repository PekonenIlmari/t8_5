package com.example.teht8_5;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static com.example.teht8_5.MainActivity.mAct;

public class BottleDispenser {
    private Bottle bottle;
    private float money;
    String outputFile;

    private static BottleDispenser bd = new BottleDispenser();

    private ArrayList<Bottle> inventory = new ArrayList<Bottle>();

    private ArrayList<String> receipt = new ArrayList<String>();

    public ArrayList<String> publicInv = new ArrayList<String>();

    public BottleDispenser() {
        money = 0;

        bottle = new Bottle("Pepsi Max", "Pepsi", 0.3f, 0.5f, 1.80f);
        inventory.add(bottle);

        bottle = new Bottle("Pepsi Max", "Pepsi", 0.3f, 1.5f, 2.20f);
        inventory.add(bottle);

        bottle = new Bottle("Coca-Cola Zero", "Coca-Cola", 0.3f, 0.5f, 2.00f);
        inventory.add(bottle);

        bottle = new Bottle("Coca-Cola Zero", "Coca-Cola", 0.3f, 1.5f, 2.50f);
        inventory.add(bottle);

        bottle = new Bottle("Fanta Zero", "Coca-Cola", 0.3f, 0.5f, 1.95f);
        inventory.add(bottle);

        for (int r = 0; r < inventory.size(); r++) {
            publicInv.add(inventory.get(r).getName() + " " + inventory.get(r).getSize() + " " +
                    inventory.get(r).getPrice()+"€");
        }

        publicInv.add(0, "Valitse pullo");
    }


    public static BottleDispenser getInstance() {
        return bd;
    }

    public void addMoney(int amount) {
        money += amount;

        mAct.amountView.setText("Rahaa koneessa: " + String.format("%.2f", money) + "€");

        if (amount == 0) {
            mAct.infoView.setText("Valitse lisättävä rahamäärä uudestaan!");
        } else {
            mAct.infoView.setText("Klink! Added more money!");
        }
    }

    public void buyBottle(String name, String s) {
        int x = findInvIndex(name, s);

        if (x == -1) {
            mAct.infoView.setText("Tuote on loppu");
        } else {

            float p = inventory.get(x).getPrice();
            if (money < p) {
                mAct.infoView.setText("Add money first!");
            } else {
                money -= inventory.get(x).getPrice();
                mAct.amountView.setText("Rahaa koneessa: " + String.format("%.2f", money) + "€");
                mAct.infoView.setText("KACHUNK! " + inventory.get(x).getName() + " came out of the dispenser!");
                receipt.add("Tuote: " + inventory.get(x).getName() + ", koko: " + inventory.get(x).getSize() +
                        "L, hinta: " + inventory.get(x).getPrice() + "€");
                inventory.remove(x);
            }
        }
    }

    public void returnMoney() {
        mAct.infoView.setText("Klink klink. Money came out! You got " + String.format("%.2f", money) + "€ back");
        money = 0;
        mAct.amountView.setText("Rahaa koneessa: " + String.format("%.2f", money) + "€");
    }

    private int findInvIndex(String name, String size) {
        int value = -1;
        for (int i = 0; i < inventory.size(); i++) {
            if (name.equals(inventory.get(i).getName().trim()) && size.equals(String.valueOf(inventory.get(i).getSize()).trim())) {
                value = i;
            }
        }

        return value;
    }

    public void writeReceipt(Context a) {
        String s;
        try {
            outputFile = "receipt.txt";
            OutputStreamWriter osw = new OutputStreamWriter(a.openFileOutput(outputFile, Context.MODE_PRIVATE));

            osw.write("***OSTOKUITTI***\n\n");

            for (int i = 0; i < receipt.size(); i++) {
                s = receipt.get(i) + "\n";

                osw.write(s);
            }

            mAct.infoView.setText("Kuitti kirjoitettu!");
            osw.close();

            for (int p = 0; p < receipt.size(); p++) {
                receipt.remove(p);
            }

        } catch(FileNotFoundException e) {
            Log.e("FileNotFoundException", "Lukuvirhe");
            mAct.infoView.setText("VIRHE!");
        } catch (IOException e) {
            Log.e("IOException", "Lukuvirhe");
            mAct.infoView.setText("VIRHE!");
        }
    }
}
