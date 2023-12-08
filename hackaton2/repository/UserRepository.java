package repository;

import connection.Connection;
import models.Model;
import models.User;

import java.util.ArrayList;
import java.util.Arrays;

public class UserRepository implements Repository {
	
	private static final int COLUMN_ID = 0;
    private static final int COLUMN_NAME = 1;
    private static final int COLUMN_NIM = 2;
    private static final int COLUMN_TEAM_NAME = 3;

    @Override
    public ArrayList<Model> find(String column, String[] conditions, boolean join, String joinTable, Connection connection) {
        ArrayList<String[]> userData = connection.readData();
        ArrayList<Model> result = new ArrayList<>();

        for (String[] row : userData) {
            boolean meetsConditions = true;

           
            if (conditions != null) {
                for (String condition : conditions) {
                    String[] parts = condition.split(";");
                    String conditionColumn = parts[0];
                    String operator = parts[1];
                    String value = parts[2];

                    int columnIndex = Arrays.asList(row).indexOf(conditionColumn);

                    if (columnIndex != -1) {
                        String rowData = row[columnIndex];

                        
                        switch (operator) {
                            case "=":
                                meetsConditions &= rowData.equals(value);
                                break;
                           
                        }
                    }
                }
            }

            if (meetsConditions) {
                User user = new User();
                try {
                    user.setId(Integer.parseInt(row[COLUMN_ID])); 
                } catch (NumberFormatException e) {
                   
                    System.out.println("Error parsing ID for user: " + Arrays.toString(row));
                    e.printStackTrace();
                    continue;
                }
                user.setName(row[COLUMN_NAME]); 
                user.setNim(row[COLUMN_NIM]); 
                user.setTeamName(row[COLUMN_TEAM_NAME]); 
                result.add(user);
            }
        }

        return result.isEmpty() ? null : result;
    }

    @Override
    public Model findOne(String column, String[] conditions, boolean join, String joinTable, Connection connection) {
        ArrayList<Model> result = find(column, conditions, join, joinTable, connection);
        return result != null && !result.isEmpty() ? result.get(0) : null;
    }

    @Override
    public Model insert(String[] data, Connection connection) {
        ArrayList<String[]> userData = connection.readData();

        
        int newId = userData.size() + 1;
        data[0] = String.valueOf(newId);

        userData.add(data);
        connection.writeData(userData);

        User newUser = new User();
        newUser.setId(newId);
        newUser.setNim(data[0]); 
        newUser.setName(data[1]); 
        newUser.setTeamName(data[3]); 

        return newUser;
    }
}

