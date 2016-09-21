package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Question;
import wad.domain.QuestionRepository;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepo;

    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("questions", questionRepo.findAll());
        return "questions";
    }

    @RequestMapping(value = "/questions", method = RequestMethod.POST)
    public String addQuestion(@RequestParam String title,
            @RequestParam String content) {
        Question question = new Question();
        question.setContent(content);
        question.setTitle(title);
        questionRepo.save(question);

        return "redirect:/questions";
    }

}
