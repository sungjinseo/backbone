package dev.greatseo.backbone.global.error.exception;

public class DuplicatedException extends RuntimeException{
	
	public DuplicatedException(String msg, Throwable t) {
        super(msg, t);
    }

    public DuplicatedException(String msg) {
        super(msg);
    }

    public DuplicatedException() {
        super();
    }
}
