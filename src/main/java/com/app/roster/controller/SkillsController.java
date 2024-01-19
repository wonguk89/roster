package com.app.roster.controller;

import com.app.roster.dto.Employee;
import com.app.roster.dto.Skill;
import com.app.roster.service.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillsController {

    private final SkillsService skillsService;

    @Autowired
    public SkillsController(SkillsService skillsService) {
        this.skillsService = skillsService;
    }

    // 근무타입 전체조회
    @GetMapping("/getAll")
    public ResponseEntity<List<Skill>> getAllSkills() {
        List<Skill> skills = skillsService.getAllSkills();
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }

    // 근무타입 신규등록
    @PostMapping("/create")
    public ResponseEntity<String> addSkill(@RequestBody List<Skill> skills) {
        try {
            skillsService.addSkill(skills);
            return ResponseEntity.ok("Skill added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding skill: " + e.getMessage());
        }
    }

    // 근무타입 정보수정
    @PutMapping("/update")
    public ResponseEntity<String> updateSkill(@RequestBody List<Skill> skills) {
        try {
            skillsService.updateSkill(skills);
            return ResponseEntity.ok("Skill updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating skill: " + e.getMessage());
        }
    }

    // 근무타입 정보삭제
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSkill(@RequestBody List<Skill> skills) {
        try {
            skillsService.deleteSkill(skills);
            return ResponseEntity.ok("Skill deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting skill: " + e.getMessage());
        }
    }
}