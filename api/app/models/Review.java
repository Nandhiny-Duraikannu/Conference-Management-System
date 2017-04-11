package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Paper review entity managed by Ebean
 */
@Entity
public class Review extends Model {
    private static final long serialVersionUID = 1L;

    @Id
    public Long id;

    @NotNull
    @ManyToOne
    public User user;

    @NotNull
    @ManyToOne
    public Paper paper;

    @Column(length = 10000)
    public String content;
}