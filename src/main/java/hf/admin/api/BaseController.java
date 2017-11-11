package hf.admin.api;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static hf.admin.model.Constants.CURRENT_USER_ID;

/**
 * Created by tengfei on 2017/11/11.
 */
public class BaseController {

    protected Long getUserId(HttpServletRequest request) {
        if(Objects.isNull(request.getAttribute(CURRENT_USER_ID))) {
            return null;
        }
        return Long.parseLong(String.valueOf(request.getAttribute(CURRENT_USER_ID)));
    }
}
