package com.example.demo1;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Quiz {
    private Integer quizId;
    private String title;

    public Quiz() {

    }
    public Quiz(String title) {
        this.title = title;
    }

    public static class MetaData {
        public static final String TABLE_NAME = "quizzes";
        public static final String QUIZ_ID = "quiz_id";
        public static final String TITLE = "title";
    }

    public Integer getQuizId() {
        return quizId;
    }

    public String getTitle() {
        return title;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Quiz{quizId=" + quizId + ", title=" + title + "}";
    }

    public static void createTable() {
        try {
            String raw = "CREATE TABLE if not exists %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s VARCHAR(50) )";
            String query = String.format(raw,
                    MetaData.TABLE_NAME,
                    MetaData.QUIZ_ID,
                    MetaData.TITLE);
            System.err.println(query);
            String connectionUrl = "jdbc:sqlite:quiz.db";
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query);
            boolean b = ps.execute();
            System.out.println("models.Quiz.createTable()");
            System.out.println(b);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
