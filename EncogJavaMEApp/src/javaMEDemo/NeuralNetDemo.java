
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

package javaMEDemo;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import org.encog.examples.neural.xorbackprop.XorBackprop;
import org.encog.examples.neural.xorbackprop.XorBackpropLoadedModel;

/**
 * This class defines a MIDlet that demonstrates the use of the Encog ME core
 * 
 * @author Sean Barbeau
 */
public class NeuralNetDemo extends MIDlet implements CommandListener {

    private boolean midletPaused = false;

    //Neural Network test
    XorBackprop xORBackprop;
    XorBackpropLoadedModel xORBackpropLoadModel;

    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private Command exitCommand;
    private Command okCommand;
    private Command exitCommand1;
    private Command okCommand1;
    private Command okCommand2;
    private Command okCommand3;
    private TextBox txtNeuralNetOutput;
    private Form formNeuralNetProperties;
    private TextField textFieldMAXEPOCHS;
    private TextField textFieldMIN_ERROR;
    private TextField textFieldNumTests;
    private TextField textFieldTimeBetweenTests;
    private List lstChooseOption;
    //</editor-fold>//GEN-END:|fields|0|

    /**
     * The HelloMIDlet constructor.
     */
    public NeuralNetDemo() {
    }

    //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
    //</editor-fold>//GEN-END:|methods|0|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">//GEN-BEGIN:|0-initialize|0|0-preInitialize
    /**
     * Initilizes the application.
     * It is called only once when the MIDlet is started. The method is called before the <code>startMIDlet</code> method.
     */
    private void initialize() {//GEN-END:|0-initialize|0|0-preInitialize
        // write pre-initialize user code here

//GEN-LINE:|0-initialize|1|0-postInitialize


    }//GEN-BEGIN:|0-initialize|2|
    //</editor-fold>//GEN-END:|0-initialize|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {//GEN-END:|3-startMIDlet|0|3-preAction
        // write pre-action user code here
        switchDisplayable(null, getLstChooseOption());//GEN-LINE:|3-startMIDlet|1|3-postAction
        // write post-action user code here
    }//GEN-BEGIN:|3-startMIDlet|2|
    //</editor-fold>//GEN-END:|3-startMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">//GEN-BEGIN:|4-resumeMIDlet|0|4-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {//GEN-END:|4-resumeMIDlet|0|4-preAction
        // write pre-action user code here
