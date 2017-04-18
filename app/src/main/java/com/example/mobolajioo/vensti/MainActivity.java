package com.example.mobolajioo.vensti;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import static com.example.mobolajioo.vensti.R.id.ms_pdf_1;
import static com.example.mobolajioo.vensti.R.id.ms_pdf_2;
import static com.example.mobolajioo.vensti.R.id.rs_pdf_1;
import static com.example.mobolajioo.vensti.R.id.ws_pdf_1;
import static com.example.mobolajioo.vensti.R.string.ms_pdf_1_link;
import static com.example.mobolajioo.vensti.R.string.ms_pdf_2_link;
import static com.example.mobolajioo.vensti.R.string.ms_vid_1_link;
import static com.example.mobolajioo.vensti.R.string.rs_pdf_1_link;
import static com.example.mobolajioo.vensti.R.string.rs_vid_1_link;
import static com.example.mobolajioo.vensti.R.string.ws_pdf_1_link;
import static com.example.mobolajioo.vensti.R.string.ws_vid_1_link;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_actionbar_toolbar_2);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //back button pressed
            }
        });

        /*
       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        //Find button "vensti_button"
        ImageButton vb = (ImageButton)this.findViewById(R.id.vensti_button);
        vb.setOnClickListener(new View.OnClickListener()
        {
            /** Called when the user touches the button */
            public void onClick(View view) {
                // Do something in response to button click
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.vensti_music);
                mp.start();
            }
        });

    // Example of a call to a native method
    //TextView tv = (TextView) findViewById(R.id.sample_text);
    //tv.setText(stringFromJNI());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vensti_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        //Log.d("SELECTED", "itemID has been selected");

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */


        switch (id) {
            case ws_pdf_1:
                handleViewingStrategies(getResources().getString(ws_pdf_1_link));
                //show_strategies_pdf(getResources().getString(R.string.ws_pdf_1));
                return true;
            case rs_pdf_1:
                handleViewingStrategies(getResources().getString(rs_pdf_1_link));
                //show_strategies_pdf(getResources().getString(rs_pdf_1));
                return true;
            case ms_pdf_1:
                handleViewingStrategies(getResources().getString(ms_pdf_1_link));
                //show_strategies_pdf(getResources().getString(ms_pdf_1));
                return true;
            case ms_pdf_2:
                handleViewingStrategies(getResources().getString(ms_pdf_2_link));
                //show_strategies_pdf(getResources().getString(ms_pdf_1));
                return true;
            case R.id.ws_vid_1:
                handleViewingStrategies(getResources().getString(ws_vid_1_link));
                //show_strategies_video();
                return true;
            case R.id.rs_vid_1:
                handleViewingStrategies(getResources().getString(rs_vid_1_link));
                //show_strategies_video();
                return true;
            case R.id.ms_vid_1:
                handleViewingStrategies(getResources().getString(ms_vid_1_link));
                //show_strategies_video();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    //handle clicking of writing strategies pdf in options menu
    public void show_strategies_pdf(String pdfURL)
    {
        //HandlePDF hPDF = new HandlePDF();
        //hPDF.showPDFUrl(getApplicationContext(),pdfURL,this);

        new HandlePDF().showPDFUrl(getApplicationContext(),pdfURL,this);
    }

    public void handleViewingStrategies (String strategiesFormat)
    {

        ViewVenstiStrategies vvs = new ViewVenstiStrategies(getApplicationContext(),strategiesFormat,this);

    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }


}
