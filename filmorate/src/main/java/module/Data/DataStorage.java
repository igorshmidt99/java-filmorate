package module.Data;

import module.Exceptions.Exist.UserExistException;
import module.Components.Film;
import module.Components.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DataStorage {
    public static final Map<Integer, Film> films = new HashMap<>();
    public static final Set<User> users = new HashSet<>();
}