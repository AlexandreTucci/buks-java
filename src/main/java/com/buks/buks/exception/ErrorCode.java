package com.buks.buks.exception;

public enum ErrorCode {

    USER_NOT_FOUND("USER_NOT_FOUND", "Usuário não encontrado."),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", "E-mail já cadastrado."),
    BOOK_NOT_FOUND("BOOK_NOT_FOUND", "Livro não encontrado."),
    ORDER_NOT_FOUND("ORDER_NOT_FOUND", "Pedido não encontrado."),
    INTERNAL_ERROR("INTERNAL_SERVER_ERROR", "Ocorreu um erro inesperado.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
