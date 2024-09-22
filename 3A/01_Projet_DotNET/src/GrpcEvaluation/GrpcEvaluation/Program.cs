using System.Threading.Tasks;
using BacktestGrpc.Protos;
using Google.Protobuf.WellKnownTypes;
using Grpc.Net.Client;
using GrpcEvaluation;
using GrpcEvaluation.ConvertisseurFile;
using PricingLibrary.DataClasses;
using PricingLibrary.MarketDataFeed;
using PricingLibrary.RebalancingOracleDescriptions;

namespace GrpcEvaluation
{
    public class Program
    {
        public static async Task Main(string[] args)
        {

            // verification args : 
            if (args.Length != 3)
            {
                Console.WriteLine($"Erreur : Taille attendue du args est 3, alors que la taille est {args.Length}");
                Environment.Exit(1);

            }

            if (!File.Exists(args[0]) || !File.Exists(args[1]))
            {
                Console.WriteLine("Chemin non valide passe en args");
                Environment.Exit(1);
            }

            if (!args[0].EndsWith(".json") || !args[1].EndsWith(".csv") || !args[2].EndsWith(".json"))
            {
                Console.WriteLine("Les extensions de fichiers attendues sont : .json, .csv, .json");
                Environment.Exit(1);
            }


            // JSONToTestParams
            JsonToTestParameters json = new(args[0]);
            BasketTestParameters testParameters = json.ConverteJSONToTestParameters();

            // csvToListDataFeed  :
            CsvToListDataFeed csv = new(args[1]);
            List<ShareValue> listShare = csv.ConverteCSVToListDataFeed();


            BacktestRequest Params = new BacktestRequest
            {
                TstParams = new TestParams
                {
                    PriceParams = new PricingParams
                    {
                        Vols = { testParameters.PricingParams.Volatilities },
                        Corrs = { CastingCorrelationsToCorrLines(testParameters.PricingParams.Correlations) },
            
                    },
                    BasketParams = new BasketParams
                    {
                        Strike = testParameters.BasketOption.Strike,
                        Maturity = DateTime.SpecifyKind(testParameters.BasketOption.Maturity, DateTimeKind.Utc).ToTimestamp(),
                        ShareIds = { testParameters.BasketOption.UnderlyingShareIds },
                        Weights = { testParameters.BasketOption.Weights },

                    },
                    RebParams = CastingIRebalacingOracleDescriptionToRebalancingParams(testParameters.RebalancingOracleDescription)
                },
                Data = new DataParams
                {
                    DataValues = { CastingListShareValueToListShareData(listShare) }
                }

            };



            using var channel = GrpcChannel.ForAddress("http://localhost:5000");
            var client = new BacktestRunner.BacktestRunnerClient(channel);

            var replyBacktest = await client.RunBacktestAsync(Params);

            List<OutputData> listOutputData = CastingListBacktestInfoToListOutputData(replyBacktest.BacktestInfo.ToList());

            // Conversion ListOutputData vers json: 
            ListOuputToJson listeToJson = new(args[2]);
            listeToJson.ConverteListOuputToJSON(listOutputData);
        }


        public static CorrLine[] CastingCorrelationsToCorrLines(double[][] correlations)
        {
            CorrLine[] corrs = new CorrLine[correlations.Length];

            for (int i = 0; i < correlations.Length; i++) 
            {
                CorrLine cor = new CorrLine() { Value = { correlations[i] } };
                corrs[i] = cor;

            }


            return corrs;

        }

        public static ShareData[] CastingListShareValueToListShareData(List<ShareValue> listShareValue)
        {
            ShareData[] listShareData = new ShareData[listShareValue.Count()];

            for (int i = 0; i < listShareValue.Count(); i++)
            {
                ShareData share = new ShareData { Id  = listShareValue[i].Id , Date = DateTime.SpecifyKind(listShareValue[i].DateOfPrice, DateTimeKind.Utc).ToTimestamp(), Value = listShareValue[i].Value };
                listShareData[i] = share;
            }


            return listShareData;

        }

        public static List<OutputData> CastingListBacktestInfoToListOutputData(List<BacktestInfo> infos)
        {
            List<OutputData> listOutputData = new();

            foreach (BacktestInfo info in infos)
            {
                listOutputData.Add(new OutputData
                {
                    Value = info.PortfolioValue,
                    Date = info.Date.ToDateTime(),
                    Deltas = info.Delta.ToArray() , 
                    DeltasStdDev = info.DeltaStddev.ToArray() ,
                    Price = info.Price ,
                    PriceStdDev = info.PriceStddev 
                });
            }


            return listOutputData;

        }

        public static RebalancingParams CastingIRebalacingOracleDescriptionToRebalancingParams(IRebalancingOracleDescription rebalacingOracle)
        {
            RebalancingParams rebalancingParams;
            switch (rebalacingOracle.Type) 
            {
                case RebalancingOracleType.Regular:
                    RegularOracleDescription regOracle = (RegularOracleDescription)rebalacingOracle;
                    RegularRebalancing regular = new RegularRebalancing() { Period = regOracle .Period};
                    rebalancingParams = new RebalancingParams() {Regular = regular};
                    break;

                case RebalancingOracleType.Weekly:
                    WeeklyOracleDescription weeklyOracle = (WeeklyOracleDescription)rebalacingOracle;
                    WeeklyRebalancing weekly = new WeeklyRebalancing() { Day = (ProtoDayOfWeek)weeklyOracle.RebalancingDay };
                    rebalancingParams = new RebalancingParams() { Weekly = weekly };
                    break;

                default:
                    throw new ArgumentException("Erreur lors de la casting IRebalancingOracleDescription vers RebalancingParams");

            }

            return rebalancingParams;

        }
    }
}


