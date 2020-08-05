package com.example.dogsapplication.view.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DogDao {

    @Insert
    List<Long> insertAll(DogBreed... dogs);      //return primary key for DogBreeds

    @Query("SELECT * FROM dogbreed")
    List<DogBreed> getAllDogs();

    @Query("SELECT * FROM dogbreed WHERE uui = :dogId")
    DogBreed getDog(int dogId);

    @Query("DELETE FROM dogbreed")
    void deleteAllDogs();

}
