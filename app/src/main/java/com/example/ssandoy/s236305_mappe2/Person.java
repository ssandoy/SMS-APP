package com.example.ssandoy.s236305_mappe2;

import java.util.Calendar;

/**
 * Created by ssandoy on 07.10.2016.
 */
public class Person { //TODO: BENYTTE ANNET NAVN?

    private int _ID;
    private String firstName;
    private String lastName;
    private String phoneNumber; //TODO: INT OR STRING?
    private Calendar birthday; //TODO BRUKE DATE OR LOCALDATE?

    public Person(int _ID, String firstName, String lastName, String phoneNumber, Calendar birthday) {
        this._ID = _ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    public Person() {

    }

    //TODO: FLERE KONSTRUKTØRER?

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

}
