
namespace HeadingCalculLibrary.Utilities
{
    public static class Utilities
    {


        public static double SumProduit(Dictionary<string, double> dict1 , Dictionary<string, double> dict2)
        {

            double res = 0;
            foreach (string key in dict1.Keys)
            {
                res += dict1[key] * dict2[key];
            }

            return res;
        }

        public static double[] GetInOrder(Dictionary<string, double> dict, string[] Ids)
        {
            double[] order = new double[Ids.Length];
            for (int i = 0; i < Ids.Length; i++)
            {
                order[i] = dict[Ids[i]];
            }


            return order;
        }
    }
}
