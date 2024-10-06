package edu.pnu.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.pnu.domain.Edu_list;
import edu.pnu.repository.ListRepository;

@NoArgsConstructor
@Service
@AllArgsConstructor
public class ListService {
	 @Autowired
	 private ListRepository listRepository;

	    public List<Edu_list> getNcsCodeByFirstSix(String part) {
	        return listRepository.findByNcsCodeFirstSix(part);
	    }
	    
	    public List<Edu_list> getNcsCodeByFirstSixSortByRating(String part) {
	        return listRepository.findByNcsCodeFirstSixSortByRating(part);
	    }
	    
	    public List<Edu_list> getNcsCodeByFirstSixSortByPs(String part) {
	        return listRepository.findByNcsCodeFirstSixSortByPs(part);
	    }

}
