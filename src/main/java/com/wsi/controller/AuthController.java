package com.wsi.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wsi.entity.Role;
import com.wsi.entity.User;
import com.wsi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;
    @GetMapping("/")
    public String index(Model model, HttpSession session, HttpServletResponse response) throws IOException {
        authService.defaultUserInitializer();
        if(session.getAttribute("username")!=null){
            response.sendRedirect("/dashboard");
        }
        return "index";
    }

    @RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.POST})
    public String register(@ModelAttribute("user")User user, Model model){
        if(user.getUsername()!=null && user.getPassword()!=null){
            authService.saveUser(user);
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", new User());
        }
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session){
        authService.defaultUserInitializer();
        model.addAttribute("greetings", "Welcome, "+session.getAttribute("username").toString());
        return "dashboard";
    }

    @RequestMapping(value = "/view-data", method = {RequestMethod.POST, RequestMethod.GET})
    public String readCsv(MultipartFile file, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(file==null){
            response.sendRedirect("/dashboard");
        } else {
            Gson gson = new Gson();
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line = "";
            List<String> csvToList = new ArrayList<>();
            String fullCsvAsLine = "";
            while ((line = br.readLine()) != null) {
                // use comma as separator
//            List csv = new ArrayList();
//            csv.add(line);
                fullCsvAsLine += "[" + line + "],";
//            csvToList.add(line.replace("\"",""));
            }
            model.addAttribute("csvList", fullCsvAsLine);
        }
        return "show_chart";
    }
}
