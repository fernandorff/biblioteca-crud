package com.pge.sisgal.domain.messages;

public final class UserValidationMessages {

    public static final String NAME_REQUIRED = "O nome é obrigatório";
    public static final String REGISTRATION_REQUIRED = "A matrícula é obrigatória";
    public static final String EMAIL_REQUIRED = "O e-mail é obrigatório";
    public static final String PASSWORD_REQUIRED = "A senha é obrigatória";
    public static final String ROLE_REQUIRED = "O papel (role) é obrigatório";
    public static final String EMAIL_INVALID = "Formato de e-mail inválido";

    public static String userNotFoundById(Long id) {
        return String.format("Usuário com ID %d não encontrado", id);
    }

    public static String userNotFoundByRegistration(String registration) {
        return String.format("Usuário com matrícula %s não encontrado", registration);
    }

    public static String userAlreadyCreatedWithSameRegistration(String registration) {
        return String.format("Usuário com matrícula %s já foi cadastrado", registration);
    }

    public static String invalidRole(String role) {
        return String.format("Papel inválido: %s. Valores válidos: %s", role, String.join(", ", "USER", "ADMIN"));
    }

    private UserValidationMessages() {}

}
