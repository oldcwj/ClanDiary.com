package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.rs.GetPolicy;
import com.qiniu.api.rs.URLUtils;

import java.io.File;

import models.Item;
import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Security;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import utils.Constant;
import utils.FileUtil;

@Security.Authenticated(Secured.class)
public class ItemController extends Controller{
    public static final String ROOT_IMAGE_PATH = "../imageCache/";
    
    public static Result about() {
        return ok(views.html.about.render());
    }
    
    public static Result list() {
        return ok(views.html.list.render(Item.find.all()));
    }
    
    // @BodyParser.Of(BodyParser.Json.class)
    public static Result create() {
        Form<Item> submission = Form.form(Item.class).bindFromRequest();
        if (submission.hasErrors()) {
            return badRequest(views.html.createForm.render(submission));
        } else {
            MultipartFormData body = request().body().asMultipartFormData();
            FilePart picture = body.getFile("imageUrl");
            File imageFile = picture.getFile();
            
            File newFile = new File(ROOT_IMAGE_PATH);//
            if (!newFile.exists()) {
                newFile.mkdirs();
            }
            
            String imageNameString = Integer.toHexString(picture.getFilename().hashCode());
            newFile = new File(newFile.getAbsolutePath() + "/" + imageNameString);
            FileUtil.copyFile(imageFile, newFile);
            // FileUtil.uploadFile(imageNameString);
            Item createItem = submission.get();
            createItem.imageUrl = imageNameString;
            createItem.save();
            if (createItem != null) {
                return redirect(routes.ItemController.details(createItem.id));
            } else {
                return internalServerError();
            }
        }
    }
    
    public static Result details(Long id) {
        Item item = Item.find.byId(id);
        if (item != null) {
            // return ok(Json.toJson(item));
            Mac mac = new Mac(Constant.ACCESS_KEY, Constant.SECRET_KEY);
            String baseUrl;
            try {
                baseUrl = URLUtils.makeBaseUrl("", "");
                GetPolicy getPolicy = new GetPolicy();
                String downloadUrl = getPolicy.makeRequest(baseUrl, mac);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return ok(views.html.details.render(item));
        } else {
            return notFound();
        }
    }
    
    public static Result createForm() {
        return ok(views.html.createForm.render(Form.form(Item.class)));
    }
    
    @BodyParser.Of(BodyParser.Json.class)
    public static Result update(Long id) {
        JsonNode json = request().body().asJson();
        Item updateItem;
        try {
            updateItem = Json.fromJson(json, Item.class);
        } catch (Exception e) {
            return badRequest();
        }
        updateItem.save();
        if (updateItem != null) {
            return ok(Json.toJson(updateItem));
        } else {
            return internalServerError();
        }
    }
    
    public static Result delete(Long id) {
        return ok();
    }
    
    public static Result serve(String filepath){
        return ok(new File(ROOT_IMAGE_PATH + "/" + filepath));
    }
}
