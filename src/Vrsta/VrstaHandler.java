package Vrsta;

import models.Brod;

public abstract class VrstaHandler {
    protected VrstaHandler nextHandler;

    protected void setNextHandler(VrstaHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void handle(Brod brod){
    }
}
