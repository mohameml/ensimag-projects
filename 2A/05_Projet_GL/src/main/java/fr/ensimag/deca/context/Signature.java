package fr.ensimag.deca.context;

import java.util.ArrayList;
import java.util.List;

/**
 * Signature of a method (i.e. list of arguments)
 *
 * @author gl10
 * @date 01/01/2024
 */
public class Signature {
    List<Type> args = new ArrayList<Type>();

    public void add(Type t) {
        args.add(t);
    }
    
    public List<Type>  getArgs() {
        return this.args;
    }
    public Type paramNumber(int n) {
        return args.get(n);
    }
    
    public int size() {
        return args.size();
    }

    @Override
    public String toString() {
        return this.getArgs().toString();
    }

    @Override
    public boolean equals(Object o) {

        if(o instanceof Signature) {
            Signature sig =(Signature)o ;
            return this.args.equals(sig.getArgs());
        }
        
        return false;
    }

}
