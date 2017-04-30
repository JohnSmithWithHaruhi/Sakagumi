package johnsmithwithharuhi.co.sakagumi.Presentation.Presenter;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import johnsmithwithharuhi.co.sakagumi.Domain.UseCase.BlogUseCase;
import johnsmithwithharuhi.co.sakagumi.Domain.Utils.BitmapUtil;
import johnsmithwithharuhi.co.sakagumi.Presentation.Adapter.BlogListAdapter;
import johnsmithwithharuhi.co.sakagumi.Presentation.ViewModel.Item.ItemBlogListViewModel;
import johnsmithwithharuhi.co.sakagumi.R;
import johnsmithwithharuhi.co.sakagumi.databinding.FragmentBlogBinding;

public class BlogPageFragment extends Fragment
    implements ItemBlogListViewModel.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

  private static final String PAGE_POSITION = "blog_page_position";

  private FragmentBlogBinding mBinding;
  private CompositeDisposable mCompositeDisposable;
  private BlogListAdapter mBlogListAdapter;
  private BlogUseCase mBlogUseCase;

  private SwipeRefreshLayout mSwipeRefreshLayout;

  private int mType;

  public static BlogPageFragment newInstance(int pagePosition) {
    Bundle args = new Bundle();
    args.putInt(PAGE_POSITION, pagePosition);
    BlogPageFragment fragment = new BlogPageFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mCompositeDisposable = new CompositeDisposable();
    mBlogUseCase = new BlogUseCase();
    mBlogListAdapter = new BlogListAdapter(getContext(), this);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_blog, container, false);
    mType = covertPagePositionToType(getArguments().getInt(PAGE_POSITION));

    initSwipeRefreshLayout();
    initRecyclerView();

    if (mBlogListAdapter.getItemCount() == 0) {
      mSwipeRefreshLayout.setRefreshing(true);
      initBlogList();
    }
    return mBinding.getRoot();
  }

  @Override public void onDestroy() {
    mCompositeDisposable.clear();
    super.onDestroy();
  }

  private void initSwipeRefreshLayout() {
    mSwipeRefreshLayout = mBinding.blogSwipeRefreshLayout;
    mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_purple,
        R.color.colorLightGreen500);
    mSwipeRefreshLayout.setOnRefreshListener(this);
  }

  private void initRecyclerView() {
    RecyclerView recyclerView = mBinding.blogRecyclerView;
    recyclerView.setHasFixedSize(true);
    recyclerView.addItemDecoration(
        new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(mBlogListAdapter);
  }

  private void initBlogList() {
    mCompositeDisposable.add(mBlogUseCase.getViewModelList(mType).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(viewModels -> {
          mBlogListAdapter.initViewModelList(viewModels);
          if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
          }
        }, throwable -> {
          Log.d("TAG", "Throwable: " + throwable.getMessage());
          if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
          }
        }));
  }

  private void loadNewBlogList() {
    mCompositeDisposable.add(
        mBlogUseCase.getNewestViewModelList(mType, mBlogListAdapter.getNewestUrl())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(viewModels -> {
              mBlogListAdapter.putViewModelList(viewModels);
              if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
              }
            }, throwable -> {
              Log.d("TAG", "Throwable: " + throwable.getMessage());
              if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
              }
            }));
  }

  private int covertPagePositionToType(int position) {
    switch (position) {
      default:
      case 0:
        return BlogUseCase.TYPE_OSU;
      case 1:
        return BlogUseCase.TYPE_NOG;
      case 2:
        return BlogUseCase.TYPE_KEY;
    }
  }

  @Override public void onItemClick(String url) {
    int toolbarColor;
    switch (mType) {
      default:
      case BlogUseCase.TYPE_OSU:
        toolbarColor = R.color.colorGrey700;
        break;
      case BlogUseCase.TYPE_NOG:
        toolbarColor = R.color.colorPurple700;
        break;
      case BlogUseCase.TYPE_KEY:
        toolbarColor = R.color.colorLightGreen700;
        break;
    }
    new CustomTabsIntent.Builder().setShowTitle(true)
        .setToolbarColor(ContextCompat.getColor(getContext(), toolbarColor))
        .enableUrlBarHiding()
        .addDefaultShareMenuItem()
        .setCloseButtonIcon(
            BitmapUtil.convertBitmapFromVectorDrawable(getContext(), R.drawable.ic_arrow_back_w))
        .setStartAnimations(getContext(), android.R.anim.slide_in_left,
            android.R.anim.slide_out_right)
        .setExitAnimations(getContext(), android.R.anim.slide_in_left,
            android.R.anim.slide_out_right)
        .build()
        .launchUrl(getContext(), Uri.parse(url));
  }

  @Override public void onRefresh() {
    mCompositeDisposable.clear();
    if (mBlogListAdapter.getItemCount() == 0) {
      mSwipeRefreshLayout.setRefreshing(true);
      initBlogList();
    } else {
      mSwipeRefreshLayout.setRefreshing(true);
      loadNewBlogList();
    }
  }
}
