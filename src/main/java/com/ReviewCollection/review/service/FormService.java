package com.ReviewCollection.review.service;

import com.ReviewCollection.review.entity.FormData;

import java.util.List;

public interface FormService {
    FormData saveForm(FormData form);
    List<FormData> getAllForms();
    FormData getFormById(Long formId);
    void deleteForm(Long formId);

    List<FormData> getAllFormsByUserId(Long userId);

}
