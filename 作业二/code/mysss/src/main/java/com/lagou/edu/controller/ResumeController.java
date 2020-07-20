package com.lagou.edu.controller;

import com.lagou.edu.pojo.Resume;
import com.lagou.edu.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/resume")
public class ResumeController {

    /**
     * Spring容器和SpringMVC容器是有层次的（父子容器）
     * Spring容器：service对象+dao对象
     * SpringMVC容器：controller对象，，，，可以引用到Spring容器中的对象
     */
    @Autowired
    private ResumeService resumeService;

    /**
     * 查询所有
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView queryAll() throws Exception {
        System.out.println("queryAll");
        List<Resume> resumes = resumeService.queryAll();
        // 封装了数据和页面信息的 ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list", resumes);
        // 视图信息(封装跳转的页面信息) 逻辑视图名
        modelAndView.setViewName("resume");
        return modelAndView;
    }

    /**
     * 根据id删除resume
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ModelAndView deleteResumeById(@PathVariable("id") Long id) throws Exception {
        System.out.println("deleteResumeById:" + id);
        resumeService.deleteResumeById(id);
        // 跳转到查询接口
        return queryAll();
    }

    /**
     * 根据id查询resume
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView findResumeById(@PathVariable("id") Long id) throws Exception {
        System.out.println("findResumeById:" + id);
        Resume resume = resumeService.findResumeById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("resume", resume);
        // 跳转到编辑页面
        modelAndView.setViewName("editResume");
        return modelAndView;
    }

    /**
     * 更新resume
     *
     * @param resume
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ModelAndView updateResume(Resume resume) throws Exception {
        System.out.println("updateResume");
        resumeService.update(resume);
        // 跳转到查询接口
        return queryAll();
    }

    /**
     * 保存resume
     *
     * @param resume
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ModelAndView saveResume(Resume resume) throws Exception {
        System.out.println("save");
        resumeService.save(resume);
        // 跳转到查询接口
        return queryAll();
    }

    /**
     * 跳转到新增resumt的jsp
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "redirectToSave", method = RequestMethod.GET)
    public ModelAndView redirectToSave() throws Exception {
        System.out.println("redirectToSave");
        ModelAndView modelAndView = new ModelAndView();
        // 跳转到编辑页面
        modelAndView.setViewName("saveResume");
        return modelAndView;
    }

}
