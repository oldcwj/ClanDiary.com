package controllers;

import java.util.List;

import models.Account;
import models.Item;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class AccountController extends Controller {
    
    public static Result login() {
        return ok(views.html.login.render(Form.form(Account.class)));
    }
    
    public static Result isLoginSuccess() {
        Form<Account> submission = Form.form(Account.class).bindFromRequest();
        if (submission.hasErrors()) {
            return badRequest(views.html.login.render(submission));
        } else {
            Account account = submission.get();
            List<Account> accountList = Account.find.where().eq("userName", account.userName)
                    .eq("password", account.password).findList();
            if (accountList != null && accountList.size() >= 1) {
                return redirect(routes.ItemController.list());
            } else {
                return badRequest(views.html.login.render(submission));
            }
        }
    }
}
