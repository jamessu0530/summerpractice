package app.keelung.controller;

import app.keelung.model.Sight;
import app.keelung.service.KeelungSightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class SightCrawlerController {

    private final KeelungSightService keelungSightService;

    public SightCrawlerController(KeelungSightService keelungSightService) {
        this.keelungSightService = keelungSightService;
    }

    @GetMapping("/SightAPI")
    public ResponseEntity<List<Sight>> getSights(@RequestParam("zone") String zone) throws IOException {
        return ResponseEntity.ok(keelungSightService.getSightsByZone(zone));
    }
}

