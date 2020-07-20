package com.lagou.edu.service.impl;

import com.lagou.edu.dao.ResumeDao;
import com.lagou.edu.pojo.Resume;
import com.lagou.edu.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeDao resumeDao;

    /**
     * 查询所有
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<Resume> queryAll() throws Exception {
        return resumeDao.findAll();
    }

    /**
     * 查询单个resume
     *
     * @param id
     * @return
     */
    @Override
    public Resume findResumeById(Long id) {
        Optional<Resume> byId = resumeDao.findById(id);
        return byId.get();
    }

    /**
     * 根据id 删除
     * @param id
     */
    @Override
    public void deleteResumeById(Long id) {
        resumeDao.deleteById(id);
    }

    /**
     * 保存resume
     *
     * @param resume
     * @return
     */
    @Override
    public Resume save(Resume resume) {
        return resumeDao.save(resume);
    }

    /**
     * 更新resume
     *
     * @param resume
     * @return
     */
    @Override
    public Resume update(Resume resume) {
        return save(resume);
    }
}
