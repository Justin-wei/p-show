package controllers;

import models.Article;
import org.jongo.MongoCollection;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import play.twirl.api.Html;
import uk.co.panaxiom.playjongo.PlayJongo;
import views.html.*;
import views.html.personal.article;
import views.html.personal.articleEdit;
import views.html.personal.articleList;

import javax.inject.Inject;
/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    private PlayJongo playJongo;
    private FormFactory formFactory;

    @Inject
    public HomeController(PlayJongo playJongo, FormFactory formFactory) {

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
        return ok(articleList.render());
    }

    public Result article() {
//            MongoCursor<Article> all = getArticleMongo().find().as(Article.class);

        Article articleObject = getArticleMongo().findOne().as(Article.class);
        if (articleObject == null) {
            articleObject = new Article();
            articleObject.articleHtml = "";
            articleObject.articleTitle = "";
        }
        return ok(article.render(Html.apply(articleObject.articleHtml)));
    }

    public Result articleEdit() {
//            MongoCursor<Article> all = getArticleMongo().find().as(Article.class);

        Article articleObject = getArticleMongo().findOne().as(Article.class);
        if (articleObject == null) {
            articleObject = new Article();
            articleObject.articleHtml = "";
            articleObject.articleTitle = "";
        }
        Form<Article> articleForm = formFactory.form(Article.class);
        articleForm = articleForm.fill(articleObject);
        return ok(articleEdit.render(articleForm));
    }

    public MongoCollection getArticleMongo() {
        return playJongo.getCollection("article");
    }

    public void insert(Article article) {
        getArticleMongo().save(article);
    }


    public Result saveArticle() {

//        DynamicForm dynamicForm =formFactory.form().bindFromRequest();
        Article article = formFactory.form(Article.class).bindFromRequest().get();
//        article.articleText = dynamicForm.get("content");
//        article.articleHtml = dynamicForm.get("result");
        insert(article);
        return ok();
    }
}
