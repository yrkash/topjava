package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.User;

import java.util.Comparator;

public class UserComparator implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
    }
}
