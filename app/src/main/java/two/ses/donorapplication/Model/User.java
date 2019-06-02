package two.ses.donorapplication.Model;

public class User {
    private String name;
    private String email;
    private String group;
    private String address;
    private Integer phone;

    public User(){

    }

    public User(String name, String email, String group, String address, Integer phone) {
        this.name = name;
        this.email = email;
        this.group = group;
        this.address = address;
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGroup() {
        return group;
    }

    public String getAddress() {
        return address;
    }

    public Integer getPhone() {
        return phone;
    }
//    public void setEventIDS(Event[] events){
//        this.eventIDS = eventIDS;
//    }
//    public String[] getEventIDS() {return eventIDS;}
}
