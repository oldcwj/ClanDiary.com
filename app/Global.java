import java.io.File;

import models.User;
import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import play.libs.F.*;
import utils.Constant;
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
        
        File newFile = new File(Constant.ROOT_IMAGE_PATH);//
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        
        File smallFile = new File(Constant.ROOT_IMAGE_SMAILL_PATH);
        if (!smallFile.exists()) {
            smallFile.mkdirs();
        }
    }

    @Override
    public Promise<Result> onHandlerNotFound(RequestHeader request) {
        return Promise.<Result>pure(notFound(
                views.html.notFoundPage.render()
            ));
    }
    
    
}