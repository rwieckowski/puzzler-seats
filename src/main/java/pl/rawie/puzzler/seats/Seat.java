package pl.rawie.puzzler.seats;

public class Seat {
    private final int row;
    private final int col;

    public Seat(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Seat shift(int dy, int dx) {
        return new Seat(row + dy, col + dx);
    }

    @Override
    public String toString() {
        return "Seat{row=" + row + ", col=" + col + '}';
    }
}
