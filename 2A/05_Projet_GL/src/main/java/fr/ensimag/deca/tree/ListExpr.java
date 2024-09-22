package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;

import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import static fr.ensimag.ima.pseudocode.Register.SP;

/**
 * List of expressions (eg list of parameters).
 *
 * @author gl10
 * @date 01/01/2024
 */
public class ListExpr extends TreeList<AbstractExpr> {


    @Override
    public void decompile(IndentPrintStream s) {
        
        int i = 0;
        int last = size() -1 ;
        for (AbstractExpr c : getList()) {
            c.decompile(s);
            if( i < last )
            {
                s.print(", ");
            }
            i++;
            
        }
    }

    public void CodeGenEmpile(DecacCompiler compiler) {
        int offset = 1;
        for (AbstractExpr exp:this.getList() ) {
            exp.CodeGenEmpile(compiler);
            compiler.addInstruction(new STORE(RegisterManager.getReg(), new RegisterOffset(-offset,SP)));
            offset++;
        }
    }
}
