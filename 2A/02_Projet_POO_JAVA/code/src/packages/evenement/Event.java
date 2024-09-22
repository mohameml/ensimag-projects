package packages.evenement;

public abstract class  Event {

    protected long date ;

    
    public Event(long date)
    {
        this.date = date ;
    }



    public long getDate()
    {
        return this.date ;
    }


    public abstract void execute();
}



