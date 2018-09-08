package com.example.linebot;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;


@RestController
public class Push {

    private static final Logger log = LoggerFactory.getLogger(Push.class);



    //Push先のユーザID
    private String userId="Ue0a170a570344b67b375192219d82b38";

    private final LineMessagingClient client;

    @Autowired
    public Push(LineMessagingClient lineMessagingClient){
        this.client=lineMessagingClient;
    }

    //テスト
    @GetMapping("test")
    public String hello(HttpServletRequest request){
        return "Get from"+request.getRequestURL();
    }

//
//    @GetMapping("timetone")
//    @Scheduled(cron="0 */1 * * * *",zone="Asia/Tokyo")//1分ごとに時刻を送る
//    public String pushTimeTone(){
//        String text= DateTimeFormatter.ofPattern("a k:mm").format(LocalDateTime.now());
//
//        try {
//            PushMessage pMsg=new PushMessage(userId,new TextMessage(text));
//            BotApiResponse resp=client.pushMessage(pMsg).get();
//            log.info("Sent messages:{}",resp);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }return text;
    }




