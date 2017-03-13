package controllers;

import models.Article;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import uk.co.panaxiom.playjongo.PlayJongo;
import views.html.*;
import views.html.personal.article;
import views.html.personal.articlelist;
import views.html.personal.personal;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
        private PlayJongo playJongo;
        private FormFactory formFactory;

        private Article at;
    private String articleHtml = "";
private Form<Article> userForm;
        Map<String,String> anyData = new HashMap<>();

    @Inject
    public HomeController(PlayJongo playJongo,FormFactory formFactory ) {

         this.playJongo = playJongo;

         this.formFactory = formFactory;
    }
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result signUp() {
        return ok(signUp.render());
    }

    public Result newPost() {
        return ok(articlelist.render());
    }

    public Result article() {
//            MongoCursor<Article> all = getArticleMongo().find().as(Article.class);
        at = getArticleMongo().findOne().as(Article.class);
            if (at==null){
                at = new Article();
                at.articleHtml="";
                at.articleName="";
            }

            Logger.debug("111111111"+at);
        return ok(article.render(at));
    }

    public MongoCollection getArticleMongo() {
        return playJongo.getCollection("article");
    }

    public void insert(Article article) {
        getArticleMongo().save(article);
    }


    public Result saveArticle(){

        DynamicForm dynamicForm =formFactory.form().bindFromRequest();
//        Article article = new Article();

        at.articleName="1";
        at.publishedDate="1";
        at.tag="1";
        at.articleIntroduction="1";
//        at.articleText = dynamicForm.get("content");
//        at.articleHtml = dynamicForm.get("result");
        Logger.debug("atarticleHtml"+at.articleHtml);
        insert(at);
        return ok();
    }
}
