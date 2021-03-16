package com.effective.java.ch12.item88;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * @author han
 */
public final class Period implements Serializable {
    private Date start;
    private Date end;

    public Period(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
        if (this.start.compareTo(this.end) > 0) {
            throw new IllegalArgumentException();
        }
    }

    public Date getStart() {
        return new Date(start.getTime());
    }

    public Date getEnd() {
        return new Date(end.getTime());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();

        // 클라이언트가 소유해서는 안되는 객체 잠조를 갖는 필드는 항상 방어적 복사를 해야함
        start = new Date(start.getTime());
        end = new Date(end.getTime());

        if (start.compareTo(end) > 0) {
            throw new InvalidObjectException(start + "가 " + end + "보다 늦다.");
        }
    }

    @Override
    public String toString() {
        return start + " - " + end;
    }
}
