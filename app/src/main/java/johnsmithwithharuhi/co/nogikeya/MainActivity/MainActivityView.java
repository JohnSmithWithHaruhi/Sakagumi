package johnsmithwithharuhi.co.nogikeya.MainActivity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import johnsmithwithharuhi.co.nogikeya.Blog.FragmentView;
import johnsmithwithharuhi.co.nogikeya.R;
import johnsmithwithharuhi.co.nogikeya.databinding.ActivityMainBinding;

public class MainActivityView extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    BottomNavigationView bottomNavigationView = mBinding.mainBottomNavigation;
    bottomNavigationView.setOnNavigationItemSelectedListener(
        new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
              case R.id.action_blog:
                getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_content, new FragmentView(), null)
                    .addToBackStack(null)
                    .commit();
                break;
              case R.id.action_event:
              case R.id.action_favorite:
            }
            return true;
          }
        });
  }
}
