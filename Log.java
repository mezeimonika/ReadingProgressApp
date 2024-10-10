public class Log {
    private int timp;

    public Log(int timp) {
        this.timp = timp;
    }

    public int getMinute() {
        return timp;
    }

    public String toString()
    {
        int ore = timp / 60;
        int minute = timp % 60;
        if (ore > 0) {
            return ore + " ore și " + minute + " minute";
        } else {
            return minute + " minute";
        }
    }
}
