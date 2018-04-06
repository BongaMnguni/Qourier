package com.icourier.qourier;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class JobDone extends AppCompatActivity {

    String username,fullnam;
    EditText edtkey;
    Button key;
    TextView textView;
LinearLayout layout;
    Timer t ;
    TimerTask task ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_done);
        textView = (TextView) findViewById(R.id.textv);

        Bundle bundle = getIntent().getExtras();
        fullnam = bundle.getString("fullname");
        username = bundle.getString("username");

        layout = (LinearLayout)findViewById(R.id.key_container);
        layout.getBackground().setAlpha(150);

        t=new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Animation upAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
                        upAnim.reset();
                        textView.clearAnimation();
                        textView.setAnimation(upAnim);
                    }
                });
            }
        };

        t.scheduleAtFixedRate(task, 0, 3000);

        Bundle b = getIntent().getExtras();
        username = b.getString("username");
        key = (Button)findViewById(R.id.btnkey);
        edtkey = (EditText)findViewById(R.id.edtKey);
        key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtkey.getText().toString().equals("")){
                    edtkey.setError("Activation Key Required!");
                }else {
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "admin@icourier.co.za;", null));
                    //pass icourieradmin
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Claim Your Cash");
                    intent.putExtra(Intent.EXTRA_TEXT, "Activation Key :\t" + edtkey.getText().toString().trim() + "\n \n " + "username  :\t" + username);
                    startActivity(Intent.createChooser(intent, "Choose an Email client :"));
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.back, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back) {
            // pass back the value to main
            Intent i = new Intent(JobDone.this,MakeDelivery.class);
            i.putExtra("fullname",fullnam);
            i.putExtra("username",username);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
