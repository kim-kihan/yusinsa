package com.example.ygm.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.ygm.DB.DB;
import com.example.ygm.Home.EventItem;
import com.example.ygm.MainActivity;
import com.example.ygm.R;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {

    ArrayList<EventItem> item;

    ImageView ivBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Activity activity = this;

        TextView title = findViewById(R.id.textView_title_activity_event);

        ivBody = findViewById(R.id.imageView_event_body);

        item = new ArrayList<>();
        DB task = new DB(new DB.OnResultListener() {
            @Override
            public void onComplete(boolean result) {
                if (!result) {
                    title.setText(item.get(0).getTitle());
                    Glide.with(activity).load(item.get(0).getBody()).override(Target.SIZE_ORIGINAL).into(ivBody);
                }
            }
        }, this, R.id.fragment_event, item);
        task.execute("http://15.165.152.15//ShowEvent.php", getIntent().getStringExtra("eventPK"));
    }
}