package uoc.edu.easyorderbackend.model;

import java.util.ArrayList;
import java.util.List;

public class Worker extends User {
    private List<Table> tableAlerts;

    public Worker() {
        tableAlerts  = new ArrayList<>();
    }

    public Worker(List<Table> tableAlerts) {
        this.tableAlerts = tableAlerts;
    }

    public List<Table> getTableAlerts() {
        return tableAlerts;
    }

    public void setTableAlerts(List<Table> tableAlerts) {
        this.tableAlerts = tableAlerts;
    }

    public boolean addTableAlert(Table tableAlert) {
        boolean added = false;
        if (!tableAlerts.contains(tableAlert)) {
            tableAlerts.add(tableAlert);
            added = true;
        }
        return added;
    }

    public boolean removeTableAlert(Table tableAlert) {
        return tableAlerts.remove(tableAlert);
    }
}
