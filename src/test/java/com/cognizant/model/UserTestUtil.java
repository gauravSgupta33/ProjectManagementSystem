package com.cognizant.model;

import com.cognizant.entities.User;

public class UserTestUtil {
	public static User create(Integer id, String firstName, String lastName) {
        User dto = new User();

        dto.setId(id);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);

        return dto;
    }

    public static User createModelObject(Integer id, String firstName, String lastName) {
        User model = User.getBuilder(firstName, lastName).build();

        model.setId(id);

        return model;
    }
}