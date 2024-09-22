using System.Globalization;
using CsvHelper;
using PricingLibrary.MarketDataFeed;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace GrpcEvaluation.ConvertisseurFile
{
    public class CsvToListDataFeed : IConvertisseurFile
    {
        public string Chemin { get ; init ; }

        public CsvToListDataFeed(string chemin)
        {
            Chemin = chemin;
        }

        public List<ShareValue> ConverteCSVToListDataFeed()
        {
            List<ShareValue> listOutputData = new();

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
                       

                    }
                }

                return listOutputData;

            }
            catch (Exception ex)
            {
                throw new IOException($"Error: lors de la convestion du csv vers ListShareValue : {ex.Message}");
            }
        }


    }
}
