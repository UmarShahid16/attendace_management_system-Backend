package com.ams.AMS.util.skybiometry;

import com.ams.AMS.exceptions.DAOResponse;
import com.ams.AMS.util.response.Response;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SkyBiometryService {

    private final String API_KEY = "1vghogmmkhcq5q7t2r4rtohffj";
    private final String API_SECRET = "7nqub6e4hkaef9llfi6mh88gkv";
    private final String NAMESPACE = "faceid_attendance";

    public String detectFace(String imageUrl) {
        Response response1 = new Response();
        try {
            if (imageUrl == null || imageUrl.isEmpty()) {
                response1.setResponse(DAOResponse.IMAGE_URL_REQUIRED);
                return null;
            }
            String url = "https://api.skybiometry.com/fc/faces/detect.json";
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("api_key", API_KEY)
                    .queryParam("api_secret", API_SECRET)
                    .queryParam("urls", imageUrl);

            RestTemplate restTemplate = new RestTemplate();
            Map response = restTemplate.getForObject(builder.toUriString(), Map.class);
            // extract tid from response
            String tid = (String) ((Map) ((List) ((Map) ((List) response.get("photos")).get(0)).get("tags")).get(0)).get("tid");
            return tid;
        }catch (Exception e ){
            e.printStackTrace();
        }
        return null;
    }

    public void saveFace(String tid, Long userId) {
        try {
            String url = "https://api.skybiometry.com/fc/tags/save.json";
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("api_key", API_KEY)
                    .queryParam("api_secret", API_SECRET)
                    .queryParam("tids", tid)
                    .queryParam("uid", userId + "@" + NAMESPACE);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForObject(builder.toUriString(), Map.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void trainFace(Long userId) {
        try {
            Response response = new Response();
            if(userId == null){
                response.setResponse(DAOResponse.USER_ID_REQUIRED);
                return;
            }
            String url = "https://api.skybiometry.com/fc/faces/train.json";
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("api_key", API_KEY)
                    .queryParam("api_secret", API_SECRET)
                    .queryParam("uids", userId + "@" + NAMESPACE);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForObject(builder.toUriString(), Map.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // SkyBiometry Service
    public String verifyFace(String imageUrl) {
        try {
            String url = "https://api.skybiometry.com/fc/faces/recognize.json";
            RestTemplate restTemplate = new RestTemplate();

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("api_key", API_KEY);
            params.add("api_secret", API_SECRET);
            params.add("urls", imageUrl);
            params.add("uids", "all@faceid_attendance");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            String response = restTemplate.postForObject(url, request, String.class);

            JSONObject jsonResponse = new JSONObject(response);
            JSONArray tags = jsonResponse
                    .getJSONArray("photos")
                    .getJSONObject(0)
                    .getJSONArray("tags");

            if (!tags.isEmpty()) {
                JSONArray uids = tags.getJSONObject(0).getJSONArray("uids");
                if (!uids.isEmpty()) {
                    String uid = uids.getJSONObject(0).getString("uid");
                    int confidence = uids.getJSONObject(0).getInt("confidence");

                    if (confidence > 50) {
                        return uid.split("@")[0]; // only userId
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}



