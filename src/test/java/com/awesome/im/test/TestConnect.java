package com.awesome.im.test;

import com.awesome.im.ImClient;

public class TestConnect {

    public  static  String token ="5ddb0d19-eed9-4ed4-9d88-70e6691ef327";
    public  static  String userId ="23232323";
    public static void main(String[] args) {



        ImClient imClient=new ImClient();
        try {
            imClient.connect("127.0.0.1",50070).sync();
            imClient.authenticate(userId,token);
//            imClient.send(userId,token);

        } catch (Exception e) {
            e.printStackTrace();
        }
//

    }
}
