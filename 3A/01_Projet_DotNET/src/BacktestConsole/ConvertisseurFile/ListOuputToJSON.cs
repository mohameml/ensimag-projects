using PricingLibrary.DataClasses;
using System.Text.Json;

namespace BacktestConsole.ConvertisseurFile
{
    public class ListOuputToJson : IConvertisseurFile
    {
        public string Chemin { get; init; }
    
        public ListOuputToJson(string chemin)
        {
            Chemin = chemin;
        }

        public void ConverteListOuputToJSON(List<OutputData> listOutputData)
        {
            // Sérialiser la liste en JSON et l'écrire dans le fichier

            var options = new JsonSerializerOptions()
            {
                PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
                WriteIndented = true
            };


            try
            {
                string jsonString = JsonSerializer.Serialize(listOutputData, options);

                // Écrire dans le fichier
                File.WriteAllText(Chemin, jsonString);
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error : {ex.Message} \n{ex.StackTrace}");
                Environment.Exit(1);
            }
        }
    }
}
