package models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Paper-Authors relationship entity managed by Ebean
 */
@Entity
public class PaperFile extends com.avaje.ebean.Model {

    private static final long serialVersionUID = 1L;

    @OneToOne
    public Paper paper;

    @NotNull
    @Lob
    public byte[] file;

    public int size;

    public String format;

    public PaperFile() {
    }

    public static Find<Long, PaperFile> find = new Find<Long, PaperFile>() {
    };
}