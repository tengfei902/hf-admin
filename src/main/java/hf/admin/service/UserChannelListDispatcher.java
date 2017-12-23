package hf.admin.service;

import hf.admin.rpc.AdminClient;
import hf.base.client.DefaultClient;
import hf.base.dispatcher.DispatchResult;
import hf.base.dispatcher.Dispatcher;
import hf.base.model.UserChannel;
import hf.base.model.UserChannelPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserChannelListDispatcher implements Dispatcher {
    @Autowired
    private AdminClient adminClient;
    @Autowired
    private DefaultClient client;

    @Override
    public DispatchResult dispatch(HttpServletRequest request, String page) {
        String groupId = request.getParameter("id");

        List<UserChannelPage> list = client.getUserChannelInfo(groupId);
//        List<UserChannel> list = adminClient.getUserChannelList(groupId);
        DispatchResult dispatchResult = new DispatchResult();
        dispatchResult.setPage(page);
//        dispatchResult.addObject("channels",list);
        dispatchResult.addObject("providers",list);
        dispatchResult.addObject("group",groupId);
        return dispatchResult;
    }
}
