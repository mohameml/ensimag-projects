using System.Globalization;
using CsvHelper;
using PricingLibrary.MarketDataFeed;

namespace BacktestConsole.ConvertisseurFile
{
    public class CsvToListDataFeed : IConvertisseurFile
    {
        public string Chemin { get ; init ; }

        public CsvToListDataFeed(string chemin)
        {
            Chemin = chemin;
        }

        public List<DataFeed> ConverteCSVToListDataFeed()
        {
            List<ShareValue> listOutputData = new();
            List<DataFeed> listDataFeed = new();

            try
            {
                using (var reader = new StreamReader(Chemin))
                using (var csv = new CsvReader(reader, CultureInfo.InvariantCulture))
                {
                    // Convertir  CSV en une liste d'objets
                    var data = csv.GetRecords<ShareValue>();
                        
                    // Parcourir les objets :
                    foreach (var ele in data)
                    {
                        listOutputData.Add(ele);
                        listDataFeed = listOutputData.GroupBy(d => d.DateOfPrice,
                                  t => new { Symb = t.Id.Trim(), Val = t.Value },
                                  (key, g) => new DataFeed(key, g.ToDictionary(e => e.Symb, e => e.Val))).ToList();


                    }
                }

                return listDataFeed;

            }
            catch (Exception ex)
            {
                throw new IOException($"Error: lors de la convestion du csv vers ListShareValue : {ex.Message}");
            }
        }


    }
}
