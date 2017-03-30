package models;

import java.io.Serializable;
import javax.persistence.*;


@Embeddable
public class PaperAuthorsPK implements Serializable {

    public long paper_id;

    public long author_id;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PaperAuthorsPK other = (PaperAuthorsPK) obj;
        if (((Object)this.paper_id == null) ? ((Object)other.paper_id != null) : ! (this.paper_id == other.paper_id)) {
            return false;
        }
        if (((Object)this.author_id == null) ? ((Object)other.author_id != null) : !(this.author_id == other.author_id)) {
            return false;
        }
        return true;
    }

    public long getPaperID() {
        return this.paper_id;
    }

    public void setPaperID(long id) {
        this.paper_id = id;
    }

    public long getAuthorID() {
        return this.author_id;
    }

    public void setAuthorID(long id) {
        this.author_id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = 94 * hash + ((Object)this.paper_id != null ? this.hashCode() : 0);
        return hash;
    }
}