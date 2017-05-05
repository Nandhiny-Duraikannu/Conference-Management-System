package models;

import play.Logger;
import play.data.validation.ValidationError;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * User entity managed by Ebean
 */
@Entity
public class PCMember extends com.avaje.ebean.Model {

    private static final long serialVersionUID = 1L;

    @Id
    public Long id;

    @NotNull
    @ManyToOne
    public Conference conference;

    @NotNull
    @ManyToOne
    public User user;

    public String role;

    /**
     * Generic query helper for entity User with id Long
     */
    public static Find<Long, PCMember> find = new Find<Long, PCMember>() {
    };

    public static List<PCMember> getByConfId(Long conf_id) {
        return find.where().eq("conference.id", conf_id).findList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

