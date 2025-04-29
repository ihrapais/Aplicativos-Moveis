package com.example.supermercadoapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Produto")
public class Produto {
    @PrimaryKey(autoGenerate = true)
    public int id; // Chave primária autoincremental

    public String nome; // Nome do produto
    public double preco; // Preço do produto
    public String categoria; // Categoria do produto

    @Override
    public String toString() {
        return "Produto: " + nome + " | Preço: R$" + preco + " | Categoria: " + categoria;
    }
}