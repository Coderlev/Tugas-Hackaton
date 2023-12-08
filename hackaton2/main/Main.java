package main;

import connection.Connection;
import repository.Repository;
import repository.TeamRepository;
import repository.UserRepository;
import models.Model;
import models.Team;
import models.User;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
    	Connection teamConnection = new Connection("C:\\Users\\Ravel2014\\Desktop\\teams.csv");
    	Connection userConnection = new Connection("C:\\Users\\Ravel2014\\Desktop\\user.csv");
    	Connection mainConnection = new Connection("C:\\Users\\Ravel2014\\Desktop\\database.zip");

        Repository userRepository = new UserRepository();
        Repository teamRepository = new TeamRepository();

        int choice;

        do {
            System.out.println("1. Menu Utama");
            System.out.println("2. Insert Data");
            System.out.println("3. Show");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                   
                    showMainMenu();
                    break;
                case 2:
                    
                    showInsertDataMenu(userRepository, teamRepository, userConnection);
                    break;
                case 3:
                    
                    showShowMenu(userRepository, teamRepository, userConnection);
                    break;
                case 4:
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    private static void showMainMenu() {
        
        System.out.println("This is the Main Menu, Please select Insert or Show.");
    }

    private static void showInsertDataMenu(Repository userRepository, Repository teamRepository, Connection connection) {
        
        System.out.println("Which table to insert? 1. User, 2. Team.");
        int tableChoice = scanner.nextInt();
        scanner.nextLine(); 

        switch (tableChoice) {
            case 1:
                insertUser(userRepository, connection);
                break;
            case 2:
                insertTeam(teamRepository, connection);
                break;
            default:
                System.out.println("Invalid table choice.");
        }
    }

    private static void insertUser(Repository userRepository, Connection connection) {
        System.out.println("Add name:");
        String name = scanner.nextLine();

        System.out.println("Add nim:");
        String nim = scanner.nextLine();

        System.out.println("Add team:");
        String teamName = scanner.nextLine();

        String[] userData = {null, name, nim, teamName}; 
        Model newUser = userRepository.insert(userData, connection);

        if (newUser != null) {
            System.out.println("User created!");
        } else {
            System.out.println("Error: Unable to create user.");
        }
    }

    private static void insertTeam(Repository teamRepository, Connection connection) {
        System.out.println("Add team name:");
        String teamName = scanner.nextLine();

        String[] teamData = {null, teamName}; 
        Model newTeam = teamRepository.insert(teamData, connection);

        if (newTeam != null) {
            System.out.println("Team created!");
        } else {
            System.out.println("Error: Unable to create team.");
        }
    }

    private static void showShowMenu(Repository userRepository, Repository teamRepository, Connection connection) {
       
        System.out.println("Which table to show? 1. User, 2. Team.");
        int tableChoice = scanner.nextInt();
        scanner.nextLine(); 

        switch (tableChoice) {
            case 1:
                
                showUsers(userRepository, connection);
                break;
            case 2:
                
                showTeams(teamRepository, connection);
                break;
            default:
                System.out.println("Invalid table choice.");
        }
    }

    private static void showUsers(Repository userRepository, Connection connection) {
        try {
            
            System.out.println("Want to filter by condition? 1. Yes, 2. No.");
            int filterChoice = scanner.nextInt();
            scanner.nextLine(); 

            String column = null;
            String[] conditions = null;

            if (filterChoice == 1) {
                System.out.println("Add condition, separate by semicolon.");
                String conditionInput = scanner.nextLine();
                String[] conditionParts = conditionInput.split(";");
                if (conditionParts.length == 3) {
                    column = conditionParts[0];
                    conditions = new String[]{conditionParts[1], conditionParts[2]};
                } else {
                    System.out.println("Invalid condition format.");
                    return;
                }
            }

            
            ArrayList<Model> users = userRepository.find(column, conditions, false, null, connection);

            
            if (users != null) {
                if (!users.isEmpty()) {
                    for (Model user : users) {
                        System.out.println(user.toString());
                    }
                } else {
                    System.out.println("No matching users found.");
                }
            } else {
                System.out.println("Error: Unable to read user data.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }




    private static void showTeams(Repository teamRepository, Connection connection) {
        System.out.println("Want to filter by condition? 1. Yes, 2. No.");
        int filterChoice = scanner.nextInt();
        scanner.nextLine(); 

        String column = null;
        String[] conditions = null;

        if (filterChoice == 1) {
            System.out.println("add condition, separate by semicolon.");
            String conditionInput = scanner.nextLine();
            String[] conditionParts = conditionInput.split(";");
            if (conditionParts.length == 3) {
                column = conditionParts[0];
                conditions = new String[]{conditionParts[1], conditionParts[2]};
            } else {
                System.out.println("Invalid condition format.");
                return;
            }
        }

        ArrayList<Model> teams = teamRepository.find(column, conditions, false, null, connection);

        if (teams != null && !teams.isEmpty()) {
            for (Model team : teams) {
                System.out.println(team.toString());
            }
        } else {
            System.out.println("No matching teams found.");
        }
    }
}

