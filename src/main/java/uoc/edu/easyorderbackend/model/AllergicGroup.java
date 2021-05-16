package uoc.edu.easyorderbackend.model;

import java.util.List;

@Deprecated
public class AllergicGroup {
    private String uid;
    private String name;
    private List<Aliment> allergicAliments;

    public AllergicGroup() {
    }

    public AllergicGroup(String uid, String name, List<Aliment> allergicAliments) {
        this.uid = uid;
        this.name = name;
        this.allergicAliments = allergicAliments;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Aliment> getAllergicAliments() {
        return allergicAliments;
    }

    public void setAllergicAliments(List<Aliment> allergicAliments) {
        this.allergicAliments = allergicAliments;
    }

    public boolean addAllergicAliment(Aliment allergicAliment) {
        boolean added = false;
        if(!this.allergicAliments.contains(allergicAliment)) {
            this.allergicAliments.add(allergicAliment);
            added = true;
        }
        return added;
    }
}
