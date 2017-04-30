package johnsmithwithharuhi.co.sakagumi.Presentation.Presenter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import johnsmithwithharuhi.co.sakagumi.R;
import johnsmithwithharuhi.co.sakagumi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    BottomNavigationView bottomNavigationView = mBinding.mainBottomNavigation;
    bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
      switch (item.getItemId()) {
        case R.id.action_blog:
        case R.id.action_rss:
        case R.id.action_event:
          break;
      }
      return true;
    });

    getSupportFragmentManager().beginTransaction()
        .add(R.id.main_content, new BlogMainFragment())
        .commit();
  }
}
