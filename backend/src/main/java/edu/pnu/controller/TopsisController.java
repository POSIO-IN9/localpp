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
        // 요청 본문에서 weights와 codePart를 추출합니다.
        List<Double> weightList = (List<Double>) request.get("weights");
        String codePart = (String) request.get("codePart");

        // weights를 double 배열로 변환합니다.
        double[] weights = weightList.stream().mapToDouble(Double::doubleValue).toArray();

        // TopsisService에 가중치를 설정합니다.
        topsisService.setWeights(weights);

        // codePart를 이용해 TOPSIS 알고리즘을 실행합니다.
        return topsisService.calculateTopsis(codePart);
    }
}