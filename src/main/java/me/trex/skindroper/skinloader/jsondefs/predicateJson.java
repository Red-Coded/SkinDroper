package me.trex.skindroper.skinloader.jsondefs;

public class predicateJson {
    private cmdJson predicate;
    private String model;

    public predicateJson(int CMD,String model) {
        this.predicate = new cmdJson(CMD);
        this.model = model;
    }

    public cmdJson getPredicate() {
        return predicate;
    }

    public void setPredicate(cmdJson predicate) {
        this.predicate = predicate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
