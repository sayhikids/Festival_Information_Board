package com.whiteboard.whiteboard;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whiteboard.whiteboard.entity.Festival;
import com.whiteboard.whiteboard.repository.FestivalRepository;
@RunWith(SpringRunner.class)
@SpringBootTest
public class FestivalRepositoryTest {

    @Autowired
    private FestivalRepository festivalRepository;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper 주입

   @Test
    public void testImportFestivalsFromJson() {
        try {
            // JSON 파일을 클래스 경로에서 읽어옴
            ClassPathResource resource = new ClassPathResource("templates/festivalAPI/JinjuFestivalOpenAPI.json");
            InputStream inputStream = resource.getInputStream();
            // ObjectMapper를 사용하여 JSON 데이터 파싱
            JsonNode jsonNode = objectMapper.readTree(inputStream);

            // "item" 배열에 있는 모든 아이템을 가져옴
            JsonNode items = jsonNode.at("/getFestivalJinju/item");

            // 모든 아이템을 처리
            for (JsonNode item : items) {
                // 필드 가져오기 및 널 체크
                String festivalTitle = getValueFromJson(item, "name");
                String region = getValueFromJson(item, "address");
                String venue = getValueFromJson(item, "area");
                String period = getValueFromJson(item, "address");
                String description = getValueFromJson(item, "content");
                String link = getValueFromJson(item, "homepage");
                String poster = getValueFromJson(item, "images");
                String thumbnail= getValueFromJson(item, "images");

                // 필드 값을 이용하여 Festival 엔티티 생성 및 저장
                Festival festival = Festival.builder()
                        .festivalTitle(festivalTitle)
                        .region(region)
                        .venue("경상남도 진주시"+venue)
                        .period(period+"부터")
                        .description(description)
                        .link(link)
                        .poster(poster)
                        .thumbnail(thumbnail)
                        .readCount(0L)
                        .build();

                festivalRepository.save(festival); // 데이터베이스에 저장

                System.err.println(festival);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getValueFromJson(JsonNode jsonNode, String fieldName) {
        JsonNode fieldNode = jsonNode.get(fieldName);
        return fieldNode != null ? fieldNode.asText() : "";
    }

}

// @Test
// public void festival(){

// //조회 하려는 축제 넘버
// Long festivalNumId = 14L;

// Festival festival =
// festivalRepository.findByFestivalNum(festivalNumId).orElse(null);

// if(festival != null){
// System.out.println("축제 정보");
// System.out.println("축제 타이틀: " +festival.getFestivalTitle());
// System.out.println("축제 썸네일: " +festival.getThumbnail());
// }else{
// System.err.println("없는 정보 입니다.");
// }

// }