//GEN-LINE:|4-resumeMIDlet|1|4-postAction
        // write post-action user code here
    }//GEN-BEGIN:|4-resumeMIDlet|2|
    //</editor-fold>//GEN-END:|4-resumeMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">//GEN-BEGIN:|5-switchDisplayable|0|5-preSwitch
    /**
     * Switches a current displayable in a display. The <code>display</code> instance is taken from <code>getDisplay</code> method. This method is used by all actions in the design for switching displayable.
     * @param alert the Alert which is temporarily set to the display; if <code>null</code>, then <code>nextDisplayable</code> is set immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {//GEN-END:|5-switchDisplayable|0|5-preSwitch
        // write pre-switch user code here
        Display display = getDisplay();//GEN-BEGIN:|5-switchDisplayable|1|5-postSwitch
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }//GEN-END:|5-switchDisplayable|1|5-postSwitch
        // write post-switch user code here
    }//GEN-BEGIN:|5-switchDisplayable|2|
    //</editor-fold>//GEN-END:|5-switchDisplayable|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
    /**
     * Called by a system to indicated that a command has been invoked on a particular displayable.
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:|7-commandAction|0|7-preCommandAction
        // write pre-action user code here
        if (displayable == formNeuralNetProperties) {//GEN-BEGIN:|7-commandAction|1|37-preAction
            if (command == okCommand3) {//GEN-END:|7-commandAction|1|37-preAction
                // write pre-action user code here
                xORBackprop = new XorBackprop(this);
                xORBackprop.setMAX_EPOCHS(Integer.parseInt(this.textFieldMAXEPOCHS.getString()));
                xORBackprop.setMIN_ERROR(Double.parseDouble(this.textFieldMIN_ERROR.getString()));
                xORBackprop.setNumTests(Integer.parseInt(this.textFieldNumTests.getString()));
                xORBackprop.setTimeBetweenTests(Long.parseLong(this.textFieldTimeBetweenTests.getString()));
                switchDisplayable(null, getTxtNeuralNetOutput());//GEN-LINE:|7-commandAction|2|37-postAction
                xORBackprop.startExample();
            }//GEN-BEGIN:|7-commandAction|3|46-preAction
        } else if (displayable == lstChooseOption) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|3|46-preAction
                // write pre-action user code here
                lstChooseOptionAction();//GEN-LINE:|7-commandAction|4|46-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|5|27-preAction
        } else if (displayable == txtNeuralNetOutput) {
            if (command == exitCommand1) {//GEN-END:|7-commandAction|5|27-preAction
                // write pre-action user code here
                exitMIDlet();//GEN-LINE:|7-commandAction|6|27-postAction
                // write post-action user code here
            } else if (command == okCommand2) {//GEN-LINE:|7-commandAction|7|32-preAction
                // write pre-action user code here
//GEN-LINE:|7-commandAction|8|32-postAction
                if(xORBackprop != null){
                    xORBackprop.stopExample();  //Shutdown the ANN
                }
            }//GEN-BEGIN:|7-commandAction|9|7-postCommandAction
        }//GEN-END:|7-commandAction|9|7-postCommandAction
        // write post-action user code here
    }//GEN-BEGIN:|7-commandAction|10|
    //</editor-fold>//GEN-END:|7-commandAction|10|



    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand ">//GEN-BEGIN:|18-getter|0|18-preInit
    /**
     * Returns an initiliazed instance of exitCommand component.
     * @return the initialized component instance
     */
    public Command getExitCommand() {
        if (exitCommand == null) {//GEN-END:|18-getter|0|18-preInit
            // write pre-init user code here
            exitCommand = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|18-getter|1|18-postInit
            // write post-init user code here
        }//GEN-BEGIN:|18-getter|2|
        return exitCommand;
    }
    //</editor-fold>//GEN-END:|18-getter|2|





    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand ">//GEN-BEGIN:|22-getter|0|22-preInit
    /**
     * Returns an initiliazed instance of okCommand component.
     * @return the initialized component instance
     */
    public Command getOkCommand() {
        if (okCommand == null) {//GEN-END:|22-getter|0|22-preInit
            // write pre-init user code here
            okCommand = new Command("RunANN", Command.OK, 0);//GEN-LINE:|22-getter|1|22-postInit
            // write post-init user code here
        }//GEN-BEGIN:|22-getter|2|
        return okCommand;
    }
    //</editor-fold>//GEN-END:|22-getter|2|



    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand1 ">//GEN-BEGIN:|26-getter|0|26-preInit
    /**
     * Returns an initiliazed instance of exitCommand1 component.
     * @return the initialized component instance
     */
    public Command getExitCommand1() {
        if (exitCommand1 == null) {//GEN-END:|26-getter|0|26-preInit
            // write pre-init user code here
            exitCommand1 = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|26-getter|1|26-postInit
            // write post-init user code here
        }//GEN-BEGIN:|26-getter|2|
        return exitCommand1;
    }
    //</editor-fold>//GEN-END:|26-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtNeuralNetOutput ">//GEN-BEGIN:|25-getter|0|25-preInit
    /**
     * Returns an initiliazed instance of txtNeuralNetOutput component.
     * @return the initialized component instance
     */
    public TextBox getTxtNeuralNetOutput() {
        if (txtNeuralNetOutput == null) {//GEN-END:|25-getter|0|25-preInit
            // write pre-init user code here
            txtNeuralNetOutput = new TextBox("Neural Net Output", null, 5000, TextField.ANY);//GEN-BEGIN:|25-getter|1|25-postInit
            txtNeuralNetOutput.addCommand(getExitCommand1());
            txtNeuralNetOutput.addCommand(getOkCommand2());
            txtNeuralNetOutput.setCommandListener(this);//GEN-END:|25-getter|1|25-postInit
            // write post-init user code here
        }//GEN-BEGIN:|25-getter|2|
        return txtNeuralNetOutput;
    }
    //</editor-fold>//GEN-END:|25-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand1 ">//GEN-BEGIN:|29-getter|0|29-preInit
    /**
     * Returns an initiliazed instance of okCommand1 component.
     * @return the initialized component instance
     */
    public Command getOkCommand1() {
        if (okCommand1 == null) {//GEN-END:|29-getter|0|29-preInit
            // write pre-init user code here
            okCommand1 = new Command("Run ANN", Command.OK, 0);//GEN-LINE:|29-getter|1|29-postInit
            // write post-init user code here
        }//GEN-BEGIN:|29-getter|2|
        return okCommand1;
    }
    //</editor-fold>//GEN-END:|29-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand2 ">//GEN-BEGIN:|31-getter|0|31-preInit
    /**
     * Returns an initiliazed instance of okCommand2 component.
     * @return the initialized component instance
     */
    public Command getOkCommand2() {
        if (okCommand2 == null) {//GEN-END:|31-getter|0|31-preInit
            // write pre-init user code here
            okCommand2 = new Command("Stop", Command.OK, 0);//GEN-LINE:|31-getter|1|31-postInit
            // write post-init user code here
        }//GEN-BEGIN:|31-getter|2|
        return okCommand2;
    }
    //</editor-fold>//GEN-END:|31-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand3 ">//GEN-BEGIN:|36-getter|0|36-preInit
    /**
     * Returns an initiliazed instance of okCommand3 component.
     * @return the initialized component instance
     */
    public Command getOkCommand3() {
        if (okCommand3 == null) {//GEN-END:|36-getter|0|36-preInit
            // write pre-init user code here
            okCommand3 = new Command("Start", Command.OK, 0);//GEN-LINE:|36-getter|1|36-postInit
            // write post-init user code here
        }//GEN-BEGIN:|36-getter|2|
        return okCommand3;
    }
    //</editor-fold>//GEN-END:|36-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formNeuralNetProperties ">//GEN-BEGIN:|33-getter|0|33-preInit
    /**
     * Returns an initiliazed instance of formNeuralNetProperties component.
     * @return the initialized component instance
     */
    public Form getFormNeuralNetProperties() {
        if (formNeuralNetProperties == null) {//GEN-END:|33-getter|0|33-preInit
            // write pre-init user code here
            formNeuralNetProperties = new Form("Neural Net Properties:", new Item[] { getTextFieldMAXEPOCHS(), getTextFieldMIN_ERROR(), getTextFieldNumTests(), getTextFieldTimeBetweenTests() });//GEN-BEGIN:|33-getter|1|33-postInit
            formNeuralNetProperties.addCommand(getOkCommand3());
            formNeuralNetProperties.setCommandListener(this);//GEN-END:|33-getter|1|33-postInit
            // write post-init user code here
        }//GEN-BEGIN:|33-getter|2|
        return formNeuralNetProperties;
    }
    //</editor-fold>//GEN-END:|33-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textFieldMAXEPOCHS ">//GEN-BEGIN:|35-getter|0|35-preInit
    /**
     * Returns an initiliazed instance of textFieldMAXEPOCHS component.
     * @return the initialized component instance
     */
    public TextField getTextFieldMAXEPOCHS() {
        if (textFieldMAXEPOCHS == null) {//GEN-END:|35-getter|0|35-preInit
            // write pre-init user code here
            textFieldMAXEPOCHS = new TextField("Max Number of Epochs", "500", 32, TextField.NUMERIC);//GEN-LINE:|35-getter|1|35-postInit
            // write post-init user code here
        }//GEN-BEGIN:|35-getter|2|
        return textFieldMAXEPOCHS;
    }
    //</editor-fold>//GEN-END:|35-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textFieldMIN_ERROR ">//GEN-BEGIN:|39-getter|0|39-preInit
    /**
     * Returns an initiliazed instance of textFieldMIN_ERROR component.
     * @return the initialized component instance
     */
    public TextField getTextFieldMIN_ERROR() {
        if (textFieldMIN_ERROR == null) {//GEN-END:|39-getter|0|39-preInit
            // write pre-init user code here
            textFieldMIN_ERROR = new TextField("Min. Amount of Error", "0.001", 32, TextField.DECIMAL);//GEN-LINE:|39-getter|1|39-postInit
            // write post-init user code here
        }//GEN-BEGIN:|39-getter|2|
        return textFieldMIN_ERROR;
    }
    //</editor-fold>//GEN-END:|39-getter|2|





    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textFieldNumTests ">//GEN-BEGIN:|42-getter|0|42-preInit
    /**
     * Returns an initiliazed instance of textFieldNumTests component.
     * @return the initialized component instance
     */
    public TextField getTextFieldNumTests() {
        if (textFieldNumTests == null) {//GEN-END:|42-getter|0|42-preInit
            // write pre-init user code here
            textFieldNumTests = new TextField("Number of Tests", "50", 32, TextField.NUMERIC);//GEN-LINE:|42-getter|1|42-postInit
            // write post-init user code here
        }//GEN-BEGIN:|42-getter|2|
        return textFieldNumTests;
    }
    //</editor-fold>//GEN-END:|42-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textFieldTimeBetweenTests ">//GEN-BEGIN:|43-getter|0|43-preInit
    /**
     * Returns an initiliazed instance of textFieldTimeBetweenTests component.
     * @return the initialized component instance
     */
    public TextField getTextFieldTimeBetweenTests() {
        if (textFieldTimeBetweenTests == null) {//GEN-END:|43-getter|0|43-preInit
            // write pre-init user code here
            textFieldTimeBetweenTests = new TextField("Time Between Tests (ms)", "10000", 32, TextField.NUMERIC);//GEN-LINE:|43-getter|1|43-postInit
            // write post-init user code here
        }//GEN-BEGIN:|43-getter|2|
        return textFieldTimeBetweenTests;
    }
    //</editor-fold>//GEN-END:|43-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: lstChooseOption ">//GEN-BEGIN:|44-getter|0|44-preInit
    /**
     * Returns an initiliazed instance of lstChooseOption component.
     * @return the initialized component instance
     */
    public List getLstChooseOption() {
        if (lstChooseOption == null) {//GEN-END:|44-getter|0|44-preInit
            // write pre-init user code here
            lstChooseOption = new List("Please choose option:", Choice.IMPLICIT);//GEN-BEGIN:|44-getter|1|44-postInit
            lstChooseOption.append("Train Local Neural Net", null);
            lstChooseOption.append("Load Model", null);
            lstChooseOption.setCommandListener(this);
            lstChooseOption.setSelectedFlags(new boolean[] { false, false });//GEN-END:|44-getter|1|44-postInit
            // write post-init user code here
        }//GEN-BEGIN:|44-getter|2|
        return lstChooseOption;
    }
    //</editor-fold>//GEN-END:|44-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: lstChooseOptionAction ">//GEN-BEGIN:|44-action|0|44-preAction
    /**
     * Performs an action assigned to the selected list element in the lstChooseOption component.
     */
    public void lstChooseOptionAction() {//GEN-END:|44-action|0|44-preAction
        // enter pre-action user code here
        String __selectedString = getLstChooseOption().getString(getLstChooseOption().getSelectedIndex());//GEN-BEGIN:|44-action|1|48-preAction
        if (__selectedString != null) {
            if (__selectedString.equals("Train Local Neural Net")) {//GEN-END:|44-action|1|48-preAction
                System.out.println("Training model on phone...");
                switchDisplayable(null, getFormNeuralNetProperties());//GEN-LINE:|44-action|2|48-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Load Model")) {//GEN-LINE:|44-action|3|49-preAction
                System.out.println("Loading model from server...");
                xORBackpropLoadModel = new XorBackpropLoadedModel(this);
                switchDisplayable(null, getTxtNeuralNetOutput());//GEN-LINE:|44-action|4|49-postAction
                xORBackpropLoadModel.startExample();
            }//GEN-BEGIN:|44-action|5|44-postAction
        }//GEN-END:|44-action|5|44-postAction
        // enter post-action user code here
    }//GEN-BEGIN:|44-action|6|
    //</editor-fold>//GEN-END:|44-action|6|

    /**
     * Returns a display instance.
     * @return the display instance.
     */
    public Display getDisplay () {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {
        switchDisplayable (null, null);
        destroyApp(true);

        if(xORBackprop != null){
            xORBackprop.stopExample();  //Shutdown the ANN
        }

        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started.
     * Checks whether the MIDlet have been already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp() {
        if (midletPaused) {
            resumeMIDlet ();
        } else {
            initialize ();
            startMIDlet ();
        }
        midletPaused = false;
    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp() {
        midletPaused = true;
    }

    /**
     * Called to signal the MIDlet to terminate.
     * @param unconditional if true, then the MIDlet has to be unconditionally terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional) {

    }

    /*
     *
     */
    public synchronized void updateGUI(String string){

        try{
            if(this.txtNeuralNetOutput.size() > this.txtNeuralNetOutput.getMaxSize() - 100){
                //clear the text box, since its almost full
                this.txtNeuralNetOutput.setString(null);
            }
            this.txtNeuralNetOutput.insert(string + "\n", this.txtNeuralNetOutput.size());
        }catch(IllegalArgumentException e){
            //System.out.println("Error updating GUI:" + e);
            //Exceeded max size, so clear box and try again
            this.txtNeuralNetOutput.setString(null);
            this.txtNeuralNetOutput.insert(string + "\n", this.txtNeuralNetOutput.size());
        }
    }

}
