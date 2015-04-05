package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.util.List;

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
    
    public static Result about() {
        return ok(views.html.about.render());
    }
    
    public static Result list() {
        String email = ctx().session().get("email");
        List<Item> items = Item.find.where().eq("email", email).findList();
        return ok(views.html.list.render(items));
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
            
            String imageNameString = Integer.toHexString(picture.getFilename().hashCode());
            FileUtil.saveImageToDisk(imageFile, imageNameString);
            FileUtil.scaleSmallImageToDisk(imageFile, imageNameString); 
            
            String email = ctx().session().get("email");
            Item createItem = submission.get();
            createItem.imageUrl = imageNameString;
            createItem.email = email;
            createItem.save();
            return redirect(routes.ItemController.details(createItem.id));
        }
    }
    
    public static Result details(Long id) {
        Item item = Item.find.byId(id);
        if (item != null) {
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
        return ok(Json.toJson(updateItem));
    }
    
    public static Result delete(Long id) {
        return ok();
    }
    
    public static Result serve(String filepath){
        return ok(new File(Constant.ROOT_IMAGE_PATH + "/" + filepath));
    }
    
    public static Result smallImage(String filepath){
        return ok(new File(Constant.ROOT_IMAGE_SMAILL_PATH + "/" + filepath));
    }
}
