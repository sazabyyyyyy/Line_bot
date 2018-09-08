package com.example.linebot;


import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.ImagemapMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;

import java.time.LocalTime;
import java.util.Random;

//@LineMessageHandler をつけたclassが、Message APIの設定画面で設定した
// WebhookURL https://xxx.ngrok.io/callbackの処理用クラスにあたる
@LineMessageHandler
public class Callback {

    private static final Logger log = LoggerFactory.getLogger(Callback.class);




    //フォローイベントに対応する
    /*@EventMappingをつけたメソッドが各イベント（フォロー、話しかけなど）に対応する処理に当たる。
    どのイベントに対応するかは引数で与えられたイベントクラスによって変わる（例：フォローイベントはFollowEvent)
     */

    //BotをフォローしたときにuseIdを返答する
    @EventMapping
    public TextMessage handleFollow(FollowEvent event){
        String userId=event.getSource().getUserId();
        return reply("あなたのユーザIDは" +userId);
    }

    //返答メッセージを作る
    private TextMessage reply(String text){
        return new TextMessage(text);
    }


    //オウム返し
    @EventMapping
    public Message hadleMessage(MessageEvent<TextMessageContent> event){
        TextMessageContent tmc=event.getMessage();
        String text=tmc.getText();
        //やあと入力されたらgreet()メソッドを返す。それ以外はオウム返し。
        switch (text){
            case "やあ":
                return greet();
            case "こんにちは":
                return greet();
            case "こんばんは":
                return greet();
            case "おみくじ":
                return replyOmikuji();
                default:
                    return reply(text);
        }
    }

    //時間帯に合わせて内容を変えて挨拶を返すメソッド
    private TextMessage greet(){
        LocalTime lt=LocalTime.now();
        int hour=lt.getHour();
        if(hour>=17){
            return reply("こんばんは！");
        }
        if(hour>=11){
            return reply("こんにちは");
        }
        return reply("おはよう！");
    }

    //画像メッセージを作るメソッド
    private ImageMessage repliyImage(String url){
        return new ImageMessage(url,url);
    }


    //ランダムにおみくじ画像を返す
    private ImageMessage replyOmikuji(){
        //乱数を作る。0から始まる。
        int ranNum=new Random().nextInt(6);

        switch (ranNum){
            case 0://大吉
                return repliyImage("https://3.bp.blogspot.com/-vQSPQf-ytsc/T3K7QM3qaQI/AAAAAAAAE-s/6SB2q7ltxwg/s1600/omikuji_daikichi.png");
            case 1://大凶
                return repliyImage("https://2.bp.blogspot.com/-h61ngruj0tE/T3K7RDUWmPI/AAAAAAAAE-0/KXtPY8fDwco/s1600/omikuji_daikyou.png");
            case 2:
                return repliyImage("https://3.bp.blogspot.com/-_z-n-7gO3KA/T3K7MU3MdGI/AAAAAAAAE-k/8qs-jxqS4LE/s1600/omikuji_chuukichi.png");
            case 3://中吉
                return repliyImage("https://3.bp.blogspot.com/-nZt5pjGWT9E/T3K7TJ4wEZI/AAAAAAAAE_E/c1X2-N54EYo/s1600/omikuji_syoukichi.png");
            case 4://末吉
                return repliyImage("https://3.bp.blogspot.com/-JLNa8mwZRnU/T3K7StR-bEI/AAAAAAAAE-8/rQrDomz5MSw/s1600/omikuji_suekichi.png");
            case 5://兇
                return repliyImage("https://4.bp.blogspot.com/-qCfF4H7YOvE/T3K7R5ZjQVI/AAAAAAAAE-4/Hd1u2tzMG3Q/s1600/omikuji_kyou.png");

                default:
                    return repliyImage("https://3.bp.blogspot.com/-nZt5pjGWT9E/T3K7TJ4wEZI/AAAAAAAAE_E/c1X2-N54EYo/s1600/omikuji_syoukichi.png");
        }
    }

}
