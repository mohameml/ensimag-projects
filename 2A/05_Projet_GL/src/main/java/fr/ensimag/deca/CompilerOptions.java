package fr.ensimag.deca;

import fr.ensimag.deca.codegen.RegisterManager;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User-specified options influencing the compilation.
 *
 * @author gl10
 * @date 01/01/2024
 */
public class CompilerOptions {
    public static final int QUIET = 0;
    public static final int INFO  = 1;
    public static final int DEBUG = 2;
    public static final int TRACE = 3;
    public int getDebug() {
        return debug;
    }

    public boolean getParallel() {
        return parallel;
    }

    public boolean getPrintBanner() {
        return printBanner;
    }

    public boolean getParse() {
        return parse;
    }

    public boolean getNocheck() {
        return nocheck;
    }

    public boolean getVerification() {
        return verification;
    }

    public List<File> getSourceFiles() {
        return Collections.unmodifiableList(sourceFiles);
    }

    private int debug = 0;
    private boolean parallel = false;
    private boolean printBanner = false;
    private boolean parse = false;
    private boolean verification = false;
    private boolean nocheck = false;
    private List<File> sourceFiles = new ArrayList<File>();

    
    public void parseArgs(String[] args) throws CLIException {
        // A FAIRE : parcourir args pour positionner les options correctement.
        int i=0;
        if (args.length==0){
            displayUsage();
        }
        while (i<args.length){
            if (args[i].equals("-P")){
                parallel = true;
            }
            else if (args[i].equals("-n")){
                nocheck = true;
            }
            else if (args[i].equals("-b")){
                printBanner = true;
                if (args.length != 1){
                    throw new CLIException("-b ");
                }
            }
            else if (args[i].equals("-p")){
                if (verification){
                    throw new CLIException("Les options -p et -v sont incompatibles");
                }
                parse = true;
            }
            else if (args[i].equals("-v")){
                if (parse){
                    throw new CLIException("Les options -p et -v sont incompatibles");
                }
                verification = true;
            }
            else if (args[i].equals("-r")){
                i++;
                try {
                    RegisterManager.setRegMax(Integer.parseInt(args[i]) - 1);
                } catch (NumberFormatException e) {
                    throw  new CLIException("-r X : X doit être un entier");
                }
                if (RegisterManager.getRegMax() + 1>15 || RegisterManager.getRegMax() + 1<4){
                    throw new CLIException("-r X : X doit être un entier strictement inférieur à 16 et supérieur à 3");
                }
            }
            else if (args[i].equals("-d")) {
                if (debug<3){
                    debug++;
                }
            }
            else{
                if (args[i].startsWith("-")){
                    throw new CLIException(args[i]+" cette option n'existe pas");
                }
                else{
                    this.sourceFiles.add((new File(args[i])));
                }
            }
            i++;
        }

        /*Fin*/
        Logger logger = Logger.getRootLogger();
        // map command-line debug option to log4j's level.
        switch (getDebug()) {
        case QUIET: break; // keep default
        case INFO:
            logger.setLevel(Level.INFO); break;
        case DEBUG:
            logger.setLevel(Level.DEBUG); break;
        case TRACE:
            logger.setLevel(Level.TRACE); break;
        default:
            logger.setLevel(Level.ALL); break;
        }
        logger.info("Application-wide trace level set to " + logger.getLevel());

        boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect!!!
        if (assertsEnabled) {
            logger.info("Java assertions enabled");
        } else {
            logger.info("Java assertions disabled");
        }

    }

    protected void displayUsage() {
        System.out.println("decac [[-p | -v] [-n] [-r X] [-d]* [-P] <fichier deca>...] | [-b]");
    }
}
