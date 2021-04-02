package uoc.edu.easyorderbackend.model;

import java.util.List;

public class Client extends User {
    private List<Aliment> favouriteAliments;
    private List<Aliment> allergies;

    public Client() {
        super();
    }

    public Client(List<Aliment> favouriteAliments, List<Aliment> allergies) {
        super();
        this.favouriteAliments = favouriteAliments;
        this.allergies = allergies;
    }

    public List<Aliment> getFavouriteAliments() {
        return favouriteAliments;
    }

    public void setFavouriteAliments(List<Aliment> favouriteAliments) {
        this.favouriteAliments = favouriteAliments;
    }

    public Boolean addFavouriteAliments(Aliment aliment) {
        boolean added = false;
        if (!favouriteAliments.contains(aliment)) {
            favouriteAliments.add(aliment);
            added = true;
        }
        return added;
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
