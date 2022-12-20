package dev.greatseo.backbone.global.response;


import lombok.Getter;

@Getter
public class Existence {

    private boolean existence;

    public Existence(boolean existence) {
        this.existence = existence;
    }
}
