package com.sysclinic.SysClinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/pacientes-view")
    public String pacientes() {

        return "pacientes";
    }

    @GetMapping("/medicos-view")
    public String medicos() {

        return "medicos";
    }

    @GetMapping("/consultas-view")
    public String consultas() {

        return "consultas";
    }
}