package me.trex.skindroper.skinloader.jsondefs;


public class TextureJson {

    private String parent;
    private TextureStructure textures;

    public TextureJson(String parent, TextureStructure textures) {
        this.parent = parent;
        this.textures = textures;
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
}