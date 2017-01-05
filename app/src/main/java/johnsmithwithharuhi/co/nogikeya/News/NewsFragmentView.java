package johnsmithwithharuhi.co.nogikeya.News;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import johnsmithwithharuhi.co.nogikeya.R;
import johnsmithwithharuhi.co.nogikeya.databinding.FragmentNewsBinding;

public class NewsFragmentView extends Fragment {

  private FragmentNewsBinding mBinding;

  public NewsFragmentView() {
  }

  public static NewsFragmentView newInstance() {
    Bundle args = new Bundle();
    NewsFragmentView fragment = new NewsFragmentView();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false);
    RecyclerView recyclerView = mBinding.newsRecyclerView;
    recyclerView.setAdapter(new NewsRecyclerAdapter());

    return mBinding.getRoot();
  }
}
