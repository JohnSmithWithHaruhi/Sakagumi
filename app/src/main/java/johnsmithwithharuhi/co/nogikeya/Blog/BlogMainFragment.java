package johnsmithwithharuhi.co.nogikeya.Blog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import johnsmithwithharuhi.co.nogikeya.R;
import johnsmithwithharuhi.co.nogikeya.databinding.FragmentBlogMainBinding;

public class BlogMainFragment extends Fragment {

  public BlogMainFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    FragmentBlogMainBinding mBinding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_blog_main, container, false);

    TabLayout tabLayout = mBinding.blogMainTabLayout;
    ViewPager viewPager = mBinding.blogMainViewPager;

    FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getFragmentManager()) {
      @Override public CharSequence getPageTitle(int position) {
        switch (position) {
          case 0:
            return "推しメン";
          case 1:
            return "乃木坂";
          case 2:
            return "欅坂";
          default:
            return "";
        }
      }

      @Override public Fragment getItem(int position) {
        return FragmentView.newInstance(position);
      }

      @Override public int getCount() {
        return 3;
      }
    };

    viewPager.setAdapter(fragmentPagerAdapter);
    tabLayout.setupWithViewPager(viewPager);

    return mBinding.getRoot();
  }
}
