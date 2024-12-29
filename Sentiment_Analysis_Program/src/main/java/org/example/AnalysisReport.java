package org.example;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;

import java.util.HashMap;
import java.util.Properties;

public class AnalysisReport {
    private static HashMap<String, String> sentenceAnalysis = new HashMap<String, String>();
    private int totalSentiment = 0, positivityCount = 0, neutralityCount = 0, negativityCount = 0;
    private int positivityPercent = 0, neutralityPercent = 0, negativityPercent = 0;
    private String text, analysis, compiledReport;

    AnalysisReport(String feedback) {
        text = feedback;
    }
    public void generateAnalysisReport() {
        analyzeAndStoreText();
        sentimentCounter();
        setPercentages();
        displayAnalysisResult();
    }
    private void analyzeAndStoreText()
    {
        //create properties object
        Properties pipelinProp = new Properties();
        Properties tokenProps = new Properties();

        //configure pipeline properties
        pipelinProp.setProperty("enforceRequirements", "false");
        pipelinProp.setProperty("annotators", "parse, sentiment");
        pipelinProp.setProperty("parse.binaryTrees", "true");

        //Configure/set tokens properties
        tokenProps.setProperty("annotators", "tokenize, ssplit");

        //creating an object of the stanfordNLP Class
        StanfordCoreNLP pipeline = new StanfordCoreNLP(pipelinProp);
        StanfordCoreNLP tokenizer = new StanfordCoreNLP(tokenProps);

        Annotation annotation = tokenizer.process(text);
        pipeline.annotate(annotation);

        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);

            //store sentence analysis in a hash map
            sentenceAnalysis.put(sentence.toString(), sentiment);
        }
    }
    public void displayHashMap(HashMap<String, String> List) { //Consider placing this method in a shared or common class.
        System.out.println("Username\t\tPassword");
        for (String i : List.keySet()) {
            System.out.println(i + "\t" + List.get(i));
        }
    }
    private void sentimentCounter(){  //Counts the number of sentences in the text and how many times each sentiment occurs in the text
        for (String i: sentenceAnalysis.keySet()) {
            switch (sentenceAnalysis.get(i)) {
                case "Very positive", "Positive": {
                   positivityCount += 1;
                   break;
                }
                case "Neutral": {
                    neutralityCount += 1;
                    break;
                }
                case "very negative","Negative": {
                    negativityCount += 1;
                    break;
                }
            }
            totalSentiment += 1;
        }
    }
    private void setPercentages() { //returns the percentage value of the sentiment provided.
        positivityPercent = (int)(((float)positivityCount / totalSentiment) * 100);
        neutralityPercent = (int)(((float)neutralityCount / totalSentiment) * 100);
        negativityPercent = (int)(((float)negativityCount / totalSentiment) * 100);
    }
    private void displayAnalysisResult() {
        analysis = "Neutral: " + printBars(neutralityPercent) + " " + neutralityPercent + "%\n";
        analysis += "Negative: " + printBars(negativityPercent) + " " + negativityPercent + "%\n";
        analysis += "Postive: " + printBars(positivityPercent) + " " +  positivityPercent + "%\n";
        System.out.println(analysis);
    }

    private String printBars(int barPercents) {
       int  barCounts = (int) (barPercents / 10);
        String bars = "";
        for (int n = 0; n < barCounts; n++ ) {
            bars = bars + "|";
        }
        return bars;
    }

    public String getAnalysisReport() {
       compiledReport = text + "\n\n" + analysis;
       return compiledReport;
    }
}
