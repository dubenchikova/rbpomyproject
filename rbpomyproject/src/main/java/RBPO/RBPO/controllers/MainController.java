package RBPO.RBPO.controllers;

import RBPO.RBPO.entity.MedicalRecords;
import RBPO.RBPO.services.AppUserService;
import RBPO.RBPO.services.MedicalRecordsService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MedicalRecordsService medicalRecordsService;
    @GetMapping("/")
    public String qwe(Model model) {
        return "redirect:/all";
    }
    @GetMapping("/error")
    public String error() {
        return "error";
    }
    @GetMapping("/all")
    public String home(@RequestParam(name = "title", required = false) String title, Model model) {
        //model.addAttribute("medicalRecordss", medicalRecordsService.listmedicalRecordss(title));
        List<MedicalRecords> medicalRecordss = medicalRecordsService.getAllmedicalRecordss();
        model.addAttribute("medicalRecordss", medicalRecordss);
        return "home";
    }
    @GetMapping("/profile")
    public String profile(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("username", authentication.getName());

        return "Profile";


    }
}
