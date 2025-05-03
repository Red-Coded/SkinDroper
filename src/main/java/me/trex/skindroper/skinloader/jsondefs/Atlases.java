package me.trex.skindroper.skinloader.jsondefs;

import java.util.ArrayList;

public class Atlases {
    private ArrayList<AtlasesSource> sources;

    public Atlases(ArrayList<AtlasesSource> AtlasesSources) {
        this.sources = AtlasesSources;
    }

    public ArrayList<AtlasesSource> getAtlasesSources() {
        return sources;
    }

    public void setAtlasesSources(ArrayList<AtlasesSource> atlasesSources) {
        sources = atlasesSources;
    }
}
