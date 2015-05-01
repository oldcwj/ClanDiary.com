package controllers;

import models.User;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Application extends Controller {

	public static Result GO_HOME = redirect(routes.ItemController.list());
	
	public static Result index() {
		String email = ctx().session().get("email");
		if (email != null) {
			User user = User.findByEmail(email);
			if (user != null) {
				return GO_HOME;
			} else {
				session().clear();
			}
		}
		return ok(login.render(Form.form(Application.Login.class)));
	}
	
	public static Result signup() {
	    return ok(signup.render(Form.form(User.class)));
	}
	
    public static Result signupAuth() {
        Form<User> signupForm = Form.form(User.class).bindFromRequest();
        if (signupForm.hasErrors()) {
            return badRequest(signup.render(signupForm));
        } else {
            try {
                User user = signupForm.get();
                user.save();
                session("email", user.email);
                System.out.println("signup");
            } catch (Exception exception) {
                return badRequest(signup.render(signupForm));
            }
            return GO_HOME;
        }
    }
	
	// TODO
	public static Result getLoginName() {
	    String email = ctx().session().get("email");
	    
	    return ok();
	}

	public static Result logout() {
		session().clear();
		flash("success", Messages.get("youve.been.logged.out"));
		return GO_HOME;
	}

	public static Result authenticate() {
		Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			return badRequest(login.render(loginForm));
		} else {
			session("email", loginForm.get().email);
			System.out.println("authenticate");
			return GO_HOME;
		}
	}

	public static class Login {
		public String email;
		public String password;

		public String validate() {
			if (User.authenticate(email, password) == null) {
				return Messages.get("Invalid username or password");
			}
			return null;

		}
	}
}
