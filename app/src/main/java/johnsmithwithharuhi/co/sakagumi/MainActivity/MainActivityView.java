package johnsmithwithharuhi.co.sakagumi.MainActivity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import johnsmithwithharuhi.co.sakagumi.Blog.BlogMainFragment;
import johnsmithwithharuhi.co.sakagumi.R;
import johnsmithwithharuhi.co.sakagumi.databinding.ActivityMainBinding;

public class MainActivityView extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    getSupportFragmentManager().beginTransaction()
        .add(R.id.main_content, new BlogMainFragment())
        .commit();
    BottomNavigationView bottomNavigationView = mBinding.mainBottomNavigation;
    bottomNavigationView.setOnNavigationItemSelectedListener(
        new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
              case R.id.action_blog:
              case R.id.action_rss:
              case R.id.action_event:
              case R.id.action_member:
              case R.id.action_favorite:
                break;
            }
            return true;
          }
        });
  }
}
