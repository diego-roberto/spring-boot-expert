package io.github.diegoroberto.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException() {
        super("Pedido não encontrado.");
    }

}
