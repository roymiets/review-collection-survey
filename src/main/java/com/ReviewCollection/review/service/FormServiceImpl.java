package com.ReviewCollection.review.service;

import com.ReviewCollection.review.entity.FormData;
import com.ReviewCollection.review.repository.FormDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormServiceImpl implements FormService{
    @Autowired
    private FormDataRepository formRepository;
    @Override
    public FormData saveForm(FormData form) {
        return formRepository.save(form);
    }

    @Override
    public List<FormData> getAllForms() {
        return formRepository.findAll();
    }

    @Override
    public FormData getFormById(Long formId) {
        return formRepository.findById(formId).orElse(null);
    }

    @Override
    public void deleteForm(Long formId) {
        formRepository.deleteById(formId);
    }

    @Override
    public List<FormData> getAllFormsByUserId(Long userId) {
        return formRepository.findAllByUserId(userId);
    }
}
