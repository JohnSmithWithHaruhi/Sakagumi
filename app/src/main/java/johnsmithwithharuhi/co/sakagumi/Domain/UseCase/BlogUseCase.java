package johnsmithwithharuhi.co.sakagumi.Domain.UseCase;

import io.reactivex.Observable;
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
}
