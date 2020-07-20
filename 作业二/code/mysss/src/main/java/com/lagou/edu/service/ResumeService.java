package com.lagou.edu.service;

import com.lagou.edu.pojo.Resume;

import java.util.List;

public interface ResumeService {
    List<Resume> queryAll() throws Exception;

    Resume findResumeById(Long id);

    void deleteResumeById(Long id);

    Resume save(Resume resume);

    Resume update(Resume resume);
}
