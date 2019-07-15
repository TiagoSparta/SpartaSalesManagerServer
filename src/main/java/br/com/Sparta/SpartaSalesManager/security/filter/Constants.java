package br.com.Sparta.SpartaSalesManager.security.filter;

public enum Constants {
    SECRET("secre"),
    TOKEN_PREFIX("Bearer"),
    HEADER_STRING("Authorization"),
    EXPIRATION_TIME(86400000L);

    private String constant;
    private long constantTime;

    Constants(String constantConstuctor) {
        this.constant = constantConstuctor;
    }

    Constants(long constantTimeConstructor) {
        this.constantTime = constantTimeConstructor;
    }

    public String getConstant() {
        return constant;
    }

    public long getConstantTime() {
        return constantTime;
    }
}
