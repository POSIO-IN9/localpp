package edu.pnu.controller;

import edu.pnu.domain.Edu_list;
import edu.pnu.service.TopsisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TopsisController {

    @Autowired
    private TopsisService topsisService;

    @PostMapping("/topsis")
    public List<Edu_list> runTopsis(@RequestBody Map<String, Object> request) {
        List<Double> weightList = (List<Double>) request.get("weights");
        String codePart = (String) request.get("codePart");

        double[] weights = weightList.stream().mapToDouble(Double::doubleValue).toArray();

        topsisService.setWeights(weights);

        return topsisService.calculateTopsis(codePart);
    }
}
