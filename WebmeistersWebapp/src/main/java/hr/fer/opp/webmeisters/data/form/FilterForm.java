package hr.fer.opp.webmeisters.data.form;

public class FilterForm {

    private boolean prosli;
    private boolean buduci;

    private boolean trajanje0do48;
    private boolean trajanje48do7;
    private boolean trajanje7doDalje;

    private String sort;

    public String getSort() {
        return sort;
    }

    public void setSort(String url) {
        this.sort = url;
    }

    public boolean isProsli() {
        return prosli;
    }

    public void setProsli(boolean prosli) {
        this.prosli = prosli;
    }

    public boolean isBuduci() {
        return buduci;
    }

    public void setBuduci(boolean buduci) {
        this.buduci = buduci;
    }

    public boolean isTrajanje0do48() {
        return trajanje0do48;
    }

    public void setTrajanje0do48(boolean trajanje0do48) {
        this.trajanje0do48 = trajanje0do48;
    }

    public boolean isTrajanje48do7() {
        return trajanje48do7;
    }

    public void setTrajanje48do7(boolean trajanje48do7) {
        this.trajanje48do7 = trajanje48do7;
    }

    public boolean isTrajanje7doDalje() {
        return trajanje7doDalje;
    }

    public void setTrajanje7doDalje(boolean trajanje7doDalje) {
        this.trajanje7doDalje = trajanje7doDalje;
    }

    @Override
    public String toString() {
        return "filter";
    }
}
