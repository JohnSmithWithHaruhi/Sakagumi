package johnsmithwithharuhi.co.nogikeya.MainActivity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import johnsmithwithharuhi.co.nogikeya.News.NewsFragment;
import johnsmithwithharuhi.co.nogikeya.R;
import johnsmithwithharuhi.co.nogikeya.databinding.ActivityMainBinding;

public class MainActivityView extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        ViewPager viewPager = mBinding.mainViewPager;
        TabLayout tabLayout = mBinding.mainTabLayout;

        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new NewsFragment();
            }

            @Override
            public int getCount() {
                return 5;
            }
        };

        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_fiber_new_24_b);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_rss_feed_24_b);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_assignment_ind_24_b);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_event_24_b);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_favorite_border_24_b);
    }
}
