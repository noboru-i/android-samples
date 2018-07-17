package hm.orz.chaos114.android.androidsamples;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import hm.orz.chaos114.android.androidsamples.bottomnavigation.BlankFragment;
import hm.orz.chaos114.android.androidsamples.databinding.ActivityBottomNavigationBinding;

public class BottomNavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBottomNavigationBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_bottom_navigation);

        binding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        setFragment("search");
                        return true;
                    case R.id.action_settings:
                        setFragment("settings");
                        return true;
                    case R.id.action_navigation:
                        setFragment("navigation");
                        return true;
                }
                return false;
            }
        });

        setFragment("search");
    }

    private void setFragment(String text) {
        Fragment fragment = BlankFragment.newInstance(text);

        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(text);
        if (currentFragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment, text)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else {
            // TODO: scroll to top
        }
    }
}
