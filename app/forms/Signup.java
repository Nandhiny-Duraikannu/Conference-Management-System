package forms;

import models.User;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class Signup {
    private String name;
    private String password;

    public List validate() {
        User user = User.getByNameAndPassword(getName(), getPassword());
        List errors = new ArrayList();

        if (user != null) {
            errors.add(new ValidationError("name", "This user already exists"));
        }

        return errors;
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
        return "SignupForm {name: " + this.name + "}";
    }

}

