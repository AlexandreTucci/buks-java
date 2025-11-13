package com.buks.buks.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // === Usuário / Autenticação ===
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "Usuário não encontrado."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "EMAIL_ALREADY_EXISTS", "E-mail já cadastrado."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS", "Credenciais inválidas."),
    NO_USERS_FOUND(HttpStatus.NOT_FOUND, "NO_USERS_FOUND", "Nenhum usuário encontrado."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "INVALID_REQUEST", "Os dados enviados são inválidos."),

    // === Livro ===
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOK_NOT_FOUND", "Livro não encontrado."),
    INVALID_BOOK_DATA(HttpStatus.BAD_REQUEST, "INVALID_BOOK_DATA", "Os dados do livro são inválidos."),

    // === Avaliação ===
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW_NOT_FOUND", "Avaliação não encontrada."),
    NO_REVIEWS_FOUND(HttpStatus.NOT_FOUND, "NO_REVIEWS_FOUND", "Nenhuma avaliação cadastrada."),

    // === Pedido ===
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER_NOT_FOUND", "Pedido não encontrado."),

    // === Pagamento ===
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "PAYMENT_NOT_FOUND", "Pagamento não encontrado."),
    ORDER_ALREADY_PAID(HttpStatus.CONFLICT, "ORDER_ALREADY_PAID", "Este pedido já possui um pagamento realizado."),
    PAYMENT_VALUE_MISMATCH(HttpStatus.BAD_REQUEST, "PAYMENT_VALUE_MISMATCH", "O valor do pagamento diverge do pedido (opcional implementar)."),

    // === Sistema ===
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Falha na validação dos dados enviados."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "Ocorreu um erro inesperado.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
