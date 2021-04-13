package com.jmac.demo.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlParserController {

    @GetMapping("/parse")
    public final String getUrlContentString(@RequestParam final String urlString) {
        String returnString = "";
        StringBuilder contentString = new StringBuilder();
        try {
            URL urlObj = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            // Set user agent so we can get content
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

            conn.connect();
            int responseCodeType = conn.getResponseCode() / 100;
            System.out.println(responseCodeType);
            if (responseCodeType != 2 && responseCodeType != 3 ){
                // Response code is not 2xx or 3xx
                returnString = "Site error code";
            } else {
                InputStreamReader streamReader = new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8"));
                BufferedReader br  = new BufferedReader(streamReader);
                String line;
                while ((line = br.readLine()) != null) {
                    contentString.append(line);
                }
                // Have contents, send to tokenizer
                JMTokenizer tokenizer = new JMTokenizer(null);
                List<String> tokenList = tokenizer.tokenizeString(contentString.toString());
                Map<String, Integer> tokenFrequencies = countFrequencies(tokenList);
                // TO DO:
                // SAVE EACH STRING AS WORD ENTITY
                // SAVE TOKEN FREQ TO DB, USING WORD ENTITY CLASS
                
                System.out.println(tokenFrequencies);
            }
        } catch (MalformedURLException e) {
            returnString = "Invalid URL";
            e.printStackTrace();
        } catch (IOException e) {
            returnString = "IO error with site";
            e.printStackTrace();
        }
        System.out.println(returnString);
        return returnString;
    }

    public static Map<String, Integer> countFrequencies(List<String> list)
    {
        // hashmap to store the frequency of element
        Map<String, Integer> hm = new HashMap<String, Integer>();
        for (String i : list) {
            Integer j = hm.get(i);
            hm.put(i, (j == null) ? 1 : j + 1);
        }
        return hm;
    }
}
