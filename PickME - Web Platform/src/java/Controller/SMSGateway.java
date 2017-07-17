package Controller;

import java.net.*;

import java.io.*;

public class SMSGateway {

    public void sendSMS(String to, String msg) throws Exception {
        URL textit = new URL("http://textit.biz/sendmsg/index.php?id=94716153210&pw=7842&to=" + to + "&text=" + msg);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(textit.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
        }
        in.close();
    }
}
