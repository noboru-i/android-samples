package hm.orz.chaos114.android.androidsamples;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import hm.orz.chaos114.android.androidsamples.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String[] screens = {
            "NestedScrollView",
            "Typography"
    };

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.list.setAdapter(new ArrayAdapter<>(this, R.layout.view_main_row, screens));
        binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                start(screens[position]);
            }
        });
    }

    private void start(String screenName) {
        String className = screenName + "Activity";
        try {
            Intent intent = new Intent(this, Class.forName(getPackageName() + "." + className));
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
