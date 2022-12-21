package com.javafinal.WebMail.Controller;

import com.javafinal.WebMail.Constant.Archive;
import com.javafinal.WebMail.Model.Email;
import com.javafinal.WebMail.Model.EmailRepository;

import com.javafinal.WebMail.Model.Label;
import com.javafinal.WebMail.Service.QueryService;
import com.javafinal.WebMail.UserSessionInfo;
import com.javafinal.WebMail.ViewModel.EmailBasic;
import com.javafinal.WebMail.ViewModel.EmailInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private QueryService queryService;

    @Autowired
    private EmailRepository repository;

    @GetMapping("/tag")
    public void tag(@RequestParam(value = "email", required = false) int emailId,
                      @RequestParam(value = "label", required = false) int labelId, HttpServletResponse response)
    {
        boolean result = queryService.tagLabel(labelId, emailId);
        try {
            response.sendRedirect("/user");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/send")
    public void send(HttpServletResponse response, EmailInput emailInput){
        Boolean result = true;
        if(emailInput.getArchive().contains(Archive.INBOX))
            result = queryService.sendAnEmail(emailInput);
        else if (emailInput.getArchive().contains(Archive.DRAFT)) {
            result = queryService.saveADraft(emailInput);
        }
        try {
            response.sendRedirect("/user");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/updateArchive")
    @ResponseBody
    public String updateArchive(
            @RequestParam(value = "id", required = false) int emailId,
            @RequestParam(value = "sender", required = false) String senderEmail,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "date", required = false) String date
            , HttpServletResponse response)
    {
        EmailBasic emailBasic = new EmailBasic(emailId,senderEmail,title,date);
        queryService.toggleEmailStarArchive(emailBasic);
        return emailBasic.toString();
    }

    @GetMapping("/viewDetail")
    public String viewDetail(
            @RequestParam(value = "id", required = false) int emailId,
            @RequestParam(value = "sender", required = false) String senderEmail,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "date", required = false) String date
            , HttpServletResponse response, Model model)
    {
        EmailBasic emailBasic = new EmailBasic(emailId,senderEmail,title,date);
        Email email = queryService.getEmailByCriteria(emailBasic);
        model.addAttribute("user",UserSessionInfo.getCurrentUser());
        model.addAttribute("email",email);
        return "EmailDetail";
    }

    @GetMapping("/delete")
    @ResponseBody
    public String delete(
            @RequestParam(value = "id", required = false) int emailId,
            @RequestParam(value = "sender", required = false) String senderEmail,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "date", required = false) String date
            , HttpServletResponse response)
    {
        EmailBasic emailBasic = new EmailBasic(emailId,senderEmail,title,date);
//        Email email = repository.findEmailByEmailId(emailId);
//        if (email.getArchive().equals("DELETE")){
//            repository.delete(email);
//        }
//        else {
            boolean result = queryService.deleteEmail(emailBasic);
//        }
        try {
            response.sendRedirect("/user");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return emailBasic.toString();
    }

    @GetMapping("/alllabels")
    @ResponseBody
    public String alllabels(){
        List<Label> a = queryService.getAllLabels();
        return a.toString();
    }
}
