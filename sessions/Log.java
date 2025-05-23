package sessions;


public class Log {
    private int minute;
    private int ore;
    private int secunde;
    private int pagini;
    public Log(int minute, int ore, int secunde, int pagini) {
        this.minute = minute;
        this.ore=ore;
        this.secunde=secunde;
        this.pagini=pagini;
    }
    public int getPagini() {
        return pagini;
    }

    public void setPagini(int pagini) {
        this.pagini = pagini;
    }

    public int getSecunde() {
        return secunde;
    }

    public void setSecunde(int secunde) {
        this.secunde = secunde;
    }

    public int getOre() {
        return ore;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setOre(int ore) {
        this.ore = ore;
    }

    public int getMinute() {
        return minute;
    }
    public String getFormattedTime() {
        if (ore== 0) {
            if (minute == 0) {
                return secunde + "s";
            }
            else return minute + "m " + secunde + "s";
        }else if (minute == 0) {
            if(secunde==0)
            {
                return ore + "h ";
            }
            else return ore+"h "+secunde+"s";
        }else if(secunde==0)
        {
            return ore+"h "+minute+"m";
        }
        else {
            return ore + "h " + minute + "m "+ secunde+"s";
        }
    }
}
