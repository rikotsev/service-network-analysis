package analysis.example;

public class User {

    private String id;
    private String name;

    public User() {};

    public User(String id, String name, int age) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
