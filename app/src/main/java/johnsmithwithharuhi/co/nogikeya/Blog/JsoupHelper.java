package johnsmithwithharuhi.co.nogikeya.Blog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import johnsmithwithharuhi.co.nogikeya.Blog.Model.KModel;

class JSoupHelper {

    List<KModel> getKModelList(String url) throws Exception {
        Document document = Jsoup.connect(url).get();
        Elements elements = document.getElementsByClass("box-newposts").first().getElementsByTag("li");
        List<KModel> kModelList = new ArrayList<>();
        for (Element element : elements) {
            Element boxBlogElement = element.getElementsByClass(("box-blog")).first();
            KModel kModel = new KModel();
            kModel.time.set(boxBlogElement.select("time").text());
            kModel.name.set(boxBlogElement.select("p").not(".ttl").first().text());
            kModel.title.set(boxBlogElement.getElementsByClass("ttl").text());
            kModel.content.set(boxBlogElement.getElementsByClass("text").text());
            kModel.url.set(element.getElementsByTag("a").first().attr("href"));
            kModelList.add(kModel);
        }
        return kModelList;
    }
}
