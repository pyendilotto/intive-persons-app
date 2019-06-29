package com.pieroyendilotto.testyendilotto;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

    private String thumbnail;
    private String large;
    private String username;
    private String firstname;
    private String lastname;
    private String email;


    public Person(String thumbnail, String large, String username, String firstname, String lastname, String email){

        this.thumbnail=thumbnail;
        this.large=large;
        this.username=username;
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;

    }

    public Person(Parcel in){

        this.thumbnail=in.readString();
        this.large=in.readString();
        this.username=in.readString();
        this.firstname=in.readString();
        this.lastname=in.readString();
        this.email=in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        /*dest.writeString(getEquipos());
        dest.writeString(getFecha());
        dest.writeInt(getChecked() ? 1 : 0);*/
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };



    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
