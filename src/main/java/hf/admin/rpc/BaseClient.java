package hf.admin.rpc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hf.admin.utils.ResponseResult;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;

/**
 * Created by tengfei on 2017/11/11.
 */
public class BaseClient {
    private static final String APPLICATION_URL_ENCODED = "application/x-www-form-urlencoded";
    private RestTemplate restTemplate = new RestTemplate();

    protected <T> T get(RemoteParams params,Class<T> dataType) {
        return restTemplate.getForObject(params.getPath(),dataType,null == params.getParams()?params.getParamObj():params.getParams());
    }

    protected String post(RemoteParams params) {
        return restTemplate.postForObject(params.getPath(),null == params.getParams()?params.getParamObj():params.getParams(),String.class);
    }
}
