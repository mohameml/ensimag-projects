using PricingLibrary.MarketDataFeed;

namespace HeadingCalculLibrary.StrategieSystematique
{
    public class Portfolio
    {

        public Dictionary<string, double> Compositions { get; private set; }
        public double Cash { get; private set; } = 0;
        public DateTime Date { get; private set; }

        public Portfolio(Dictionary<string, double>  dictInit , DataFeed data , double value  )
        {
            Compositions = dictInit;
            Cash = value - Utilities.Utilities.SumProduit(dictInit, data.PriceList);
            Date = data.Date;
        }

        public void UpdateCompo(Dictionary<string, double> Compos ,  DataFeed feed)
        {
            double value = GetPortfolioValue(feed);
            Compositions = Compos;
            Cash = value - Utilities.Utilities.SumProduit(feed.PriceList , Compos);
            Date = feed.Date;
        }

        public double GetPortfolioValue(DataFeed feed)
        {
            double value = Utilities.Utilities.SumProduit(feed.PriceList, Compositions) + Cash * RiskFreeRateProvider.GetRiskFreeRateAccruedValue(Date , feed.Date);
            return value;
        }
    }
}
