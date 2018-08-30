package common;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomerData {
    private int id;
    private String name;
    private String address;
    private String socialSecurityNo;
    private int zipCode;


public CustomerData(){

}

public CustomerData(String name, String address, String socialSecurityNo, int zipCode){
    this.name = name;
    this.address = address;
    this.socialSecurityNo = socialSecurityNo;
    this.zipCode = zipCode;
}


    public CustomerData(String name, String address, String socialSecurityNo, int zipCode, int id){
        this.name = name;
        this.address = address;
        this.socialSecurityNo = socialSecurityNo;
        this.zipCode = zipCode;
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getSocialSecurityNo() {
        return socialSecurityNo;
    }

    public void setSocialSecurityNo(String socialSecurityNo) {
        this.socialSecurityNo = socialSecurityNo;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }
}
