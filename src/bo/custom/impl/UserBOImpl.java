package bo.custom.impl;

import bo.custom.UserBO;
import dao.DAOFactory;
import dao.custom.UserDAO;
import dto.UserDTO;
import entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    private final UserDAO userDAO = (UserDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        ArrayList<UserDTO> allUsers = new ArrayList<>();
        ArrayList<User> all = userDAO.getAll();
        for (User user : all) {
            allUsers.add(new UserDTO(user.getUsername(), user.getPassword(), false));
        }
        return allUsers;
    }
}
