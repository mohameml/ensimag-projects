namespace HeadingCalculLibrary.Rebalancing
{
    public class DetectRebalacingRegular: DetectRebalacingFactory
    {
        private int Count { get; set; }
        private int Periode { get; init; }

        public DetectRebalacingRegular(int periode)
        {
            Periode = periode;
            Count = 1;
        }
        public override bool IsRebalancing(DateTime date)
        {
            if(Count == Periode)
            {
                Count = 1; 
                return true;
            }    
            Count++;
            return false;
        }
    }
}
