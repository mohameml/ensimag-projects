using BacktestConsole.ConvertisseurFile;
using PricingLibrary.DataClasses;
using PricingLibrary.MarketDataFeed;
using HeadingCalculLibrary.StrategieSystematique;

namespace BacktestConsole
{
    internal class Program
    {
        static void Main(string[] args)
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
            List<DataFeed> listDataFeed = csv.ConverteCSVToListDataFeed();


            // Algo SystematicStrategie : 
            AlgoSystematicStrategie algo = new(testParameters, listDataFeed);
            List<OutputData> historiquesOutputData = algo.ExecuterAlogSystematicStrategie();

            // Conversion ListOutputData vers json: 
            ListOuputToJson listeToJson = new(args[2]);
            listeToJson.ConverteListOuputToJSON(historiquesOutputData);
        }
    }



}
