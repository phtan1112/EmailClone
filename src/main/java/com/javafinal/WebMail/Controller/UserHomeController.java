package com.javafinal.WebMail.Controller;

import com.javafinal.WebMail.Constant.Archive;
import com.javafinal.WebMail.Model.Label;
import com.javafinal.WebMail.Model.LabelRepository;
import com.javafinal.WebMail.Model.User;
import com.javafinal.WebMail.Model.UserRepository;
import com.javafinal.WebMail.Service.QueryService;
import com.javafinal.WebMail.UserSessionInfo;
import com.javafinal.WebMail.ViewModel.EmailBasic;
import com.javafinal.WebMail.UserSessionInfo;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserHomeController {
    @Autowired
    private UserRepository repo;
    @Autowired
    private QueryService queryService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private LabelRepository labelRepo;

    private static final String MODEL_EMAILBASICLIST = "emailBasicList";
    private static final String MODEL_LISTSIZE = "listSize";

    private static final String MODEL_CURRENT_ARCHIVE = "archive";

    @ModelAttribute
    public void userDetail(Model model, Principal principal){
        String email = principal.getName();
        User u = repo.findByEmail(email);
        UserSessionInfo.setCurrentUser(u);
        model.addAttribute("user", u);
        List<Label> lstLabel = labelRepo.findAllByUserEmailIgnoreCase(u.getEmail());
        System.out.println(lstLabel.size());
        model.addAttribute("listLabel1", lstLabel);




    }

    @GetMapping("/whoami")
    @ResponseBody
    public String whoAmI(){
        return UserSessionInfo.getCurrentUser().toString();
    }

    @GetMapping("")
    public String home(Model model, Principal principal){
        String email = principal.getName();
        User u = repo.findByEmail(email);
        List<EmailBasic> emailBasicList = queryService.loadReceivedBasicEmails();
        model.addAttribute(MODEL_EMAILBASICLIST,emailBasicList);
        model.addAttribute(MODEL_LISTSIZE,emailBasicList.size());
        model.addAttribute(MODEL_CURRENT_ARCHIVE,Archive.INBOX);
        List<Label> lstLabel = labelRepo.findAllByUserEmailIgnoreCase(u.getEmail());
        model.addAttribute("listLabel", lstLabel);
        return "UserHome";
    }

    @GetMapping("/viewLabel")
    public String viewLabel(@RequestParam(value = "labelId", required = false) int id,
                            @RequestParam(value = "labelName", required = false) String name,
                            Model model)
    {
        List<EmailBasic> basicList = queryService.loadAllTaggedBasicEmails(id);
        model.addAttribute(MODEL_EMAILBASICLIST,basicList);
        model.addAttribute(MODEL_LISTSIZE,basicList.size());
        model.addAttribute(MODEL_CURRENT_ARCHIVE,Archive.INBOX);
        List<Label> lstLabel = labelRepo.findAll();
        model.addAttribute("listLabel", lstLabel);
        return "UserHome";
    }

    @GetMapping("/viewStar")
    public String viewStar(Model model){
        List<EmailBasic> basicList = queryService.loadReceivedBasicStarEmails();
        model.addAttribute(MODEL_EMAILBASICLIST,basicList);
        model.addAttribute(MODEL_LISTSIZE,basicList.size());
        model.addAttribute(MODEL_CURRENT_ARCHIVE,Archive.STAR);
        List<Label> lstLabel = labelRepo.findAll();
        model.addAttribute("listLabel", lstLabel);
        return "UserHome";
    }

    @GetMapping("/viewTrash")
    public String viewTrash(Model model){
        List<EmailBasic> basicList = queryService.loadBasicEmailsByArchive(Archive.DELETE);
        model.addAttribute(MODEL_EMAILBASICLIST, basicList);
        model.addAttribute(MODEL_LISTSIZE,basicList.size());
        model.addAttribute(MODEL_CURRENT_ARCHIVE,Archive.DELETE);
        List<Label> lstLabel = labelRepo.findAll();
        model.addAttribute("listLabel", lstLabel);
        return "UserHome";
    }

    @GetMapping("/viewDraft")
    public String viewDraft(Model model){
        List<EmailBasic> basicList = queryService.loadDraftBasicEmails();
        model.addAttribute(MODEL_EMAILBASICLIST, basicList);
        model.addAttribute(MODEL_LISTSIZE,basicList.size());
        model.addAttribute(MODEL_CURRENT_ARCHIVE,Archive.DRAFT);
        List<Label> lstLabel = labelRepo.findAll();
        model.addAttribute("listLabel", lstLabel);
        return "UserHome";
    }

    @GetMapping("/viewSent")
    public String viewSent(Model model){
        List<EmailBasic> basicList = queryService.loadSentBasicEmails();
        model.addAttribute(MODEL_EMAILBASICLIST, basicList);
        model.addAttribute(MODEL_LISTSIZE,basicList.size());
        model.addAttribute(MODEL_CURRENT_ARCHIVE,Archive.SENT);
        List<Label> lstLabel = labelRepo.findAll();
        model.addAttribute("listLabel", lstLabel);
        return "UserHome";
    }

    @PostMapping("/editInfo")
    public String edit(HttpServletResponse response,Model model, Principal principal,inputEdit ie){
        String email = principal.getName();
        User u = repo.findByEmail(email);

        Path root = Paths.get("src/main/resources/static/assets/");
        String fileName = StringUtils.cleanPath(ie.getAvatar().getOriginalFilename());
        System.out.println(fileName);
        if(ie.getName().equals("")){
            model.addAttribute("errorMsgOfEditInfo","pls fill your name");
        }
        else if(fileName.equals("")){
            u.setName(ie.getName());
            repo.save(u);
            model.addAttribute("SuccessMsgOfEditInfo","Change your name success");
        }
        else{
            u.setName(ie.getName());
            u.setAvatar(fileName);
            repo.save(u);


            try {
                Files.copy(ie.getAvatar().getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

                model.addAttribute("SuccessMsgOfEditInfo","Change information successfully");
            } catch (Exception e) {
                throw  new RuntimeException(e.getMessage());
            }


        }
        try {
            response.sendRedirect("/user");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "UserHome";
    }

    @PostMapping("/changePassword")
    public String changePassword(HttpServletResponse response,Model model, Principal principal,ChangePassword CP){
        String email = principal.getName();
        User u = repo.findByEmail(email);

        if(CP.getOldPass().equals("")){
            model.addAttribute("errorMsgOfChangePassword","your old password is empty");
        }
        else if(CP.getNewPass().equals("")){
            model.addAttribute("errorMsgOfChangePassword","your new password is empty");
        }
        else if(!CP.getNewPass().equals(CP.getConfPass())){
            model.addAttribute("errorMsgOfChangePassword","confirm password is not same");
        }
        else if(!encoder.matches(CP.getOldPass(),u.getPassword())){
            model.addAttribute("errorMsgOfChangePassword","your old password is not correct");
        }
        else{
            u.setPassword(encoder.encode(CP.getNewPass()));

            repo.save(u);
            model.addAttribute("SuccessMsgOfChangePassword","change success");
        }
        try {
            response.sendRedirect("/user");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "UserHome";
    }

    @PostMapping("/addLabel")
    public String addLabel(HttpServletResponse response, Model model, Principal principal, AddLabel AL){
        String email = principal.getName();
        User u = repo.findByEmail(email);
        List<Label> lstLabel = labelRepo.findAllByUserEmailIgnoreCase(email);

        if(AL.getNameLabel().equals("")){
            model.addAttribute("errorMsgOfAddLabel","Label name is empty");
        }
        else{
            boolean check = true;
            for (int i=0;i<lstLabel.size();i++){
                if (lstLabel.get(i).getName().equals(AL.getNameLabel())){
                    check = false;
                    break;
                }
            }
            if (check){
                Label l = new Label(0,AL.getNameLabel(),null,u);
                labelRepo.save(l);
                model.addAttribute("SuccessMsgOfAddLabel","Add success");
            }
            else{
                model.addAttribute("errorMsgOfAddLabel","Label name is exist");
            }
        }
        try {
            response.sendRedirect("/user");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "UserHome";
    }
    @PostMapping("/editLabel")
    public String editLabel(HttpServletResponse response, Model model, Principal principal,AddLabel AL,@RequestParam(name = "labelId") String labelId){
        String email = principal.getName();
        User u = repo.findByEmail(email);
        Label l = labelRepo.findById(Integer.parseInt(labelId));
        l.setName(AL.getNameLabel());
        labelRepo.save(l);
        try {
            response.sendRedirect("/user");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "UserHome";
    }

    @PostMapping("/deleteLabel")
    public String deleteLabel(HttpServletResponse response, Model model, Principal principal,AddLabel AL,@RequestParam(name = "deleteLabelId") String labelId){
        String email = principal.getName();
        User u = repo.findByEmail(email);
        Label l = labelRepo.findById(Integer.parseInt(labelId));
        labelRepo.delete(l);
        try {
            response.sendRedirect("/user");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "UserHome";
    }
}



@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class inputEdit{
    private String name;
    private MultipartFile avatar;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class ChangePassword{
    private String oldPass;
    private String newPass;
    private String confPass;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class AddLabel{
    private String nameLabel;
}
