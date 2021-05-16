package uoc.edu.easyorderbackend.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client extends User {
    private String tableId;
    private List<Aliment> allergies;

    public Client() {
        super();
        allergies = new ArrayList<>();
    }

    public Client(String tableId, List<Aliment> allergies) {
        super();
        this.tableId = tableId;
        this.allergies = allergies;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
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
