package wad.controller;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Exam;
import wad.domain.ExamRepository;
import wad.domain.Question;
import wad.domain.QuestionRepository;

@Controller
public class ExamController {

    @Autowired
    private ExamRepository examRepo;
    @Autowired
    private QuestionRepository questionRepo;

    @RequestMapping(value = "/exams", method = RequestMethod.GET)
    public String listExams(Model model) {
        model.addAttribute("exams", examRepo.findAll());
        return "exams";
    }

    @RequestMapping(value = "/exams/{id}", method = RequestMethod.GET)
    public String viewExam(Model model, @PathVariable Long id) {
        model.addAttribute("exam", examRepo.findOne(id));
        model.addAttribute("questions", questionRepo.findAll());
        return "exam";
    }

    @RequestMapping(value = "/exams", method = RequestMethod.POST)
    public String addExam(@RequestParam String subject,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date examDate) {
        Exam exam = new Exam();
        exam.setSubject(subject);
        exam.setExamDate(examDate);
        examRepo.save(exam);

        return "redirect:/exams";
    }

    @RequestMapping(value = "/exams/{examId}/questions/{questionId}", method = RequestMethod.POST)
    @Transactional
    public String addQuestionToExam(@PathVariable Long examId, @PathVariable Long questionId) {
        if (!examRepo.findOne(examId).getQuestions().contains(questionRepo.findOne(questionId))) {
            examRepo.findOne(examId).getQuestions().add(questionRepo.findOne(questionId));
        }
        return "redirect:/exams/" + examId;
    }
}
