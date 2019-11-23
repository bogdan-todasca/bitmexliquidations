package business.manager;

import business.Venue;

import java.util.Objects;

public class Subscription {
    final Venue venue;
    final String code;

    public Subscription(Venue venue, String code) {
        this.venue = venue;
        this.code = code;
    }

    public Venue getVenue() {
        return venue;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return venue == that.venue &&
                Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(venue, code);
    }
}
