package com.nikvay.doctorapplication.model;

public class TestListModel {
    String testName;
    String testNote;
    int indexTest;

    public TestListModel(int indexTest,String testName, String testNote) {
        this.testName = testName;
        this.testNote = testNote;
        this.indexTest = indexTest;
    }



    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestNote() {
        return testNote;
    }

    public void setTestNote(String testNote) {
        this.testNote = testNote;
    }

    public int getIndexTest() {
        return indexTest;
    }

    public void setIndexTest(int indexTest) {
        this.indexTest = indexTest;
    }
}
