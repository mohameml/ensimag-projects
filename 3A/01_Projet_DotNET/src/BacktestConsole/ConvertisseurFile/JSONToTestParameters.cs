using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;
using PricingLibrary.DataClasses;
using PricingLibrary.RebalancingOracleDescriptions;

namespace BacktestConsole.ConvertisseurFile
{
    public class JsonToTestParameters : IConvertisseurFile
    {
        public string Chemin { get; init; }
        private string StringJson { get; set; }



        public JsonToTestParameters(string chemin)
        {
            Chemin = chemin;
            try
            {
                StringJson = File.ReadAllText(chemin);

            }
            catch (Exception ex) 
            { 
                throw new IOException($"faild to ReadAllText from {chemin} : {ex.Message}");
            }

            
        }




        public BasketTestParameters  ConverteJSONToTestParameters()
        {

            string? JSONStringBasketParams = ExtractetringJONStringBasketParams(StringJson);
            string? JSONStringRebalacingDescription = ExtractetringRebalacingDescription(StringJson);
            if (JSONStringBasketParams != null && JSONStringRebalacingDescription != null) 
            {
                BasketTestParameters? testParams = ConvertJSONStringBasketParams(JSONStringBasketParams);
                IRebalancingOracleDescription? reabalicngOracleDescription = ConvertJSONStringRebalacingDescription(JSONStringRebalacingDescription);


                if(testParams != null && reabalicngOracleDescription!= null)
                {
                    BasketTestParameters TestParameters = new();

                    TestParameters.BasketOption = testParams.BasketOption;
                    TestParameters.PricingParams = testParams.PricingParams;
                    TestParameters.RebalancingOracleDescription = reabalicngOracleDescription;

                    return TestParameters;

                }
                else
                {

                    throw new InvalidCastException("Erreur : ConvertJSONStringBasketParams return null or ConvertJSONStringRebalacingDescription return null");
                }

            } 
            else
            {

                throw new IOException("Erreur : ExtractetringJONStringBasketParams return null or ExtractetringRebalacingDescription return null");
            }

        }


        public static BasketTestParameters? ConvertJSONStringBasketParams(string JSONStringBasketParams)
        {
            try
            {
                var options = new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true
                };

                BasketTestParameters? testParams = JsonSerializer.Deserialize<BasketTestParameters>(JSONStringBasketParams , options);
                return testParams;
            
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error : {ex.Message} \n{ex.StackTrace}");
                Environment.Exit(1);
            }

            return null;

        }
        
        /**
         * <summary>private void ConvertRebalancingOracleDescription()</summary>
         * 
         */
        public static  IRebalancingOracleDescription? ConvertJSONStringRebalacingDescription(string JSONStringRebalacingDescription)
        {
            try
            {
                byte[] jsonUtf8Bytes = Encoding.UTF8.GetBytes(JSONStringRebalacingDescription);
                Utf8JsonReader jsonReader = new Utf8JsonReader(jsonUtf8Bytes);


                RebalancingOracleDescriptionConverter jsonConverter = new();
                var options = new JsonSerializerOptions()
                {
                    PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
                    Converters = { new JsonStringEnumConverter(), new RebalancingOracleDescriptionConverter() }
                };
                IRebalancingOracleDescription? reabalicngOracleDescription = jsonConverter.Read(ref jsonReader, typeof(IRebalancingOracleDescription), options);

                return reabalicngOracleDescription;


            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error : {ex.Message} \n{ex.StackTrace}");
                Environment.Exit(1);
            }
            return null;
        }

        public static string? ExtractetringRebalacingDescription(string stringJSON)
        {

            try
            {

                using (JsonDocument doc = JsonDocument.Parse(stringJSON))
                {
                    // Obtenir la racine du document JSON
                    JsonElement root = doc.RootElement;

                    // Vérifier si la clé 'rebalancingOracleDescription' existe
                    if (root.TryGetProperty("rebalancingOracleDescription", out JsonElement rebalancingOracleDescription))
                    {

                        return rebalancingOracleDescription.ToString();

                    }

                }
            }
            catch (Exception ex)
            {
                throw new ArgumentException($"Error in ExtractetringRebalacingDescription , {ex.Message}");

            }
            return null;
        }

        public static string? ExtractetringJONStringBasketParams(string stringJSON)
        {
            try
            {

                using (JsonDocument doc = JsonDocument.Parse(stringJSON))
                {
                    // Obtenir la racine du document JSON
                    JsonElement root = doc.RootElement;

                    if (root.TryGetProperty("pricingParams", out JsonElement pricingParams)
                        &&
                        root.TryGetProperty("basketOption", out JsonElement basketOption))
                    {

                        string JONStringBasketParams = "{\n\"PricingParams\" :" + pricingParams.ToString() + ",\n" + "\"BasketOption\" :" + basketOption.ToString() + "\n}";

                        return JONStringBasketParams;
                    }


                }

            }
            catch (Exception ex)
            {
               
                throw new ArgumentException($"Error in ExtractetringJONStringBasketParams , {ex.Message}");
            }
            return null;
        }


    }
}
