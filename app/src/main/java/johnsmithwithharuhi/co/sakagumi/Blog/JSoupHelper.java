package johnsmithwithharuhi.co.sakagumi.Blog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import johnsmithwithharuhi.co.sakagumi.Constant;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class JSoupHelper {

  private static final int OSU_KEY = 0;
  private static final int NOG_KEY = 1;
  private static final int KEY_KEY = 2;

  List<ViewModel> getViewModelList(int type) throws IOException {
    switch (type) {
      case OSU_KEY:
        return getOsuViewModelList();
      case NOG_KEY:
        return getNogViewModelList();
      case KEY_KEY:
        return getKeyViewModelList();
      default:
        return getOsuViewModelList();
    }
  }

  private List<ViewModel> getOsuViewModelList() throws IOException {
    Document document = Jsoup.connect(
        Constant.KEY_URL + "/s/k46o/diary/member/list?ima=0000&page=0&rw=30&cd=member&ct=11").get();
    Elements elements = document.getElementsByTag("article");
    List<ViewModel> viewModelList = new ArrayList<>();
    for (Element element : elements) {
      ViewModel viewModel = new ViewModel();
      viewModel.type.set(2);
      viewModel.name.set(element.getElementsByClass("name").text());
      viewModel.title.set(element.getElementsByTag("a").first().text());
      viewModel.url.set(Constant.KEY_URL + element.getElementsByTag("a").first().attr("href"));
      viewModel.content.set(element.getElementsByClass("box-article").text());
      viewModel.time.set(
          element.getElementsByClass(("box-bottom")).first().getElementsByTag("li").first().text());
      viewModelList.add(viewModel);
    }
    return viewModelList;
  }

  private List<ViewModel> getKeyViewModelList() throws IOException {
    Document document = Jsoup.connect(
        Constant.KEY_URL + "/s/k46o/diary/member/list?ima=0000&page=0&rw=30&cd=member").get();
    Elements elements = document.getElementsByTag("article");
    List<ViewModel> viewModelList = new ArrayList<>();
    for (Element element : elements) {
      ViewModel viewModel = new ViewModel();
      viewModel.type.set(2);
      viewModel.name.set(element.getElementsByClass("name").text());
      viewModel.title.set(element.getElementsByTag("a").first().text());
      viewModel.url.set(Constant.KEY_URL + element.getElementsByTag("a").first().attr("href"));
      viewModel.content.set(element.getElementsByClass("box-article").text());
      viewModel.time.set(
          element.getElementsByClass(("box-bottom")).first().getElementsByTag("li").first().text());
      viewModelList.add(viewModel);
    }
    return viewModelList;
  }

  private List<ViewModel> getNogViewModelList() throws IOException {
    List<ViewModel> viewModelList = new ArrayList<>();
    for (int i = 1; i <= 6; i++) {
      viewModelList.addAll(getNogViewModelList(i));
    }
    return viewModelList;
  }

  private List<ViewModel> getNogViewModelList(int page) throws IOException {
    Document document = Jsoup.connect(Constant.NOG_URL + "/?p=" + page).get();
    Elements headElements = document.select(".heading");
    Elements bodyElements = document.getElementsByClass("entrybody");
    Elements bottomElements = document.getElementsByClass("entrybottom");
    List<ViewModel> viewModelList = new ArrayList<>();
    for (int i = 0; i < headElements.size(); i++) {
      Element headElement = headElements.get(i);
      ViewModel viewModel = new ViewModel();
      viewModel.type.set(1);
      viewModel.name.set(headElement.getElementsByClass("author").text());
      viewModel.title.set(headElement.getElementsByTag("a").first().text());
      viewModel.url.set(headElement.getElementsByTag("a").first().attr("href"));
      viewModel.content.set(bodyElements.get(i).text().replace(" ", ""));
      viewModel.time.set(bottomElements.get(i).text().split("｜")[0]);
      viewModelList.add(viewModel);
    }
    return viewModelList;
  }
}
