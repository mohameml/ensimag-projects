using HeadingCalculLibrary.Rebalancing;
using PricingLibrary.Computations;
using PricingLibrary.DataClasses;
using PricingLibrary.MarketDataFeed;




namespace HeadingCalculLibrary.StrategieSystematique
{
    public class AlgoSystematicStrategie
    {
        private BasketTestParameters TestParameters { get; set; }
        private Pricer Pricer { get; set; }
        private DetectRebalacingFactory DetectRebalacing { get; set; }
        private List<DataFeed> ListDataFeed { get; set; }






        public AlgoSystematicStrategie(BasketTestParameters testParameters , List<DataFeed> listDataFeed)
        {
            TestParameters = testParameters;
            ListDataFeed  =  listDataFeed;
            Pricer = new Pricer(TestParameters);
            

            DetectRebalacing = DetectRebalacingFactory.GetRebalacing(TestParameters.RebalancingOracleDescription);
        }

        public List<OutputData> ExecuterAlogSystematicStrategie()
        {
            DataFeed feedInit = ListDataFeed[0] ;
            PricingResults res0 = Pricer.Price(feedInit.Date, Utilities.Utilities.GetInOrder(feedInit.PriceList, TestParameters.BasketOption.UnderlyingShareIds));
            Dictionary<string, double> composInit = GetNewCompos(res0);

            Portfolio Portfolio = new Portfolio(composInit, ListDataFeed[0], res0.Price);

            List<OutputData>  HistoriquesOutputData = new();
            OutputData output0 = new()
            {
                Value = res0.Price,
                Date = feedInit.Date,
                Price = res0.Price,
                PriceStdDev = res0.PriceStdDev,
                Deltas = res0.Deltas,
                DeltasStdDev = res0.DeltaStdDev
            };

            HistoriquesOutputData.Add(output0);

            foreach (DataFeed feed in ListDataFeed.Skip(1))
            {
                double[] spots = Utilities.Utilities.GetInOrder(feed.PriceList, TestParameters.BasketOption.UnderlyingShareIds);
                var res = Pricer.Price(feed.Date, spots);                
                if (DetectRebalacing.IsRebalancing(feed.Date))
                {
                    double value = Portfolio.GetPortfolioValue(feed);
                    Dictionary<string, double> newCompos = GetNewCompos(res);
                    Portfolio.UpdateCompo(newCompos , feed);
                    OutputData output = new()
                    {
                        Value = value,
                        Date = feed.Date,
                        Price = res.Price,
                        PriceStdDev = res.PriceStdDev,
                        Deltas = res.Deltas,
                        DeltasStdDev = res.DeltaStdDev
                    };

                    HistoriquesOutputData.Add(output);

                }
            }
            return HistoriquesOutputData;
        }


        private  Dictionary<string, double> GetNewCompos(PricingResults res)
        {
            Dictionary<string, double> compos = new();

            for (int i = 0; i < res.Deltas.Length; i++)
            {
                compos.Add(TestParameters.BasketOption.UnderlyingShareIds[i], res.Deltas[i]);
            }

            return compos;
        }


    }
}
