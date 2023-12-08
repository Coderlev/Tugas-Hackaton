package models;

public class User extends Model {
    private int id;
    private String name;
    private String nim;
    private String teamName;

    

    public User() {
    	
    }
    public User(int id, String name, String nim, String teamName) {
        this.id = id;
        this.name = name;
        this.nim = nim;
        this.teamName = teamName;
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

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", nim='" + nim + '\'' + ", teamName='" + teamName + '\'' +  '}';
    }
}

