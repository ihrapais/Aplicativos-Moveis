package com.example.implementapet;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Pet")
public class Pet {
    @PrimaryKey(autoGenerate = true)
    public int id; // Novo campo ID como chave primária

    @NonNull
    public String cpf; // CPF do dono, agora apenas um atributo
    public String nome; // Nome do PET
    public String telefone; // Telefone do dono

    @Override
    public String toString() {
        return "PET: " + nome + " | CPF do Dono: " + cpf;
    }
}