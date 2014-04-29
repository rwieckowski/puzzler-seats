package pl.rawie.puzzler.seats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeatFinder {
    private final boolean[][] seats;
    private final Seat preferred;
    private final int maxDistance;

    public SeatFinder(boolean[][] seats, Seat preferred) {
        this.seats = seats;
        this.preferred = preferred;
        maxDistance = maxSeatsDistance(seats);
    }

    private int maxSeatsDistance(boolean[][] seats) {
        int maxDistance = seats.length;
        for (int i = 0; i < seats.length; i++)
            maxDistance = Math.max(maxDistance, seats[i].length);
        return maxDistance;
    }

    public Seat find() {
        for (int distance = 0; distance < maxDistance; distance++) {
            List<Seat> atDistance = seatsAtDistance(distance);
            List<Seat> available = filterAvailable(atDistance);
            if (!available.isEmpty())
                return available.get(0);
        }
        throw new AllSeatsTakenException();
    }

    private List<Seat> filterAvailable(List<Seat> seats) {
        List<Seat> available = new ArrayList<Seat>();
        for (Seat seat : seats)
            if (isAvailable(seat))
                available.add(seat);
        return available;
    }

    private List<Seat> seatsAtDistance(int distance) {
        if (distance == 0) return Arrays.asList(preferred);
        else {
            List<Seat> seats = new ArrayList<Seat>();
            // four corners
            seats.add(preferred.shift(distance, distance));
            seats.add(preferred.shift(-distance, distance));
            seats.add(preferred.shift(distance, -distance));
            seats.add(preferred.shift(-distance, -distance));
            // four sides
            for (int i = -distance + 1; i <= distance - 1; i++) {
                seats.add(preferred.shift(-distance, i));
                seats.add(preferred.shift(distance, i));
                seats.add(preferred.shift(i, distance));
                seats.add(preferred.shift(i, -distance));
            }
            return seats;
        }
    }

    private boolean isAvailable(Seat seat) {
        if (seat.getRow() >= 0
                && seat.getRow() < seats.length
                && seat.getCol() >= 0
                && seat.getCol() < seats[seat.getRow()].length)
            return seats[seat.getRow()][seat.getCol()];
        else
            return false;
    }
}
