package two.ses.donorapplication.Model;

import java.io.Serializable;

public class CharityModel implements Serializable {
    String name, charityType, description, contact, address, email;

    public CharityModel() {
    }

    public CharityModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public CharityModel(String name, String  email, String contact, String address) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.address = address;
    }

    public CharityModel(String name, String charityType , String description, String contact, String address) {
        this.name = name;
        this.charityType  = charityType ;
        this.description = description;
        this.contact = contact;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharityType() {
        return charityType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCharityType(String charityType) {
        this.charityType = charityType;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }
}
