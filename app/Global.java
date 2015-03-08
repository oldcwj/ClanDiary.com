import models.Item;
import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import play.libs.F.*;

import static play.mvc.Results.*;

public class Global extends GlobalSettings {
    
    @Override
    public void onStart(Application app) {
        super.onStart(app);
        if (Item.find.all().isEmpty()) {
        }
    }

    @Override
    public Promise<Result> onHandlerNotFound(RequestHeader request) {
        return Promise.<Result>pure(notFound(
                views.html.notFoundPage.render()
            ));
    }
    
    
}