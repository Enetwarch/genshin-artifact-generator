package com.github.enetwarch.genshinartifactgenerator.data;

public enum Type {

    FLOWER("Flower of Life"),
    PLUME("Plume of Death"),
    SANDS("Sands of Eon"),
    GOBLET("Goblet of Eonothem"),
    CIRCLET("Circlet of Logos");

    private final String artifactType;

    private Type(String artifactType) {
        this.artifactType = artifactType;
    }

    public String getType() {
        return artifactType;
    }

}