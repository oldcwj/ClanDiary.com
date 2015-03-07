package controllers;


import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;

import models.Item;
import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import utils.FileUtil;

public class ItemController extends Controller{
    public static final String ROOT_IMAGE_PATH = "~/Play/ClanDiary/imageCache/";
    
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
            newFile = new File(newFile.getAbsolutePath() + "/" + picture.getFilename());
            FileUtil.copyFile(imageFile, newFile);
            Item createItem = submission.get();
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
