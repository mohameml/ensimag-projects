using BacktestGrpc.Protos;
using Grpc.Core;
using Google.Protobuf.WellKnownTypes;
using HeadingCalculLibrary.StrategieSystematique;
using PricingLibrary.DataClasses;
using PricingLibrary.MarketDataFeed;
using PricingLibrary.RebalancingOracleDescriptions;

namespace GrpcBacktestServer.Services
{
    public class BacktestRunnerService : BacktestRunner.BacktestRunnerBase
    {
        private readonly ILogger<BacktestRunnerService> _logger;
        public BacktestRunnerService(ILogger<BacktestRunnerService> logger)
        {
            _logger = logger;
        }

        public override Task<BacktestOutput> RunBacktest(BacktestRequest request, ServerCallContext context)
        {



            BasketTestParameters testParameters = new BasketTestParameters
            {
                BasketOption = new Basket
                {
                    Strike = request.TstParams.BasketParams.Strike,
                    Maturity = request.TstParams.BasketParams.Maturity.ToDateTime(),
                    UnderlyingShareIds = request.TstParams.BasketParams.ShareIds.ToArray(),
                    Weights = request.TstParams.BasketParams.Weights.ToArray(),

                },
                PricingParams = new BasketPricingParameters
                {
                    Volatilities = request.TstParams.PriceParams.Vols.ToArray(),
                    Correlations = CastingCorrlinesToCorrelations(request.TstParams.PriceParams.Corrs.ToArray())
                }
                ,
                RebalancingOracleDescription = CastingRebalacingParamsToIRebalacingOracleDescription(request.TstParams.RebParams)

            };
            List<ShareValue> listDataShare = CastingListShareDataToListShareValue(request.Data.DataValues.ToArray()); 
            List<DataFeed> listDataFeed = listDataShare.GroupBy(d => d.DateOfPrice,
                                  t => new { Symb = t.Id.Trim(), Val = t.Value },
                                  (key, g) => new DataFeed(key, g.ToDictionary(e => e.Symb, e => e.Val))).ToList();

            AlgoSystematicStrategie algo = new AlgoSystematicStrategie(testParameters, listDataFeed);
            List<OutputData> listOutputData =   algo.ExecuterAlogSystematicStrategie();


            List<BacktestInfo> listInfos = CastingListOutputDataToListBacktestInfo(listOutputData);

            return Task.FromResult(new BacktestOutput { 
                BacktestInfo = { listInfos.ToArray() },
            });
        }


        private List<BacktestInfo> CastingListOutputDataToListBacktestInfo(List<OutputData> infos)
        {
            List<BacktestInfo> listBacktestInfo = new();

            foreach (OutputData info in infos)
            {
                listBacktestInfo.Add(new BacktestInfo
                {
                    PortfolioValue = info.Value,
                    Date = DateTime.SpecifyKind(info.Date, DateTimeKind.Utc).ToTimestamp(),
                    Delta = {info.Deltas},
                    DeltaStddev = { info.DeltasStdDev },
                    Price = info.Price,
                    PriceStddev = info.PriceStdDev
                });
            }


            return listBacktestInfo;

        }

        private static double[][] CastingCorrlinesToCorrelations(CorrLine[] corrlines)
        {
            double[][] corrs = new double[corrlines.Length][]; 

            for (int i = 0; i < corrlines.Length; i++)
            {
                corrs[i] = corrlines[i].Value.ToArray();

            }


            return corrs;

        }

        private List<ShareValue> CastingListShareDataToListShareValue(ShareData[] listShareData)
        {
            List<ShareValue> listShareValue = new();

            for (int i = 0; i < listShareData.Length; i++)
            {
                ShareValue share = new ShareValue { Id = listShareData[i].Id, DateOfPrice = listShareData[i].Date.ToDateTime(), Value = listShareData[i].Value };
                //listShareValue[i] = share;
                listShareValue.Add(share);
            }


            return listShareValue;

        }

        public static IRebalancingOracleDescription CastingRebalacingParamsToIRebalacingOracleDescription(RebalancingParams rebalancingParams)
        {
            IRebalancingOracleDescription rebalancingOracleDescription;

            switch (rebalancingParams.RebTypeCase)
            {
                case RebalancingParams.RebTypeOneofCase.Regular:
                    RegularRebalancing regular = rebalancingParams.Regular;
                    rebalancingOracleDescription = new RegularOracleDescription() {  Period = regular.Period };
                    break;

                case RebalancingParams.RebTypeOneofCase.Weekly:
                    WeeklyRebalancing weekly = rebalancingParams.Weekly;
                    rebalancingOracleDescription = new WeeklyOracleDescription() { RebalancingDay = (DayOfWeek)weekly.Day };
                    break;

                default:
                    throw new ArgumentException("Erreur lors de la casting IRebalancingOracleDescription vers RebalancingParams");

            }

            return rebalancingOracleDescription;

        }
    }
}


