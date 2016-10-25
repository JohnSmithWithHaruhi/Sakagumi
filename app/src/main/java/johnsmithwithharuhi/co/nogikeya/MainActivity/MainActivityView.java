package johnsmithwithharuhi.co.nogikeya.MainActivity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

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
        List<String> stringList = Arrays.asList("News", "Blog", "Info", "Ticket", "Set");
        for (int i = 0; i < stringList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(stringList.get(i)));
        }
    }
}
