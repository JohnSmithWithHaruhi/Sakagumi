package johnsmithwithharuhi.co.nogikeya.Blog;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
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

public class FragmentView extends Fragment implements ViewModel.OnItemClickListener {

  private String url = "/mob/news/diarKiji.php?site=k46o&ima=0000&page=0&rw=20&cd=member";
  private JSoupHelper mJSoupHelper;
  private CompositeDisposable mCompositeDisposable;

  private ListAdapter mListAdapter;

  public static FragmentView newInstance() {
    Bundle args = new Bundle();
    FragmentView fragment = new FragmentView();
    fragment.setArguments(args);
    return fragment;
  }

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
    RecyclerView recyclerView = binding.blogRecyclerView;
    recyclerView.setHasFixedSize(true);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.addItemDecoration(
        new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(mListAdapter);
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
            mListAdapter.setKModelList(viewModels, FragmentView.this);
            mListAdapter.notifyDataSetChanged();
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            Log.d("TAG", "test: " + throwable.getMessage());
          }
        }));
    return binding.getRoot();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    mCompositeDisposable.dispose();
  }

  @Override public void onItemClick(String url) {
    new CustomTabsIntent.Builder().setShowTitle(true).enableUrlBarHiding().addDefaultShareMenuItem()
        .setStartAnimations(getContext(), android.R.anim.slide_in_left,
            android.R.anim.slide_out_right)
        .setExitAnimations(getContext(), android.R.anim.slide_in_left,
            android.R.anim.slide_out_right)
        .build()
        .launchUrl(getContext(), Uri.parse(url));
  }
}
