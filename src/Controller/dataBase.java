package Controller;

import Model.functions_DAO;
import Model.functions_DAO.User;

public class dataBase {
    
    public static void addPredefinedUsers() {
        functions_DAO.users.add(new User("Ricardo Neto", "Cardiovascular"));
        functions_DAO.users.add(new User("Jane Smith", "Neurosurgery"));
        functions_DAO.users.add(new User("Emily Johnson", "Plastic Surgery"));
    }
    
}