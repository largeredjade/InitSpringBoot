package com.likelion.lionlib.exception;

public class LoanNotFoundException extends RuntimeException{
    public LoanNotFoundException(){
        super("Loan Not Found");
    }

    public LoanNotFoundException(Long loanId) {
        super("Loan: " + loanId + " not found");
    }
}
