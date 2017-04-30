package johnsmithwithharuhi.co.sakagumi.Data.Repository;

import io.reactivex.Observable;
import java.util.List;
import johnsmithwithharuhi.co.sakagumi.Data.Entity.BlogEntity;
import johnsmithwithharuhi.co.sakagumi.Data.JSoup.BlogJSoup;

public class BlogRepository {

  private BlogJSoup mBlogJSoup;

  public BlogRepository() {
    mBlogJSoup = new BlogJSoup();
  }

  public Observable<List<BlogEntity>> getOsuBlog() {
    return Observable.create(e -> {
      e.onNext(mBlogJSoup.createOsuBlogList());
      e.onComplete();
    });
  }

  public Observable<List<BlogEntity>> getNogBlog() {
    return Observable.create(e -> {
      e.onNext(mBlogJSoup.createNogBlogList());
      e.onComplete();
    });
  }

  public Observable<List<BlogEntity>> getKeyBlog() {
    return Observable.create(e -> {
      e.onNext(mBlogJSoup.createKeyBlogList());
      e.onComplete();
    });
  }
}
