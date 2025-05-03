package me.trex.skindroper.skinloader.jsondefs;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MasterJson {

    private String parent;
    private TextureStructure textures;
    private ArrayList<predicateJson> overrides;

    public MasterJson(String parent, String textures, ArrayList<predicateJson> overrides) {
        this.parent = parent;
        this.textures = new TextureStructure(textures);
        this.overrides = overrides;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public TextureStructure getTextures() {
        return textures;
    }

    public void setTextures(TextureStructure textures) {
        this.textures = textures;
    }

    public ArrayList<predicateJson> getoverrides() {
        return overrides;
    }

    public void setoverrides(ArrayList<predicateJson> overrides) {
        this.overrides = overrides;
    }
}