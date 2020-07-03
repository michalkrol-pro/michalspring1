package com.springboot.h2.controller;

import com.springboot.h2.model.Uczen;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class AppController {
    List<Uczen> listaOdpytanych = new ArrayList<>();
    private ArrayList<Uczen> listaUczniów = new ArrayList<>();

    @GetMapping("/")
    public String index() {
        return "uczen/index";
    }

    @RequestMapping(value = "/uczen_form", method = RequestMethod.GET)
    public String showForm() {
        return "uczen/uczen_form";
    }

    @PostMapping("/addStudents")
    public String addStudents(@RequestParam(value = "liczbaUczniow") int liczbaUczniow) {
        int start = listaUczniów.size();

        for(int i=1; i <= liczbaUczniow; i++) {
            listaUczniów.add(new Uczen(i+start, false, false));
        }
        return "redirect:/uczen_list";
    }



    @PostMapping(value = "/setAbsent")
    public String setAbsent(@RequestParam(value = "numerUcznia") int numerUcznia) {

        for (Uczen uczen:listaUczniów) {
            if(uczen.getNumer()==numerUcznia){
            uczen.setJestNieobecny(true);
        } }
    return "redirect:/uczen_list"; }

    @PostMapping(value = "/setNp")
    public String setNp(@RequestParam(value = "numerUcznia") int numerUcznia) {

        for (Uczen uczen:listaUczniów) {
            if(uczen.getNumer()==numerUcznia){
                uczen.setJestNieprzygotowany(true);
            } }
        return "redirect:/uczen_list";
    }


    @RequestMapping("/uczen_list")
    public ModelAndView uczen_list() {
        listaUczniów.stream().sorted(Comparator.comparing(Uczen::getNumer));
        return new ModelAndView("uczen/uczen_list", "list", listaUczniów);
    }

    @GetMapping ("/losuj")
    public ModelAndView losuj() {
        List<Uczen> listaAktywnych = new ArrayList<>();
        for (Uczen uczen:listaUczniów){
            if(uczen.isJestNieobecny()==false&&uczen.isJestNieprzygotowany()==false ){
                listaAktywnych.add(uczen);
            }
        }
        listaAktywnych.removeAll(listaOdpytanych);

        int maxNumer =  listaAktywnych.size()-1;
        Random random = new Random();
        int los = random.nextInt(maxNumer);
        Uczen wylosowany = listaAktywnych.get(los);
        listaOdpytanych.add(wylosowany);

        return new ModelAndView("uczen/losowanie", "uczen", wylosowany);    }
}