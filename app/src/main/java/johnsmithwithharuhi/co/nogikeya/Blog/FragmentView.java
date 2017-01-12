package johnsmithwithharuhi.co.nogikeya.Blog;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

  private static final String MEMBER_ID_KEY = "memberIdKey";

  private String url = "/mob/news/diarKiji.php?site=k46o&ima=0000&page=0&rw=25&cd=member";
  private JSoupHelper mJSoupHelper;
  private CompositeDisposable mCompositeDisposable;

  private SwipeRefreshLayout mSwipeRefreshLayout;
  private RecyclerView mRecyclerView;
  private ListAdapter mListAdapter;

  public static FragmentView newInstance(int memberId) {
    Bundle args = new Bundle();
    args.putInt(MEMBER_ID_KEY, memberId);
    FragmentView fragment = new FragmentView();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mJSoupHelper = new JSoupHelper();
    mCompositeDisposable = new CompositeDisposable();
    mListAdapter = new ListAdapter(this);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    FragmentBlogBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_blog, container, false);

    mSwipeRefreshLayout = binding.blogSwipeRefreshLayout;
    mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_purple,
        R.color.colorLightGreen500);
    mSwipeRefreshLayout.setOnRefreshListener(this);

    mRecyclerView = binding.blogRecyclerView;
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.addItemDecoration(
        new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mRecyclerView.setAdapter(mListAdapter);

    loadBlogList();
    return binding.getRoot();
  }

  @Override public void onDestroy() {
    if (mCompositeDisposable.isDisposed()) {
      mCompositeDisposable.clear();
    }
    super.onDestroy();
  }

  private void loadBlogList() {
    mCompositeDisposable.add(Observable.create(new ObservableOnSubscribe<List<ViewModel>>() {
      @Override public void subscribe(ObservableEmitter<List<ViewModel>> e) throws Exception {
        e.onNext(mJSoupHelper.getViewModelList(getArguments() == null ? Constant.K_URL + url
            : Constant.K_URL + url + "&ct=" + getArguments().getInt(MEMBER_ID_KEY)));
        e.onComplete();
      }
    })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<ViewModel>>() {
          @Override public void accept(List<ViewModel> viewModels) throws Exception {
            mListAdapter.putViewModelList(viewModels);
            if (mSwipeRefreshLayout.isRefreshing()) {
              mSwipeRefreshLayout.setRefreshing(false);
            }
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            Log.d("TAG", "Throwable: " + throwable.getMessage());
            if (mSwipeRefreshLayout.isRefreshing()) {
              mSwipeRefreshLayout.setRefreshing(false);
            }
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
    if (mCompositeDisposable.isDisposed()) {
      mCompositeDisposable.clear();
    }
    loadBlogList();
  }
}
