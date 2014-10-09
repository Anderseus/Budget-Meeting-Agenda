package com.example.budgetmeetingagenda;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class EditInfo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        Button button_01 = (Button)findViewById(R.id.button);

        final EditText editText_01 = (EditText)findViewById(R.id.editText);
        final EditText editText_02 = (EditText)findViewById(R.id.editText2);
        final EditText editText_03 = (EditText)findViewById(R.id.editText3);

        Button button_02 = (Button)findViewById(R.id.button2);
        final TextView textView_01 = (TextView)findViewById(R.id.textView2);
        Button button_03 = (Button)findViewById(R.id.button3);

        final AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("spoderman");
        alert.setMessage("Fill in all the required info");
        alert.setIcon(R.drawable.spoderman);
        alert.setButton("OK" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();

        try{
            FileInputStream fIn = openFileInput("userData.txt");
            InputStreamReader isr = new InputStreamReader(fIn);
                        /* Prepare a char-Array that will
                        * hold the chars we read back in. */
            char[] inputBuffer = new char[512];
            for (int i =0; i<512; i++){
                inputBuffer[i]='\n';
            }

            // Fill the Buffer with data from the file
            isr.read(inputBuffer);

            // Transform the chars to a String
            String readString = new String(inputBuffer);
            //Log.i("", readString);

            String[] split = readString.split("\n");

            editText_01.setText(split[0]);
            editText_02.setText(split[1]);
            editText_03.setText(split[2]);
            textView_01.setText("Arm Length: not measured yet");
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

        button_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditInfo.this, Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        button_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ArmLength.class));
                textView_01.setText("Arm Length: not measured yet");
            }
        });

        button_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("", "here thy asdf " + getBaseContext().getFileStreamPath(
                        "userData.txt").exists());
                try {
                    // catches IOException below
                    Log.i("", "here thy  " + getBaseContext().getFileStreamPath(
                            "userData.txt").exists());
                    File dirFiles = getApplicationContext().getFilesDir();
                    for (String strFile : dirFiles.list())
                    {
                        Log.i("", strFile);

                    }
                    FileOutputStream fOut = openFileOutput("userData.txt",
                            MODE_WORLD_READABLE);
                    OutputStreamWriter osw = new OutputStreamWriter(fOut);

                    // Write the string to the file
                    String name = editText_01.getText().toString();
                    String age = editText_02.getText().toString();
                    String height = editText_03.getText().toString();
                    String armLength = (String)textView_01.getText();
                    osw.write(name + "\n");
                    osw.write(age + "\n");
                    osw.write(height + "\n");
                    osw.write(armLength + "\n");
                    osw.flush();
                    osw.close();

                    //Reading the file back...

                    /* We have to use the openFileInput()-method
                    * the ActivityContext provides.
                    * Again for security reasons with
                    * openFileInput(...) */
                }
                catch (IOException ioe) {
                    ioe.printStackTrace();
                }

                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
