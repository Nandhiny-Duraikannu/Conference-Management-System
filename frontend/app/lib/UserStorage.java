package lib;

import controllers.routes;
import models.User;
import play.Logger;
import play.mvc.Result;
import play.mvc.Http;
import play.mvc.Security;

/**
 * Provides methods for play framework to check if route is available for current user.
 */
public class UserStorage extends Security.Authenticator {
    @Override
    public String getUsername(Http.Context ctx) {
        Logger.debug("Getting user from UserStorage getUsername() method");
        String username = ctx.session().get("username");

        if (!Http.Context.current().args.containsKey("user")) {
            Http.Context.current().args.put("user", User.getByName(username));
        }

        return username;
    }

    /**
     * Returns current user or null if it's not logged in
     *
     * @return user
     */
    public static User getCurrentUser() {
        Http.Context context = Http.Context.current();

        if (!context.args.containsKey("user")) {
            context.args.put("user", User.getByName(context.session().get("username")));
        }

        return (User) context.args.get("user");
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        Logger.debug("User is unathorized to access to the protected ressource. We redirect him to login page");
        return redirect(controllers.routes.HomeController.index());
    }
}