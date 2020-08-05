package com.example.dogsapplication.view.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DogBreed.class}, version = 1)
public abstract class DogDatabase extends RoomDatabase {
    /* rooom database 는 abstract 타입으로 정의되어야 한다 */

    /* 싱글톤 패턴으로 데이터 베이스 instance 관리 */
    private static DogDatabase instance;

    /* Context : Interface to global information about an application environment. */
    public static DogDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    DogDatabase.class,
                    "dogdatabase")
                    .build();
        }
        return instance;
    }

    public abstract DogDao dogDao();    //dao interface 에 접근
}
