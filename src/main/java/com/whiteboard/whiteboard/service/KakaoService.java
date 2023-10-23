package com.whiteboard.whiteboard.service;

import org.springframework.stereotype.Service;

@Service
public class KakaoService {

    // @Value("${kakao.client.id}")
    // private String KAKAO_CLIENT_ID;

    // @Value("${kakao.client.secret}")
    // private String KAKAO_CLIENT_SECRET;

    // @Value("${kakao.redirect.url}")
    // private String KAKAO_REDIRECT_URL;

    // private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";
    // private final static String KAKAO_API_URI = "https://kapi.kakao.com";

    // public String getKakaoLogin() {
    //     return KAKAO_AUTH_URI + "/oauth/authorize"
    //             + "?client_id=" + KAKAO_CLIENT_ID
    //             + "&redirect_uri=" + KAKAO_REDIRECT_URL
    //             + "&response_type=code";
    // }

    // public KakaoDTO getKakaoInfo(String code) throws Exception {
    //     // https://shxrecord.tistory.com/290
    //     // getKakaoInfo(String code)는 컨트롤러에서 리턴받은 인증 코드값을 통해 카카오 인증 서버에 액세스 토큰을 요청합니다.
    //     // 토큰은 액세스 토큰과 리프레쉬 토큰 두가지가 있는데, 리프레쉬 토큰은 액세스 토큰을 갱신할 때 사용됩니다. 액세스 토큰은 만료 시간이
    //     // 존재하기 때문에 발급받은 리프레쉬 토큰을 저장해두고 액세스 토큰을 갱신하는 형태로 사용하게 됩니다. 본 예제에서는 발급받은 액세스 토큰을
    //     // 통해 사용자 정보를 가져오는데까지만 다루고 있습니다.

        
    //     if (code == null)
    //         throw new Exception("Failed get authorization code");

    //     String accessToken = "";
    //     String refreshToken = "";


    //     try {
    //         HttpHeaders headers = new HttpHeaders();
    //         headers.add("Content-type", "application/x-www-form-urlencoded");

    //         MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    //         params.add("grant_type", "authorization_code");
    //         params.add("client_id", KAKAO_CLIENT_ID);
    //         params.add("client_secret", KAKAO_CLIENT_SECRET);
    //         params.add("code", code);
    //         params.add("redirect_uri", KAKAO_REDIRECT_URL);

    //         RestTemplate restTemplate = new RestTemplate();
    //         HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

    //         System.out.println("HttpEntity 확인 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + httpEntity);


    //         System.out.println("--------------------------------------------------------------------------------------------------------------");

    //         ResponseEntity<String> response = restTemplate.exchange(
    //                 KAKAO_AUTH_URI + "/oauth/token",
    //                 HttpMethod.POST,
    //                 httpEntity,
    //                 String.class);

    //         JSONParser jsonParser = new JSONParser();
    //         JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());

    //         accessToken = (String) jsonObj.get("access_token");
    //         refreshToken = (String) jsonObj.get("refresh_token");
    //     } catch (Exception e) {
    //         throw new Exception("API call failed" + e.getMessage());
    //     }

    //     return getUserInfoWithToken(accessToken);
    // }

    // private KakaoDTO getUserInfoWithToken(String accessToken) throws Exception {
    //     // getKakaoInfo(String code)는 컨트롤러에서 리턴받은 인증 코드값을 통해 카카오 인증 서버에 액세스 토큰을 요청합니다.
    //     // 토큰은 액세스 토큰과 리프레쉬 토큰 두가지가 있는데, 리프레쉬 토큰은 액세스 토큰을 갱신할 때 사용됩니다. 액세스 토큰은 만료 시간이
    //     // 존재하기 때문에 발급받은 리프레쉬 토큰을 저장해두고 액세스 토큰을 갱신하는 형태로 사용하게 됩니다. 본 예제에서는 발급받은 액세스 토큰을
    //     // 통해 사용자 정보를 가져오는데까지만 다루고 있습니다.

    //     // HttpHeader 생성
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.add("Authorization", "Bearer " + accessToken);
    //     headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    //     // HttpHeader 담기
    //     RestTemplate rt = new RestTemplate();
    //     HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
    //     ResponseEntity<String> response = rt.exchange(
    //             KAKAO_API_URI + "/v2/user/me",
    //             HttpMethod.POST,
    //             httpEntity,
    //             String.class);

    //     // Response 데이터 파싱
    //     JSONParser jsonParser = new JSONParser();
    //     JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());
    //     JSONObject account = (JSONObject) jsonObj.get("kakao_account");
    //     JSONObject profile = (JSONObject) account.get("profile");

    //     Long id = (Long) jsonObj.get("id");
    //     String email = String.valueOf(account.get("email"));
    //     String nickname = String.valueOf(profile.get("nickname"));

    //     return KakaoDTO.builder()
    //             .id(id)
    //             .email(email)
    //             .nickname(nickname).build();
    // }
}