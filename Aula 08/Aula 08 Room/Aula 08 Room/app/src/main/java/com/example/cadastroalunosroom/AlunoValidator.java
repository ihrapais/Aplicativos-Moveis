package com.example.cadastroalunosroom;

public class AlunoValidator {
    public static boolean isCpfValido(String cpf) {
        return cpf != null && cpf.length() == 11 && cpf.matches("\\d+");
    }

    public static boolean isTelefoneValido(String telefone) {
        return telefone != null && telefone.length() >= 8 && telefone.matches("\\d+");
    }
}