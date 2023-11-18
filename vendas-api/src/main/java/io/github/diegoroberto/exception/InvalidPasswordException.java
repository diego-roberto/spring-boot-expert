package io.github.diegoroberto.exception;

public class InvalidPasswordException extends RuntimeException{

    public InvalidPasswordException(String msg){
        super(msg);
    }
}
