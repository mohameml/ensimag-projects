package packages.evenement;

import java.util.ArrayList;

public class EventManager
{
    private long  currentDate ;
    

    private ArrayList<Event> listEvents ;

    private ArrayList<Event> histoeyEvents;

    



    public EventManager()
    {
        this.listEvents = new ArrayList<>();
        this.histoeyEvents = new ArrayList<>();
        this.currentDate = 0 ;
    }
    




    public void  addEvent(Event e)
    {
     
        listEvents.add(e) ;
        
        histoeyEvents.add(e);


    }   

    public void update_currentDate()
    {
        long min = this.currentDate ;

        for(Event e : this.listEvents)
        {
            if(e.getDate() < min )
            {
                min = e.getDate();
            }
        }


        this.currentDate = min ;
    }

    public void next()
    {
        this.currentDate++;

        ArrayList<Event> events = new ArrayList<>();

        for(Event e : listEvents)
        {
            if(e.getDate() < this.currentDate)
            {
                events.add(e);
            }
        }



        // executer les evenements et le supprimer :

        for(Event e : events)
        {
            e.execute();
            listEvents.remove(e);
        }
        
    }

    public boolean isFinished()
    {

        return  listEvents.isEmpty() ;
    }



    public void restart()
    {
        for(Event e: histoeyEvents)
        {
            listEvents.add(e);
        }
    }

}


