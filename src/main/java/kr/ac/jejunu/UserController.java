package kr.ac.jejunu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class UserController {
    @GetMapping(path = "/user/{id}/{name}", produces = "application/json")
    public ModelAndView user(
            @PathVariable("id") Integer id
            , @PathVariable("name") String name
    ) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject(user);
        return modelAndView;
    }

//    @GetMapping(value = "modelattribute", produces = "application/json")
//    public String modelAttribute(@ModelAttribute User user){
//        user.setName("오희주");
//        return "redirect:/upload";
//    }

    @RequestMapping("/upload")
    public void upload() {

    }
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public ModelAndView upload(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        File path = new File(request
                .getServletContext().getRealPath("/") + "/WEB-INF/static/" + file.getOriginalFilename());

        FileOutputStream fileOutputStream = new FileOutputStream(path);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(file.getBytes());
        bufferedOutputStream.close();
        ModelAndView modelAndView = new ModelAndView("upload");
        modelAndView.addObject("url", "/images/" + file.getOriginalFilename());
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView error(Exception e) {
        ModelAndView modelAndView =  new ModelAndView("error");
        modelAndView.addObject("e", e);
        return modelAndView;
    }
}
