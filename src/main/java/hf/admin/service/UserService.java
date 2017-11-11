package hf.admin.service;

import com.google.gson.reflect.TypeToken;
import hf.admin.model.UserInfoDto;
import hf.admin.model.UserInfoRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {

    private static final String HF_APP_URL = "http://127.0.0.1:8090/jh/user/get_user_list";

    public List<UserInfoDto> getUserListForAdmin(UserInfoRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(HF_APP_URL, List.class,request);
    }
}
