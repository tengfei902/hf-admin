package hf.admin.service;

import hf.admin.utils.MapUtils;
import hf.base.client.DefaultClient;
import hf.base.dispatcher.DispatchResult;
import hf.base.dispatcher.Dispatcher;
import hf.base.enums.ChannelProvider;
import hf.base.model.UserGroupExt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserAddProviderDispatcher implements Dispatcher {
    @Autowired
    private DefaultClient client;

    @Override
    public DispatchResult dispatch(HttpServletRequest request, String page) {
        DispatchResult dispatchResult = new DispatchResult();
        dispatchResult.setPage(page);

        String userGroupId = request.getParameter("groupId");
        dispatchResult.addObject("userGroupId",userGroupId);

        String id = request.getParameter("id");
        if(StringUtils.isNotEmpty(id)) {
            dispatchResult.addObject("userProviderId",id);
            UserGroupExt userGroupExt = client.getUserExtById(id);
            dispatchResult.addObject("userGroupExt",userGroupExt);
        }

        List<Map<String,String>> providers = new ArrayList<>();
        for(ChannelProvider channelProvider:ChannelProvider.values()) {
            providers.add(MapUtils.buildMap("code",channelProvider.getCode(),"name",channelProvider.getName()));
        }
        dispatchResult.addObject("providers",providers);

        return dispatchResult;
    }
}
