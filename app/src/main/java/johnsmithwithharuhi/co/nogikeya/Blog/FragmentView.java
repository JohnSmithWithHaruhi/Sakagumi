package johnsmithwithharuhi.co.nogikeya.Blog;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import johnsmithwithharuhi.co.nogikeya.Constant;
import johnsmithwithharuhi.co.nogikeya.R;
import johnsmithwithharuhi.co.nogikeya.databinding.FragmentBlogBinding;

public class FragmentView extends Fragment
    implements ViewModel.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

  private String url = "/mob/news/diarKiji.php?site=k46o&ima=0000&page=0&rw=25&cd=member";
  private JSoupHelper mJSoupHelper;
  private CompositeDisposable mCompositeDisposable;

  private SwipeRefreshLayout mSwipeRefreshLayout;
  private RecyclerView mRecyclerView;
  private ListAdapter mListAdapter;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mJSoupHelper = new JSoupHelper();
    mCompositeDisposable = new CompositeDisposable();
    mListAdapter = new ListAdapter();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    FragmentBlogBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_blog, container, false);

    mSwipeRefreshLayout = binding.blogSwipeRefreshLayout;
    mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_purple,
        android.R.color.holo_green_light);
    mSwipeRefreshLayout.setOnRefreshListener(this);

    mRecyclerView = binding.blogRecyclerView;
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    mRecyclerView.addItemDecoration(
        new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mRecyclerView.setAdapter(mListAdapter);

    loadBlogList();
    return binding.getRoot();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    mCompositeDisposable.dispose();
  }

  private void loadBlogList() {
    mCompositeDisposable.add(Observable.create(new ObservableOnSubscribe<List<ViewModel>>() {
      @Override public void subscribe(ObservableEmitter<List<ViewModel>> e) throws Exception {
        e.onNext(mJSoupHelper.getViewModelList(Constant.K_URL + url));
        e.onComplete();
      }
    })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<ViewModel>>() {
          @Override public void accept(List<ViewModel> viewModels) throws Exception {
            mListAdapter.setViewModelList(viewModels, FragmentView.this);
            mListAdapter.notifyDataSetChanged();
            if (mSwipeRefreshLayout.isRefreshing()) {
              mSwipeRefreshLayout.setRefreshing(false);
            }
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            Log.d("TAG", "test: " + throwable.getMessage());
          }
        }));
  }

  @Override public void onItemClick(String url) {
    new CustomTabsIntent.Builder().setShowTitle(true)
        .enableUrlBarHiding()
        .addDefaultShareMenuItem()
        .setStartAnimations(getContext(), android.R.anim.slide_in_left,
            android.R.anim.slide_out_right)
        .setExitAnimations(getContext(), android.R.anim.slide_in_left,
            android.R.anim.slide_out_right)
        .build()
        .launchUrl(getContext(), Uri.parse(url));
  }

  @Override public void onRefresh() {
    mCompositeDisposable.clear();
    loadBlogList();
  }
}
