package pl.rawie.puzzler.seats;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class AwayMatcher extends TypeSafeMatcher<Seat> {
    private Seat seat;
    private int distance;

    private AwayMatcher(Seat seat, int distance) {
        this.seat = seat;
        this.distance = distance;
    }

    @Override
    protected boolean matchesSafely(Seat item) {
        return seatDistance(item, seat) == distance;
    }

    private int seatDistance(Seat one, Seat other) {
        return Math.max(
                distanceFor(one.getRow(), other.getRow()),
                distanceFor(one.getCol(), other.getCol()));
    }

    private int distanceFor(int from, int to) {
        return Math.abs(from - to);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("at a distance ");
        description.appendValue(distance);
        description.appendText(" from ");
        description.appendValue(seat);
    }

    @Override
    protected void describeMismatchSafely(Seat item, Description description) {
        description.appendText("was ");
        description.appendValue(seatDistance(item, seat));
    }

    @Factory
    public static Matcher<Seat> awayFrom(Seat seat, int distance) {
        return new AwayMatcher(seat, distance);
    }
}
