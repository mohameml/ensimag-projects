package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;

public class RegisterManager{

    private static int numR = 2;
    private static int regMax = 15;
    private static int registerOfOp1;

    private static DecacCompiler compiler;

    private static boolean maxOver = false;
    public static void setCompiler(DecacCompiler compiler) {
        RegisterManager.compiler = compiler;
    }


    private static int nombreMaxTemp = 5;
    private static int nombreTempCurrent = 0;


    public static int getNumR(){
        if(numR <= regMax){
            maxOver = false;
        }
        else { /* numR > regMax ei: 16*/
            maxOver = true;
            pushOnStack();
            numR--;
        }
        return numR;
    }

    public static void pushOnStack(){
        compiler.addInstruction(new PUSH(Register.getR(regMax)));
        nombreTempCurrent++;
        if(nombreTempCurrent >= nombreMaxTemp){
            compiler.addInstruction(new BRA(new Label("Max_variable_temp")));
        }
    }

    public static void popFromStack(GPRegister regOp1){
        compiler.addInstruction(new POP(regOp1));
        nombreTempCurrent--;
    }

    public static void setMaxOver(boolean maxOver) {
        RegisterManager.maxOver = maxOver;
    }

    public static GPRegister getReg(){
        return Register.getR(getNumR());
    }

    public static void setRegMax(int regMax) {
        RegisterManager.regMax = regMax;
    }

    public static int getRegMax(){
        return regMax;
    }
    public static void setNumR(int numR){
        RegisterManager.numR = numR;
    }

    public static void setRegisterOfOp1(int registerOfOp1) {
        RegisterManager.registerOfOp1 = registerOfOp1;
    }

    public static int getRegisterOfOp1() {
        return registerOfOp1;
    }

    public static void incrementNum(){
        numR++;
        if (numR> regMax){
            maxOver = true;
        }
    }

    public static int getNombreMaxTemp() {
        return nombreMaxTemp;
    }

    public static boolean maxOver() {
        return maxOver;
    }

    public static void saveRegisters(DecacCompiler compiler){
        compiler.addComment("Début Sauvegarde de registres");
        for (int i = 2; i <= regMax ; i++) {
            compiler.addInstruction(new PUSH(Register.getR(i)));
        }
        compiler.addComment("Fin Sauvegarde de registres");
    }

    public static void restoreRegisters(DecacCompiler compiler){
        compiler.addComment("Début Restauration de registres");
        for (int i = regMax; i >= 2 ; i--) {
            compiler.addInstruction(new POP(Register.getR(i)));
        }
        compiler.addComment("Fin Restauration de registres");
    }
}
