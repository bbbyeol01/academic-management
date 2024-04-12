package com.ezen.management.controller;

import com.ezen.management.domain.Question;
import com.ezen.management.domain.QuestionName;
import com.ezen.management.dto.PageRequestDTO;
import com.ezen.management.dto.PageResponseDTO;
import com.ezen.management.dto.QuestionBlockDTO;
import com.ezen.management.dto.QuestionDTO;
import com.ezen.management.service.QuestionNameService;
import com.ezen.management.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/member/question")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('MASTER', 'ADMIN', 'TEACHER')")
public class QuestionController {


    @Value("${com.ezen.management.upload.path}")
    private String uploadPath;


    private final QuestionNameService questionNameService;
    private final QuestionService questionService;

    @GetMapping("")
    public String index(Model model, PageRequestDTO pageRequestDTO){

        List<QuestionName> questionKeywordList = questionNameService.findAll();

        model.addAttribute("questionKeywordList", questionKeywordList);

        questionKeywordList.forEach(questionKeyword -> {
            log.info(questionKeyword.getName());
        });

//        모든 사전평가 문제 리스트
//        여기는 페이징 필요함(3/29)
//        List<Question> questions = questionService.findAll();

        PageResponseDTO<Question> questions = questionService.searchQuestion(pageRequestDTO);

//        questions.getDtoList().forEach(question -> {
//            log.info(question.getContent());
//        });


        model.addAttribute("pageResponseDTO", questions);

        return "member/question/index";
    }



//    문제 이름으로 문제 리스트 불러와서 body에 리턴함
    @GetMapping("/list")
    @ResponseBody
    public List<Question> questionList(String name){

        log.info("parameter name : {}", name);
        List<Question> questionByName = questionService.findQuestionByName(name);
//
//        questionByName.forEach(question -> {
//            log.info(question.getContent());
//        });

        return questionByName;
    }


    @GetMapping("/getQuestion")
    @ResponseBody
    public Question getQuestion(Long questionIdx){

        log.info("questionIdx : {}", questionIdx);
        log.info("question : {}", questionService.findById(questionIdx));

        return questionService.findById(questionIdx);
    }


    @PostMapping("/insert")
    public String insertPOST(QuestionDTO questionDTO, MultipartFile file){

        log.info("insert question : {}", questionDTO);

        questionFileSave(questionDTO, file);

        try{
            questionService.insert(questionDTO);
            return "redirect:/member/question?code=success";
        }catch (Exception e){
            return "redirect:/member/question?code=fail";
        }

    }

    @PostMapping("/update")
    @ResponseBody
    public List<Question> updatePOST(QuestionDTO questionDTO){
//        해당 문제 수정한 다음 그 문제 키워드로 가져온 문제 리스트 반환

        int update = questionService.update(questionDTO);
        String name = questionDTO.getName();

        List<Question> questionByName = questionService.findQuestionByName(name);

        log.info("update result");
        log.info("" + questionByName);

        return questionByName;


    }

    @PostMapping("/delete")
    @ResponseBody
    public List<Question> deletePOST(Long questionIdx){

        log.info("questionIdx : {}", questionIdx);
        log.info("question : {}", questionService.findById(questionIdx));

        String name = questionService.findById(questionIdx).getName();

        questionService.delete(questionIdx);

        return questionService.findQuestionByName(name);


    }

    @GetMapping("/create")
    public void createGET(){

    }

    @PostMapping("/create")
    public String createPOST(String name,
                             @RequestParam("number") List<Integer> number,
                             @RequestParam("content") List<String> content,
                             @RequestParam(value = "example", required = false) List<String> example,
                             @RequestParam("item1") List<String> item1,
                             @RequestParam("item2") List<String> item2,
                             @RequestParam("item3") List<String> item3,
                             @RequestParam("item4") List<String> item4,
                             @RequestParam("answer") List<Integer> answer){

        questionNameService.save(name);

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for(int i = 0; i < content.size(); i++){
            QuestionDTO questionDTO = QuestionDTO.builder()
                    .name(name)
                    .number(number.get(i))
                    .content(content.get(i))
                    .item1(item1.get(i))
                    .item2(item2.get(i))
                    .item3(item3.get(i))
                    .item4(item4.get(i))
                    .answer(answer.get(i))
                    .build();

            if(example != null && example.get(i) != null){
                questionDTO.setExample(example.get(i));
            }

            questionDTOList.add(questionDTO);
        }

        questionService.multiSave(questionDTOList);

        return "redirect:/member/question?code=success";
    }



    @GetMapping("/isExist")
    @ResponseBody
    public boolean isExist(String name){
        QuestionName byName = questionNameService.findByName(name);

        return byName != null;
    }


    private void questionFileSave(QuestionDTO questionDTO, MultipartFile file){

        if(file.isEmpty()){
            return;
        }

        String uuid = UUID.randomUUID().toString();
        String originalName = file.getOriginalFilename();

        questionDTO.setUuid(uuid);
        questionDTO.setFileName(originalName);

        Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);

        try {
//           이미지 저장
            file.transferTo(savePath);
        } catch (IOException e) {
            log.error(e.getMessage());
        }


    }



}
