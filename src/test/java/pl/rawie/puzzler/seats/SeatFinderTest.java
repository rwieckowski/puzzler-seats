package pl.rawie.puzzler.seats;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static pl.rawie.puzzler.seats.AwayMatcher.awayFrom;

public class SeatFinderTest {
    private Seat findSeat(boolean[][] seats, Seat preferred) {
        return new SeatFinder(seats, preferred).find();
    }

    @Test
    public void preferredSeatIsAvailable() {
        boolean[][] seats = createSeatsFromString(
                "...",
                "...",
                "...");
        Seat preferred = new Seat(1, 1);
        Seat seat = findSeat(seats, preferred);
        assertThat(seat, is(equalTo(preferred)));
    }

    @Test
    public void availableSeatIsOneSeatAwayFromPreferred() {
        boolean[][] seats = createSeatsFromString(
                "...",
                ".*.",
                "...");
        Seat preferred = new Seat(1, 1);
        Seat seat = findSeat(seats, preferred);
        assertThat(seat, is(awayFrom(preferred, 1)));
    }

    @Test
    public void lastAvailableSeatIsOneSeatAwayFromPreferred() {
        boolean[][] seats = createSeatsFromString(
                "***",
                "***",
                "*.*");
        Seat preferred = new Seat(1, 1);
        Seat seat = findSeat(seats, preferred);
        assertThat(seat, is(awayFrom(preferred, 1)));
    }

    @Test
    public void availableSeatIsTwoSeatsAwayFromPreferred() {
        boolean[][] seats = createSeatsFromString(
                ".....",
                ".***.",
                ".***.",
                ".***.",
                ".....");
        Seat preferred = new Seat(2, 2);
        Seat seat = findSeat(seats, preferred);
        assertThat(seat, is(awayFrom(preferred, 2)));
    }

    @Test
    public void availableSeatIsInOppositeCorner() {
        boolean[][] seats = createSeatsFromString(
                "***",
                "***",
                "**.");
        Seat preferred = new Seat(0, 0);
        Seat seat = findSeat(seats, preferred);
        assertThat(seat, is(awayFrom(preferred, 2)));
    }

    @Test(expected = AllSeatsTakenException.class)
    public void allSeatsTaken() {
        boolean[][] seats = createSeatsFromString(
                "***",
                "***",
                "***");
        Seat seat = findSeat(seats, new Seat(1, 1));
    }

    private boolean[][] createSeatsFromString(String... rows) {
        boolean[][] seats = new boolean[rows.length][];
        for (int row = 0; row < rows.length; row++) {
            seats[row] = new boolean[rows[row].length()];
            for (int seat = 0; seat < rows[row].length(); seat++)
                seats[row][seat] = rows[row].charAt(seat) == '.';
        }
        return seats;
    }

    private String seatsToString(boolean[][] seats) {
        String s = "";
        for (int row = 0; row < seats.length; row++) {
            for (int seat = 0; seat < seats[row].length; seat++)
                s += seats[row][seat] ? '.' : '*';
            s += '\n';
        }
        return s;
    }
}
