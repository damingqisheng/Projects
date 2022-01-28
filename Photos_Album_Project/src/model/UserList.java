package model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The user list class
 */
public class UserList implements Serializable {
    /**
     * the list.
     */
    private List<String> _users;

    /**
     * construction.
     */
    public UserList() {
        _users = new ArrayList<String>();
    }

    /**
     * return if a string is in the list.
     * @param s the string
     * @return true for in the list, otherwise false.
     */
    public boolean isInUserList(String s) {
        return _users.contains(s);
    }


    /**
     * add a string in the list.
     * @param s the string
     */
    public void addUser(String s) {
        if (!_users.contains(s)) {
            _users.add(s);
        }
    }

    /**
     * remove from the list.
     * @param s the string to be removed
     */
    public void removeUser(String s) {
        if (_users.contains(s)) {
            _users.remove(s);
        }
    }

    /**
     * return the list.
     * @return the list
     */
    public List<String> getList() {
        return _users;
    }

    /**
     * return the size of the list
     * @return the size
     */
    public int size(){
        return _users.size();
    }
}
