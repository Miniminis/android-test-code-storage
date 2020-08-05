package com.example.dogsapplication.view.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity     //DB에게 entity 임을 알려주는 어노테이션
public class DogBreed {

    @ColumnInfo(name = "breed_Id") //변수이름과 room colum 이름이 다를 경우,
    @SerializedName("id")   //json key 이름과 다를 경우 serialized 통해서 매핑
    private String breedId;

    @ColumnInfo(name = "dog_name")
    @SerializedName("name")
    private String dogBreed;

    @ColumnInfo(name = "life_span")
    @SerializedName("life_span")
    private String lifeSpan;

    @ColumnInfo(name = "breed_group")
    @SerializedName("breed_group")
    private String breedGroup;

    @ColumnInfo(name = "bred_for")
    @SerializedName("bred_for")
    private String bredFore;

    private String temperament;

    @ColumnInfo(name = "image_url")
    @SerializedName("url")
    private String imageUrl;

    @PrimaryKey(autoGenerate = true)
    private int uui;    //나중에 db에서 받아와서 매핑해줘야함

    public DogBreed(String breedId, String dogBreed, String lifeSpan,
                    String breedGroup, String bredFore, String temperament,
                    String imageUrl) {
        this.breedId = breedId;
        this.dogBreed = dogBreed;
        this.lifeSpan = lifeSpan;
        this.breedGroup = breedGroup;
        this.bredFore = bredFore;
        this.temperament = temperament;
        this.imageUrl = imageUrl;
    }

    public String getBreedId() {
        return breedId;
    }

    public void setBreedId(String breedId) {
        this.breedId = breedId;
    }

    public String getDogBreed() {
        return dogBreed;
    }

    public void setDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(String lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public String getBreedGroup() {
        return breedGroup;
    }

    public void setBreedGroup(String breedGroup) {
        this.breedGroup = breedGroup;
    }

    public String getBredFore() {
        return bredFore;
    }

    public void setBredFore(String bredFore) {
        this.bredFore = bredFore;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getUui() {
        return uui;
    }

    public void setUui(int uui) {
        this.uui = uui;
    }
}
