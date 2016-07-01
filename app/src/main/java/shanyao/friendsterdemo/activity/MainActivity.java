package shanyao.friendsterdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import shanyao.friendsterdemo.adapter.FriendsterAdapter;
import shanyao.friendsterdemo.R;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.see_you_person)
    TextView seeYouPerson;
    @Bind(R.id.see_you_counts)
    TextView seeYouCounts;
    @Bind(R.id.dynamic_message)
    LinearLayout dynamicMessage;
    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.healthy_dynamic_ll)
    LinearLayout healthyDynamicLl;
    @Bind(R.id.to_top)
    TextView toTop;
    private FriendsterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0;i<10;i++){
            list.add(i+"");
        }
        adapter = new FriendsterAdapter(list, MainActivity.this);
        listView.setAdapter(adapter);
    }
}
