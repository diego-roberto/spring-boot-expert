package io.github.diegoroberto.constant;

public class MessageConstants {

    private MessageConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String MSG_AUTH_UNAUTHORIZED="Não autorizado";
    public static final String MSG_AUTH_LOGIN_NOT_FOUND="Login não encontrado";
    public static final String MSG_AUTH_INVALID_PASSWORD="Senha inválida";
    public static final String MSG_USER_SUCCESS="Usuário criado com sucesso";

    public static final String MSG_VALIDATION_ERROR="Erro de validação";

    public static final String MSG_PRODUCT_FOUND="Produto encontrado";
    public static final String MSG_PRODUCT_NOT_FOUND="Produto não encontrado";
    public static final String MSG_PRODUCT_NONE_FOUND="Nenhum produto encontrado";
    public static final String MSG_PRODUCT_SUCCESS="Produto salvo com sucesso";

    public static final String MSG_ORDER_FOUND="Pedido encontrado";
    public static final String MSG_ORDER_NOT_FOUND="Pedido não encontrado";
    public static final String MSG_ORDER_SUCCESS="Pedido salvo com sucesso";

    public static final String MSG_CLIENT_FOUND="Cliente encontrado";
    public static final String MSG_CLIENT_NOT_FOUND="Cliente não encontrado";
    public static final String MSG_CLIENT_NONE_FOUND="Nenhum cliente encontrado";
    public static final String MSG_CLIENT_SUCCESS="Cliente salvo com sucesso";

}
