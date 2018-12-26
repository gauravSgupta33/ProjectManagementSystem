package com.cognizant.model;

import com.cognizant.entities.User;

public class UserTestUtil {
	public static User create(Integer id, String firstName, String lastName, int employeeId) {
        User dto = new User();

        dto.setId(id);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setEmpId(employeeId);

        return dto;
    }

    public static User createModelObject(Integer id, String firstName, String lastName, int employeeId) {
        User model = User.getBuilder(firstName, lastName, employeeId).build();

        model.setId(id);

        return model;
    }
}