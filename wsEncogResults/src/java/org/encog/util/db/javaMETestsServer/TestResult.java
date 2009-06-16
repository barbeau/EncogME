/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.encog.util.db.javaMETestsServer;

/**
 *
 * @author Sean
 */
public class TestResult {

    public static int PLATFORM_DESKTOP = 0;
    public static int PLATFORM_EMULATOR = 1;
    public static int PLATFORM_PHONE = 2;

    private int platform;
    private int numEpochs;
    private double MinStopError;
    private int mathPOWSolution;
    private double learningRate;
    private double momemtum;
    private long trainingTime;
    private double finalTrainError;

    private double test00Actual;
    private double test00Error;
    private long test00Time;

    private double test10Actual;
    private double test10Error;
    private long test10Time;

    private double test01Actual;
    private double test01Error;
    private long test01Time;

    private double test11Actual;
    private double test11Error;
    private long test11Time;


    public TestResult() {
    }

    public TestResult(int numEpochs, double MinStopError, int mathPOWSolution, double learningRate, double momemtum, long trainingTime, double finalTrainError, double test00Actual, double test00Error, long test00Time, double test10Actual, double test10Error, long test10Time, double test01Actual, double test01Error, long test01Time, double test11Actual, double test11Error, long test11Time) {
        this.numEpochs = numEpochs;
        this.MinStopError = MinStopError;
        this.mathPOWSolution = mathPOWSolution;
        this.learningRate = learningRate;
        this.momemtum = momemtum;
        this.trainingTime = trainingTime;
        this.finalTrainError = finalTrainError;
        this.test00Actual = test00Actual;
        this.test00Error = test00Error;
        this.test00Time = test00Time;
        this.test10Actual = test10Actual;
        this.test10Error = test10Error;
        this.test10Time = test10Time;
        this.test01Actual = test01Actual;
        this.test01Error = test01Error;
        this.test01Time = test01Time;
        this.test11Actual = test11Actual;
        this.test11Error = test11Error;
        this.test11Time = test11Time;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    
    public double getFinalTrainError() {
        return finalTrainError;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public int getMathPOWSolution() {
        return mathPOWSolution;
    }

    public double getMinStopError() {
        return MinStopError;
    }

    public double getMomemtum() {
        return momemtum;
    }

    public int getNumEpochs() {
        return numEpochs;
    }

    public double getTest00Actual() {
        return test00Actual;
    }

    public double getTest00Error() {
        return test00Error;
    }

    public long getTest00Time() {
        return test00Time;
    }

    public double getTest01Actual() {
        return test01Actual;
    }

    public double getTest01Error() {
        return test01Error;
    }

    public long getTest01Time() {
        return test01Time;
    }

    public double getTest10Actual() {
        return test10Actual;
    }

    public double getTest10Error() {
        return test10Error;
    }

    public long getTest10Time() {
        return test10Time;
    }

    public double getTest11Actual() {
        return test11Actual;
    }

    public double getTest11Error() {
        return test11Error;
    }

    public long getTest11Time() {
        return test11Time;
    }

    public long getTrainingTime() {
        return trainingTime;
    }

    public void setFinalTrainError(double finalTrainError) {
        this.finalTrainError = finalTrainError;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public void setMathPOWSolution(int mathPOWSolution) {
        this.mathPOWSolution = mathPOWSolution;
    }

    public void setMinStopError(double MinStopError) {
        this.MinStopError = MinStopError;
    }

    public void setNumEpochs(int numEpochs) {
        this.numEpochs = numEpochs;
    }

    public void setMomemtum(double momemtum) {
        this.momemtum = momemtum;
    }

    public void setTest00Actual(double test00Actual) {
        this.test00Actual = test00Actual;
    }

    public void setTest00Error(double test00Error) {
        this.test00Error = test00Error;
    }

    public void setTest00Time(long test00Time) {
        this.test00Time = test00Time;
    }

    public void setTest01Actual(double test01Actual) {
        this.test01Actual = test01Actual;
    }

    public void setTest01Error(double test01Error) {
        this.test01Error = test01Error;
    }

    public void setTest01Time(long test01Time) {
        this.test01Time = test01Time;
    }

    public void setTest10Actual(double test10Actual) {
        this.test10Actual = test10Actual;
    }

    public void setTest10Error(double test10Error) {
        this.test10Error = test10Error;
    }

    public void setTest10Time(long test10Time) {
        this.test10Time = test10Time;
    }

    public void setTest11Actual(double test11Actual) {
        this.test11Actual = test11Actual;
    }

    public void setTest11Error(double test11Error) {
        this.test11Error = test11Error;
    }

    public void setTest11Time(long test11Time) {
        this.test11Time = test11Time;
    }

    public void setTrainingTime(long trainingTime) {
        this.trainingTime = trainingTime;
    }

    
}
