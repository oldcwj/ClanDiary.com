import models.User;
import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import play.libs.F.*;
import static play.mvc.Results.*;

public class Global extends GlobalSettings {
    
    @Override
    public void onStart(Application app) {
        super.onStart(app);
        if (User.finder.all().size() == 0) {
            User user = new User();
            user.name = "oldcwj";
            user.password = "666666";
            user.email = "oldcwj@gmail.com";
            user.save();
        }
    }

    @Override
    public Promise<Result> onHandlerNotFound(RequestHeader request) {
        return Promise.<Result>pure(notFound(
                views.html.notFoundPage.render()
            ));
    }
    
    
}