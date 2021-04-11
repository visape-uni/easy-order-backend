package uoc.edu.easyorderbackend.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client extends User {
    private List<Aliment> allergies;

    public Client() {
        super();
        allergies = new ArrayList<>();
    }

    public Client(List<Aliment> allergies) {
        super();
        this.allergies = allergies;
    }

    public List<Aliment> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<Aliment> allergies) {
        this.allergies = allergies;
    }

    public Boolean addAllergicAliment(Aliment allergicAliment) {
        boolean added = false;
        if (!allergies.contains(allergicAliment)) {
            allergies.add(allergicAliment);
            added = true;
        }
        return added;
    }
}
