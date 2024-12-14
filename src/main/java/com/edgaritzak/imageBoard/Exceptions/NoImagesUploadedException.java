package com.edgaritzak.imageBoard.Exceptions;

public class NoImagesUploadedException extends RuntimeException{

	public NoImagesUploadedException(String message) {
        super(message);
    }

    public NoImagesUploadedException(String message, Throwable cause) {
        super(message, cause);
    }
}
