package johnsmithwithharuhi.co.sakagumi.Data.JSoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import johnsmithwithharuhi.co.sakagumi.Data.Entity.BlogEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BlogJSoup {

  private static final String NOG_URL = "http://blog.nogizaka46.com";
  private static final String KEY_URL = "http://www.keyakizaka46.com";

  private static final int OSU_KEY = 0;
  private static final int NOG_KEY = 1;
  private static final int KEY_KEY = 2;

  public List<BlogEntity> createOsuBlogList() throws IOException {
    List<BlogEntity> blogEntities = new ArrayList<>();
    Document document =
        Jsoup.connect(KEY_URL + "/s/k46o/diary/member/list?ima=0000&page=0&rw=30&cd=member&ct=11")
            .get();
    Elements elements = document.getElementsByTag("article");
    for (Element element : elements) {
      BlogEntity blogEntity = new BlogEntity();
      blogEntity.setType(OSU_KEY);
      blogEntity.setName(element.getElementsByClass("name").text());
      blogEntity.setTitle(element.getElementsByTag("a").first().text());
      blogEntity.setUrl(KEY_URL + element.getElementsByTag("a").first().attr("href"));
      blogEntity.setContent(element.getElementsByClass("box-article").text());
      blogEntity.setTime(
          element.getElementsByClass(("box-bottom")).first().getElementsByTag("li").first().text());
      blogEntities.add(blogEntity);
    }
    return blogEntities;
  }

  public List<BlogEntity> createKeyBlogList() throws IOException {
    List<BlogEntity> blogEntities = new ArrayList<>();
    Document document =
        Jsoup.connect(KEY_URL + "/s/k46o/diary/member/list?ima=0000&page=0&rw=30&cd=member").get();
    Elements elements = document.getElementsByTag("article");
    for (Element element : elements) {
      BlogEntity blogEntity = new BlogEntity();
      blogEntity.setType(KEY_KEY);
      blogEntity.setName(element.getElementsByClass("name").text());
      blogEntity.setTitle(element.getElementsByTag("a").first().text());
      blogEntity.setUrl(KEY_URL + element.getElementsByTag("a").first().attr("href"));
      blogEntity.setContent(element.getElementsByClass("box-article").text());
      blogEntity.setTime(
          element.getElementsByClass(("box-bottom")).first().getElementsByTag("li").first().text());
      blogEntities.add(blogEntity);
    }
    return blogEntities;
  }

  public List<BlogEntity> createNogBlogList() throws IOException {
    List<BlogEntity> blogEntities = new ArrayList<>();
    for (int i = 1; i <= 6; i++) {
      blogEntities.addAll(createNogBlogList(i));
    }
    return blogEntities;
  }

  private List<BlogEntity> createNogBlogList(int page) throws IOException {
    Document document = Jsoup.connect(NOG_URL + "/?p=" + page).get();
    Elements headElements = document.select(".heading");
    Elements bodyElements = document.getElementsByClass("entrybody");
    Elements bottomElements = document.getElementsByClass("entrybottom");
    List<BlogEntity> blogEntities = new ArrayList<>();
    for (int i = 0; i < headElements.size(); i++) {
      Element headElement = headElements.get(i);
      BlogEntity blogEntity = new BlogEntity();
      blogEntity.setType(NOG_KEY);
      blogEntity.setName(headElement.getElementsByClass("author").text());
      blogEntity.setTitle(headElement.getElementsByTag("a").first().text());
      blogEntity.setUrl(headElement.getElementsByTag("a").first().attr("href"));
      blogEntity.setContent(bodyElements.get(i).text().replace(" ", ""));
      blogEntity.setTime(bottomElements.get(i).text().split("｜")[0]);
      blogEntities.add(blogEntity);
    }
    return blogEntities;
  }
}
