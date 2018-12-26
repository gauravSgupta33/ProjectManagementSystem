package com.cognizant.model;

import com.cognizant.entities.ParentTask;

public class ParentTaskTestUtil {
	public static ParentTask create(Integer id, String parentTask) {
        ParentTask dto = new ParentTask();

        dto.setParent_task(parentTask);

        return dto;
    }

    public static ParentTask createModelObject(Integer id, String parentTask) {
        ParentTask model = ParentTask.getBuilder(parentTask).build();

        model.setParent_ID(id);

        return model;
    }
}