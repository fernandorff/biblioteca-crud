package com.pge.sisgal.domain.messages;

public final class LoanValidationMessages {

    public static final String BOOK_ID_REQUIRED = "O ID do livro é obrigatório";
    public static final String BOOK_REQUIRED = "O livro é obrigatório";
    public static final String USER_ID_REQUIRED = "O ID do usuário é obrigatório";
    public static final String USER_REQUIRED = "O usuário é obrigatório";
    public static final String START_DATE_REQUIRED = "A data de início é obrigatória";
    public static final String DUE_DATE_REQUIRED = "A data de vencimento é obrigatória";
    public static final String LOAN_ID_REQUIRED = "O ID do empréstimo é obrigatório";
    public static final String RETURN_DATE_REQUIRED = "A data de devolução é obrigatória";

    public static final String RETURN_DATE_BEFORE_START_DATE = "A data de devolução não pode ser anterior à data de início";
    public static final String START_DATE_AFTER_CURRENT_DATE = "A data de início não pode ser posterior à data atual";
    public static final String LOAN_ALREADY_RETURNED = "O empréstimo já foi devolvido";
    public static final String DUE_DATE_BEFORE_START_DATE = "A data de vencimento não pode ser anterior à data de início";

    public static String loanNotFoundById(Long id) {
        return String.format("Empréstimo com ID %d não encontrado", id);
    }

    public static String loanMaxLimitReached(int maxLoans) {
        return String.format("O usuário atingiu o limite máximo de empréstimos: %d", maxLoans);
    }

    public static String loanDueDateExceeded(int maxDays) {
        return String.format("A data de vencimento não pode exceder %d dias a partir da data de início", maxDays);
    }

    private LoanValidationMessages() {}
}
