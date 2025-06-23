package com.pge.sisgal.domain.messages;

public final class BookValidationMessages {

    public static final String TITLE_REQUIRED = "O título é obrigatório";
    public static final String AUTHOR_REQUIRED = "O autor é obrigatório";
    public static final String ISBN_REQUIRED = "O ISBN é obrigatório";
    public static final String AVAILABLE_QUANTITY_REQUIRED = "A quantidade disponível é obrigatória";
    public static final String AVAILABLE_QUANTITY_NEGATIVE = "A quantidade disponível não pode ser negativa";

    public static String bookNotFound(Long id) {
        return String.format("Livro com ID %d não encontrado", id);
    }

    public static String bookNotFoundByIsbn(String isbn) {
        return String.format("Livro com ISBN %s não encontrado", isbn);
    }

    public static String bookAlreadyCreatedWithSameIsbn(String isbn) {
        return String.format("Livro com ISBN %s já foi cadastrado", isbn);
    }

    public static String noAvailableCopies(String title) {
        return String.format("Nenhuma cópia disponível do livro: '%s'", title);
    }

    private BookValidationMessages() {}

}
