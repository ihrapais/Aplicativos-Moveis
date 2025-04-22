package com.example.implementapet;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PetDaoRoom {
    @Insert
    void inserir(Pet pet);

    @Query("SELECT * FROM Pet")
    List<Pet> obterTodos();

    @Query("SELECT COUNT(*) FROM Pet WHERE cpf = :cpf")
    int cpfExistente(String cpf);
}