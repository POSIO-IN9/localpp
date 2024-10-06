package edu.pnu.controller;

import java.util.List;

import edu.pnu.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.pnu.repository.ListRepository;
import edu.pnu.service.ListService;

@RestController
@RequestMapping("/api")
public class MainController {

	
	@Autowired
	private ListRepository listRepo;
	@GetMapping(path="/eduall")
	public @ResponseBody Iterable<Edu_list> getAllEdu(){
		return listRepo.findAll();
	}

    @Autowired
    private ListService listService;


    @GetMapping("/ncscodes/six/{part}")
    public @ResponseBody List<Edu_list> getNcsCodeByFirstSix(@PathVariable String part) {
        return listService.getNcsCodeByFirstSix(part);
    }


    @GetMapping("/ncscodes/six/ratingsort/{part}")
    public @ResponseBody List<Edu_list> geNcsCodeByFirstSixSortByRating(@PathVariable String part) {
        return listService.getNcsCodeByFirstSixSortByRating(part);
    }
    
    @GetMapping("/ncscodes/six/pssort/{part}")
    public @ResponseBody List<Edu_list> geNcsCodeByFirstSixSortByPs(@PathVariable String part) {
        return listService.getNcsCodeByFirstSixSortByPs(part);
    }

    @GetMapping("/test")
    public String test(){
        return "부트,플라스크 연결 test입니다";

    }

}
