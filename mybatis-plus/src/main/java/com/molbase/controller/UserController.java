package com.molbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.molbase.model.system.User;
import com.molbase.service.system.IUserService;

/**
 * 
 * @date 2017年1月20日 下午5:35:08
 * @author lanlong.li
 * @desc
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/list")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        modelAndView.addObject("userList", userService.selectList(null));
        return modelAndView;
    }

    @RequestMapping("/preSave")
    public ModelAndView preSave(ModelAndView modelAndView, @RequestParam(value = "id", required = false) Long id) {
        modelAndView.setViewName("save");
        if (id != null) {
            modelAndView.addObject("user", userService.selectById(id));
        }
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/save")
    public Object save(User user) {
        if (user.getId() == null) {
            return userService.insert(user) ? renderSuccess("添加成功") : renderError("添加失败");
        } else {
            return userService.updateById(user) ? renderSuccess("修改成功") : renderError("修改失败");
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Object delete(@RequestParam(value = "id", required = false) Long id) {
        return userService.deleteById(id) ? renderSuccess("删除成功") : renderError("删除失败");
    }
}
