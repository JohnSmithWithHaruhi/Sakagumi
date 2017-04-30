package johnsmithwithharuhi.co.sakagumi.Domain.UseCase;

import android.text.TextUtils;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;
import johnsmithwithharuhi.co.sakagumi.Data.Entity.BlogEntity;
import johnsmithwithharuhi.co.sakagumi.Data.Repository.BlogRepository;
import johnsmithwithharuhi.co.sakagumi.Presentation.ViewModel.Item.ItemBlogListViewModel;
import johnsmithwithharuhi.co.sakagumi.R;

public class BlogUseCase {

  public static final int TYPE_OSU = 0;
  public static final int TYPE_NOG = 1;
  public static final int TYPE_KEY = 2;

  private BlogRepository mRepository;

  public BlogUseCase() {
    mRepository = new BlogRepository();
  }

  public Observable<List<ItemBlogListViewModel>> getViewModelList(int type) {
    Observable<List<BlogEntity>> observable;
    switch (type) {
      default:
      case TYPE_OSU:
        observable = mRepository.getOsuBlog();
        break;
      case TYPE_NOG:
        observable = mRepository.getNogBlog();
        break;
      case TYPE_KEY:
        observable = mRepository.getKeyBlog();
        break;
    }
    return observable.flatMap(
        blogEntities -> Observable.fromIterable(blogEntities).map(this::convertEntityToViewModel))
        .toList()
        .toObservable();
  }

  public Observable<List<ItemBlogListViewModel>> getNewestViewModelList(int type,
      String newestUrl) {
    Observable<List<BlogEntity>> observable;
    switch (type) {
      default:
      case TYPE_OSU:
        observable = mRepository.getOsuBlog();
        break;
      case TYPE_NOG:
        observable = mRepository.getNogBlog();
        break;
      case TYPE_KEY:
        observable = mRepository.getKeyBlog();
        break;
    }
    return observable.flatMap(
        blogEntities -> Observable.fromIterable(blogEntities).map(this::convertEntityToViewModel))
        .toList()
        .map(viewModels -> filterOlder(viewModels, newestUrl))
        .toObservable();
  }

  private ItemBlogListViewModel convertEntityToViewModel(BlogEntity blogEntity) {
    ItemBlogListViewModel viewModel = new ItemBlogListViewModel();
    viewModel.title.set(blogEntity.getTitle());
    viewModel.name.set(blogEntity.getName());
    viewModel.content.set(blogEntity.getContent());
    viewModel.url.set(blogEntity.getUrl());
    viewModel.time.set(blogEntity.getTime());
    viewModel.textColor = blogEntity.getType() == BlogEntity.NOG_KEY ? R.color.colorPurple700
        : R.color.colorLightGreen700;
    return viewModel;
  }

  private List<ItemBlogListViewModel> filterOlder(List<ItemBlogListViewModel> itemList,
      String newestUrl) {
    List<ItemBlogListViewModel> tempList = new ArrayList<>();
    for (ItemBlogListViewModel item : itemList) {
      if (!TextUtils.equals(item.url.get(), newestUrl)) {
        tempList.add(item);
      } else {
        break;
      }
    }
    return tempList;
  }
}
