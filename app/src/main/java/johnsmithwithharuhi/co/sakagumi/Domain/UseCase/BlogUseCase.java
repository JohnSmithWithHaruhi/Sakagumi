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

  private BlogRepository mRepository;

  public BlogUseCase() {
    mRepository = new BlogRepository();
  }

  public Observable<List<ItemBlogListViewModel>> getOsuViewModelList() {
    return mRepository.getOsuBlog()
        .flatMap(blogEntities -> Observable.fromIterable(blogEntities)
            .map(this::convertEntityToViewModel))
        .toList()
        .toObservable();
  }

  public Observable<List<ItemBlogListViewModel>> getNogViewModelList() {
    return mRepository.getNogBlog()
        .flatMap(blogEntities -> Observable.fromIterable(blogEntities)
            .map(this::convertEntityToViewModel))
        .toList()
        .toObservable();
  }

  public Observable<List<ItemBlogListViewModel>> getKeyViewModelList() {
    return mRepository.getKeyBlog()
        .flatMap(blogEntities -> Observable.fromIterable(blogEntities)
            .map(this::convertEntityToViewModel))
        .toList()
        .toObservable();
  }

  public Observable<List<ItemBlogListViewModel>> getNewOsuViewModelList(String newestUrl) {
    return mRepository.getOsuBlog()
        .flatMap(blogEntities -> Observable.fromIterable(blogEntities)
            .map(this::convertEntityToViewModel))
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
