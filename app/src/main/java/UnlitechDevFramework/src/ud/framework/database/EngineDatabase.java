/**
 * Author: Ronald Phillip C. Cui
 * Date: Oct 12, 2013
 */
package UnlitechDevFramework.src.ud.framework.database;

import java.util.ArrayList;

public class EngineDatabase {

    private String DBName;
    private int DBVersion;
    private ArrayList<Table> tables = new ArrayList<Table>();

    /**
     * @param dbname    - database name
     * @param dbversion - database version
     */
    public EngineDatabase(String dbname, int dbversion) {
        DBName = dbname;
        DBVersion = dbversion;
    }

    /**
     * @return database name
     */
    public String getName() {
        return DBName;
    }

    /**
     * @return database version
     */
    public int getVersion() {
        return DBVersion;
    }

    /**
     * @return an arraylist of tables to be created
     */
    public ArrayList<Table> getTables() {
        return tables;
    }

    /**
     * @param table - table to be added to the list
     * @return instance of the EngineDatabase Class
     */
    public EngineDatabase addTable(Table table) {
        tables.add(table);
        return this;
    }
}
