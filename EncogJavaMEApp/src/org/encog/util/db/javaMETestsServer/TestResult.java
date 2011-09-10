
/**
Copyright 2010 Sean Barbeau

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

**/
package org.encog.util.db.javaMETestsServer;


/**
 * This class encapsulates information for a test result
 */
public class TestResult {

    private int platform;

    public void setPlatform(int platform)  {
        this.platform = platform;
    }

    public int getPlatform() {
        return platform;
    }

    private int numEpochs;

    public void setNumEpochs(int numEpochs)  {
        this.numEpochs = numEpochs;
    }

    public int getNumEpochs() {
        return numEpochs;
    }

    private double MinStopError;

    public void setMinStopError(double MinStopError)  {
        this.MinStopError = MinStopError;
    }

    public double getMinStopError() {
        return MinStopError;
    }

    private int mathPOWSolution;

    public void setMathPOWSolution(int mathPOWSolution)  {
        this.mathPOWSolution = mathPOWSolution;
    }

    public int getMathPOWSolution() {
        return mathPOWSolution;
    }

    private double learningRate;

    public void setLearningRate(double learningRate)  {
        this.learningRate = learningRate;
    }

    public double getLearningRate() {
        return learningRate;
    }

    private double momemtum;

    public void setMomemtum(double momemtum)  {
        this.momemtum = momemtum;
    }

    public double getMomemtum() {
        return momemtum;
    }

    private long trainingTime;

    public void setTrainingTime(long trainingTime)  {
        this.trainingTime = trainingTime;
    }

    public long getTrainingTime() {
        return trainingTime;
    }

    private double finalTrainError;

    public void setFinalTrainError(double finalTrainError)  {
        this.finalTrainError = finalTrainError;
    }

    public double getFinalTrainError() {
        return finalTrainError;
    }

    private double test00Actual;

    public void setTest00Actual(double test00Actual)  {
        this.test00Actual = test00Actual;
    }

    public double getTest00Actual() {
        return test00Actual;
    }

    private double test00Error;

    public void setTest00Error(double test00Error)  {
        this.test00Error = test00Error;
    }

    public double getTest00Error() {
        return test00Error;
    }

    private long test00Time;

    public void setTest00Time(long test00Time)  {
        this.test00Time = test00Time;
    }

    public long getTest00Time() {
        return test00Time;
    }

    private double test10Actual;

    public void setTest10Actual(double test10Actual)  {
        this.test10Actual = test10Actual;
    }

    public double getTest10Actual() {
        return test10Actual;
    }

    private double test10Error;

    public void setTest10Error(double test10Error)  {
        this.test10Error = test10Error;
    }

    public double getTest10Error() {
        return test10Error;
    }

    private long test10Time;

    public void setTest10Time(long test10Time)  {
        this.test10Time = test10Time;
    }

    public long getTest10Time() {
        return test10Time;
    }

    private double test01Actual;

    public void setTest01Actual(double test01Actual)  {
        this.test01Actual = test01Actual;
    }

    public double getTest01Actual() {
        return test01Actual;
    }

    private double test01Error;

    public void setTest01Error(double test01Error)  {
        this.test01Error = test01Error;
    }

    public double getTest01Error() {
        return test01Error;
    }

    private long test01Time;

    public void setTest01Time(long test01Time)  {
        this.test01Time = test01Time;
    }

    public long getTest01Time() {
        return test01Time;
    }

    private double test11Actual;

    public void setTest11Actual(double test11Actual)  {
        this.test11Actual = test11Actual;
    }

    public double getTest11Actual() {
        return test11Actual;
    }

    private double test11Error;

    public void setTest11Error(double test11Error)  {
        this.test11Error = test11Error;
    }

    public double getTest11Error() {
        return test11Error;
    }

    private long test11Time;

    public void setTest11Time(long test11Time)  {
        this.test11Time = test11Time;
    }

    public long getTest11Time() {
        return test11Time;
    }
}
