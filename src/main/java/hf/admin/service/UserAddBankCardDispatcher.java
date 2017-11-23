package hf.admin.service;

import hf.base.client.DefaultClient;
import hf.base.contants.Constants;
import hf.base.dispatcher.DispatchResult;
import hf.base.dispatcher.Dispatcher;
import hf.base.model.UserBankCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserAddBankCardDispatcher implements Dispatcher {

    @Autowired
    private DefaultClient client;

    @Override
    public DispatchResult dispatch(HttpServletRequest request, String page) {
        String groupId = request.getParameter("group");
        String cardId = request.getParameter("cardId");
        UserBankCard userBankCard = client.getUserBankCardDetail(cardId);
        DispatchResult dispatchResult = new DispatchResult();
        dispatchResult.setPage(page);
        dispatchResult.addObject("cardInfo",userBankCard);
        dispatchResult.addObject("bankNames", Constants.bankNames);
        dispatchResult.addObject("group",groupId);
        return dispatchResult;
    }
}
