package johnsmithwithharuhi.co.nogikeya.MainActivity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import johnsmithwithharuhi.co.nogikeya.R;
import johnsmithwithharuhi.co.nogikeya.databinding.ActivityMainBinding;

public class MainActivityView extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initTabView(mBinding.mainToolbar);
    }

    private void initTabView(TabLayout tabLayout) {
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_person_outline_24_b));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_person_outline_24_b));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_person_outline_24_b));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_person_outline_24_b));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_person_outline_24_b));
    }
}
