package fr.ensimag.deca;

import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Main class for the command-line Deca compiler.
 *
 * @author gl10
 * @date 01/01/2024
 */
public class DecacMain {
    private static Logger LOG = Logger.getLogger(DecacMain.class);
    
    public static void main(String[] args) {
        // example log4j message.
        LOG.info("Decac compiler started");
        boolean error = false;
        final CompilerOptions options = new CompilerOptions();
        try {
            options.parseArgs(args);
        } catch (CLIException e) {
            System.err.println("Error during option parsing:\n"
                    + e.getMessage());
            options.displayUsage();
            System.exit(1);
        }
        if (options.getPrintBanner()) {
            System.out.println("--------------------------------------");
            System.out.println("|                                    |");
            System.out.println("|                                    |");
            System.out.println("|                                    |");
            System.out.println("|   gl10                             |");
            System.out.println("|   CHRIF M'HAMED Mohamedou          |");
            System.out.println("|   DIAB Dana                        |");
            System.out.println("|   KONE Madou                       |");
            System.out.println("|   MOHAMED AHMED Mohamed Lemine     |");
            System.out.println("|                                    |");
            System.out.println("|                                    |");
            System.out.println("|                                    |");
            System.out.println("--------------------------------------");
        }
        if (options.getSourceFiles().isEmpty()) {
            //throw new UnsupportedOperationException("decac without argument not yet implemented");
            options.displayUsage();
        }
        if (options.getParallel()) {
            // A FAIRE : instancier DecacCompiler pour chaque fichier à
            // compiler, et lancer l'exécution des méthodes compile() de chaque
            // instance en parallèle. Il est conseillé d'utiliser
            // java.util.concurrent de la bibliothèque standard Java.

            List<File> sourceFiles = options.getSourceFiles();
            int numThreads = Runtime.getRuntime().availableProcessors(); // Adjust as needed

            ExecutorService executor = Executors.newFixedThreadPool(numThreads);
            List<Future<Boolean>> futures = new ArrayList<>();

            for (File source : sourceFiles) {
                Callable<Boolean> compilationTask = () -> {
                    DecacCompiler compiler = new DecacCompiler(options, source);
                    if (options.getParse()) {
                        return compiler.parse();
                    } else if (options.getVerification()) {
                        return compiler.verify();
                    } else {

                        return compiler.compile();
                    }
                };
                Future<Boolean> future = executor.submit(compilationTask);
                futures.add(future);
            }

            // Wait for all tasks to complete
            for (Future<Boolean> future : futures) {
                try {
                    if (future.get()) {
                        error = true;
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace(); // Handle exceptions as needed
                }
            }

            // Shutdown the executor
            executor.shutdown();

            //throw new UnsupportedOperationException("Parallel build not yet implemented");
        }
        else {
            if (options.getParse()){
                for (File source : options.getSourceFiles()) {
                    DecacCompiler compiler = new DecacCompiler(options, source);
                    if (compiler.parse()) {
                        error = true;
                    }
                }
            } else if (options.getVerification()) {
                for (File source : options.getSourceFiles()) {
                    DecacCompiler compiler = new DecacCompiler(options, source);
                    if (compiler.verify()) {
                        error = true;
                    }
                }
            }
            else{
                for (File source : options.getSourceFiles()) {
                    DecacCompiler compiler = new DecacCompiler(options, source);
                    if (compiler.compile()) {
                        error = true;
                    }
                }
            }

        }
        System.exit(error ? 1 : 0);
    }
}
