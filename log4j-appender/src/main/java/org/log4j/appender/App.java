package org.log4j.appender;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {

        HttpClient client = new HttpClient("http://localhost:8091/authenticate", "http://localhost:8095/rest/log/put",
                "0bac5204-4951-11ec-81d3-0242ac130003");

        System.out.println("Auth::::" + client.authenticate() + "::::");

        System.out.println("log!" + client.putLogAndRetry("fadsfasdfadsfads"));

    }
}
