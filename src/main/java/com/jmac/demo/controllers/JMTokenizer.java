package com.jmac.demo.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JMTokenizer {
    private Set<String> stopWords = new HashSet<String>();
    private boolean stop;
    public JMTokenizer(final String[] stopListFile) {
        stop = stopListFile != null;
    }

    public final List<String> tokenizeString(final String contents) {
        String processedContents = removeAbbreviations(contents);
        processedContents = processedContents.toLowerCase();
        // Replace punctuations
        processedContents = processedContents.replaceAll("[\\p{IsPunctuation}\\p{Punct}]", " ");
        // Split the string on whitespaces
        String[] terms = processedContents.split("\\s+");
        List<String> termList = new ArrayList<String>(Arrays.asList(terms));
        if (stop) {
            termList = termList.stream().filter(x -> !stopWords.contains(x)).collect(Collectors.toList());
        }
        return termList;
    }

    public final String removeAbbreviations(final String contents) {
        StringBuffer sb = new StringBuffer();
        String regex = "\b(?:[A-Z]\\.){2,}"; //Regex to match U.S.A.
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contents);
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group().replace(".", "") + " "); // U.S.A. -> USA
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
