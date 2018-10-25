package br.com.udacity.ruyano.filmesfamosos.util;

public enum ImageQuality {
    LOW("w154"),
    MEDIUM("w185"),
    ORIGINAL("original");

    private String value;

    public String getValue() {
        return value;
    }

    ImageQuality(String value) {
        this.value = value;
    }
}
