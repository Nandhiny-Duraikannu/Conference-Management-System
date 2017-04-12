package lib;

import controllers.routes;
import models.User;
import play.Logger;
import play.mvc.Result;
import play.mvc.Http;
import play.mvc.Security;

public class UserStorage {
    /**
     * Returns current user or null if it's not logged in
     *
     * @return user
     */
    public static User getCurrentUser() {
        Http.Context context = Http.Context.current();

        if (context.request().getQueryString("currentUserName") == null) {
            return null;
        }

        if (!context.args.containsKey("user")) {
            context.args.put("user", User.getByName(context.request().getQueryString("currentUser")));
        }

        return (User) context.args.get("user");
    }
}