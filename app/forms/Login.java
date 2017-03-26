package forms;

import models.User;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class Login {
    private String name;
    private String password;

    public List validate() {
        User user = User.getByNameAndPassword(getName(), getPassword());

        if (user == null) {
            List errors = new ArrayList();
            errors.add(new ValidationError("name", "Login or password is incorrect"));
            return errors;
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginForm {name: " + this.name + "}";
    }

}

