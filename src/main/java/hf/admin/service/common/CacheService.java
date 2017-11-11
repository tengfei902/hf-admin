package hf.admin.service.common;

/**
 * Created by tengfei on 2017/11/11.
 */
public interface CacheService {
    boolean checkLogin(String userId,String sessionId);
    boolean login(String userId,String sessionId);
}
