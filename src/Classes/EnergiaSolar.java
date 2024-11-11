package Classes;

import java.util.Date;

public class EnergiaSolar {
    private long ID;
    private Date date;
    private double energiaGerada;
    private long panelID;

    public EnergiaSolar(long ID, Date date, double energiaGerada, long panelID) {
        this.ID = ID;
        this.date = date;
        this.energiaGerada = energiaGerada;
        this.panelID = panelID;
    }

    public long getID() {
        return ID;
    }

    public Date getDate() {
        return date;
    }

    public double getEnergiaGerada() {
        return energiaGerada;
    }

    public long getPanelID() {
        return panelID;
    }
}
