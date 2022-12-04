package br.edu.ifrs.gabrielanceski.oop.model;

public enum SolutionStatus {
    OPEN("Em aberto"), SOLVED("Resolvida"), NOT_SOLVED("Não resolvida");

    private final String text;

    SolutionStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
