package johnsmithwithharuhi.co.nogikeya.MainActivity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import johnsmithwithharuhi.co.nogikeya.Blog.FragmentView;
import johnsmithwithharuhi.co.nogikeya.News.NewsFragmentView;
import johnsmithwithharuhi.co.nogikeya.R;
import johnsmithwithharuhi.co.nogikeya.databinding.ActivityMainBinding;

public class MainActivityView extends AppCompatActivity {

  private Fragment mCurrentFragment = new FragmentView();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    showFragment("Blog", mCurrentFragment);
    BottomNavigationView bottomNavigationView = mBinding.mainBottomNavigation;
    bottomNavigationView.setOnNavigationItemSelectedListener(
        new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (mCurrentFragment != null && mCurrentFragment.getTag().equals(item.getTitle())) {
              return false;
            }
            switch (item.getItemId()) {
              case R.id.action_blog:
                showFragment(item.getTitle().toString(), new FragmentView());
                break;
              case R.id.action_rss:
              case R.id.action_event:
              case R.id.action_member:
              case R.id.action_favorite:
                showFragment(item.getTitle().toString(), new NewsFragmentView());
                break;
            }
            return true;
          }
        });
  }

  private void showFragment(String tag, Fragment newFragment) {
    Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
    if (fragment != null) {
      getSupportFragmentManager().beginTransaction().hide(mCurrentFragment).commit();
      getSupportFragmentManager().beginTransaction().show(fragment).commit();
      mCurrentFragment = fragment;
    } else {
      mCurrentFragment = newFragment;
      getSupportFragmentManager().beginTransaction()
          .add(R.id.main_content, newFragment, tag)
          .commit();
    }
  }
}
