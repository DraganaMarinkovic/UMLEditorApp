package raf.dsw.classycraft.app.errorHandler;

import java.time.LocalDateTime;

public class Message {
    private TipPoruke tip;
    private String tekst;
    private LocalDateTime vreme;

    public Message(TipPoruke tip, String tekst, LocalDateTime vreme) {
        this.tip = tip;
        this.tekst = tekst;
        this.vreme = vreme;
    }

    public TipPoruke getTip() {
        return tip;
    }

    public void setTip(TipPoruke tip) {
        this.tip = tip;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public LocalDateTime getVreme() {
        return vreme;
    }

    public void setVreme(LocalDateTime vreme) {
        this.vreme = vreme;
    }
}
