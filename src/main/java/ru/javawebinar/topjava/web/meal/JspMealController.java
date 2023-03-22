package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Objects;

@Controller
@RequestMapping("/meals")
public class JspMealController {

    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    public JspMealController(MealService service) {
        this.service = service;
    }

//    , @RequestParam(value = "userId", defaultValue = "false") int userId
    @GetMapping()
    public String getMeals(Model model) {
        log.info("meals");
        int userId = SecurityUtil.authUserId();
        model.addAttribute("meals", service.getAll(userId));
        return "meals";
    }

    @PostMapping("/update/{id}")
    public String update(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        int id = Integer.parseInt(paramId);
        int userId = SecurityUtil.authUserId();
        service.update(service.get(id,userId),userId);
        return "redirect:/meals";
    }

    @PostMapping()
    public String create(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        int userId = SecurityUtil.authUserId();

        service.create(meal,userId);
        return "redirect:/meals";

    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        int userId = SecurityUtil.authUserId();
        service.delete(id, userId);
        return "redirect:/meals";
    }

}
