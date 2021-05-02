package uoc.edu.easyorderbackend.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uoc.edu.easyorderbackend.constants.EasyOrderConstants;
import uoc.edu.easyorderbackend.constants.UrlEasyOrderConstants;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.model.Table;
import uoc.edu.easyorderbackend.service.TableService;

import java.util.List;

@RestController
@RequestMapping(UrlEasyOrderConstants.tableUrl)
public class TableController {
    private final static Logger logger = LoggerFactory.getLogger(TableController.class);

    private TableService tableService;

    @PostMapping(UrlEasyOrderConstants.createUrl)
    public ResponseEntity<Table> createTable(@RequestBody Table table) {
        logger.info("TableController: Create Table");
        ResponseEntity<Table> response;
        if (table != null && StringUtils.isNotBlank(table.getUid())
        && StringUtils.isNotBlank(table.getState())) {
            String[] uidSplit = table.getUid().split("/");
            if (uidSplit.length == 2) {
                String restaurantId = uidSplit[0];
                String tableId = uidSplit[1];
                table.setUid(tableId);
                Table newTable = tableService.createTable(restaurantId, table);
                response = new ResponseEntity<>(newTable, HttpStatus.OK);
            } else {
                throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "Invalid ID");
            }
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "Any mandatory camp of the Table is empty");
        }

        logger.info("TableController: Giving response");
        return response;
    }

    @GetMapping(UrlEasyOrderConstants.getAllTables)
    public ResponseEntity<List<Table>> getAllTables(@PathVariable String restaurantId) {
        logger.info("TableController: get all tables from restaurant");
        ResponseEntity<List<Table>> response;
        if (StringUtils.isNotBlank(restaurantId)) {
            List<Table> tableList = tableService.getTablesFromRestaurant(restaurantId);
            response = new ResponseEntity<>(tableList, HttpStatus.OK);
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "Invalid ID");
        }
        logger.info("TableController: Giving response");
        return response;
    }

    @PutMapping(UrlEasyOrderConstants.changeTableState)
    public ResponseEntity<Table> changeTableState (@PathVariable String restaurantId, @PathVariable String tableId, @RequestBody String newState) {
        logger.info("TableController: Change table state from restaurant");
        ResponseEntity<Table> response;

        if (StringUtils.isNotBlank(newState) && correctState(newState)) {
            if (StringUtils.isNotBlank(restaurantId) && StringUtils.isNotBlank(tableId)) {
                Table table = tableService.changeState(restaurantId, tableId, newState);
                response = new ResponseEntity<>(table, HttpStatus.OK);
            } else {
                throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "Invalid ID");
            }
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "Invalid State");
        }
        logger.info("TableController: Giving response");
        return response;
    }

    private boolean correctState(String newState) {
        return (newState.equals(EasyOrderConstants.emptyTableState) || newState.equals(EasyOrderConstants.occupiedTableState));
    }

    @Autowired
    public void setTableService(TableService tableService) {
        this.tableService = tableService;
    }
}
