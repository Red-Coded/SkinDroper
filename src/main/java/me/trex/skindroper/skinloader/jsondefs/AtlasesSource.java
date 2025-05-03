package me.trex.skindroper.skinloader.jsondefs;

import org.checkerframework.common.value.qual.StringVal;

public class AtlasesSource {
    private String type;
    private String source;
    private String prefix;


    /**
     * Atlas Source for packs
     *
     * @param type default is directory
     */
    public AtlasesSource(String type, String source, String prefix) {
        this.type = type;
        this.source = source;
        this.prefix = prefix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
