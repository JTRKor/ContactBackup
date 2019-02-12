package com.amazonaws.compute.ap_northeast_2.ec2_54_180_85_255.contactbackup.data;

public class AddressOne {
    private Integer id;
    private String name;
    private String number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public AddressOne(Integer id, String name, String number) {

        this.id = id;
        this.name = name;
        this.number = number;
    }

    public AddressOne(String name, String number) {

        this.name = name;
        this.number = number;
    }

}
