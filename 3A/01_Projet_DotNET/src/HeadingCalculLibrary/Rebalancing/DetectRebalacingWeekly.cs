

namespace HeadingCalculLibrary.Rebalancing
{
    public class DetectRebalacingWeekly: DetectRebalacingFactory
    {
        private DayOfWeek DayOfRebalancing {  get; init; }

        public DetectRebalacingWeekly(DayOfWeek dayOfRebalancing)
        {
            DayOfRebalancing = dayOfRebalancing;
        }
        public override bool IsRebalancing(DateTime date)
        {
            if(date.DayOfWeek == DayOfRebalancing)
            {
                return true;    
            }

            return false;
        }
    }
}
