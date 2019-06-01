package two.ses.donorapplication.Model;

public class Charity {
    private String charity_id;
    private String name;
    private String location;
    private String category;
    private String suburb;

    public Charity() {

    }

    public Charity(String charity_id, String name, String location, String category, String suburb) {
        this.charity_id = charity_id;
        this.name = name;
        this.location = location;
        this.category = category;
        this.suburb = suburb;
    }

    public String getCharity_id() {
        return charity_id;
    }

    public void setCharity_id(String charity_id) {
        this.charity_id = charity_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getSuburb() {
        return suburb;
    }

    @Override
    public String toString() {
        return "Charity{" +
                "charity_id='" + charity_id + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", category='" + category + '\'' +
                ", suburb='" + suburb + '\'' +
                '}';
    }

}


