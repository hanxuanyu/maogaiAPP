package com.hxuanyu.tools.maogai.Bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionsList {

    private ArrayList<Question> Questions;

    public ArrayList<Question> getQuestions() {
        return Questions;
    }

    public void setQuestions(ArrayList<Question> Questions) {
        this.Questions = Questions;
    }

    public static class Question implements Serializable {
        /**
         * content : /第一章/第一节
         * type : 单选题
         * stem : 毛泽东思想确立为党的指导思想并写入党章是在
         * correctAnswer : (D)党的七大
         * analysis :
         * difficult : 易
         * answerCount : 4
         * answers : 延安整风时期/遵义会议/党的六届六中全会/党的七大
         */

        private String content;
        private String type;
        private String stem;
        private String correctAnswer;
        private String analysis;
        private String difficult;
        private int answerCount;
        private String answers;

        public Question(){

        }

        public Question(String content, String type, String stem, String correctAnswer, String analysis, String difficult, int answerCount, String answers) {
            this.content = content;
            this.type = type;
            this.stem = stem;
            this.correctAnswer = correctAnswer;
            this.analysis = analysis;
            this.difficult = difficult;
            this.answerCount = answerCount;
            this.answers = answers;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStem() {
            return stem;
        }

        public void setStem(String stem) {
            this.stem = stem;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public void setCorrectAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
        }

        public String getAnalysis() {
            return analysis;
        }

        public void setAnalysis(String analysis) {
            this.analysis = analysis;
        }

        public String getDifficult() {
            return difficult;
        }

        public void setDifficult(String difficult) {
            this.difficult = difficult;
        }

        public int getAnswerCount() {
            return answerCount;
        }

        public void setAnswerCount(int answerCount) {
            this.answerCount = answerCount;
        }

        public String getAnswers() {
            return answers;
        }

        public void setAnswers(String answers) {
            this.answers = answers;
        }
    }
}
