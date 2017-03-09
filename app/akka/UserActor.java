package akka;

import akka.actor.Props;
import akka.actor.UntypedActor;
import models.User;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import play.Logger;
import play.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

import javax.inject.Inject;

/**
 * Created by Justin-pc on 2017/3/7.
 */
public class UserActor extends UntypedActor {

    private PlayJongo playJongo;

    @Inject
    public UserActor(PlayJongo playJongo) {
        this.playJongo = playJongo;
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        Logger.debug("Start actor to save time tracker message to mongo {}", self());
    }
    @Override
    public void postStop() throws Exception {
        super.postStop();
        Logger.debug("Stop actor to save time tracker message to mongo {}", self());
    }

    public void onReceive(Object msg) throws Exception {
        if (msg instanceof String) {
            User user = new User();
            user.name="1111111111111";
            insert(user);
        }
    }

    public MongoCollection users() {
        return playJongo.getCollection("users");
    }

    public void insert(User user) {
        users().save(user);
    }

    public void remove(ObjectId id) {
        users().remove(id);
    }

    public User findByName(String name) {
        return users().findOne("{name: #}", name).as(User.class);
    }

}
