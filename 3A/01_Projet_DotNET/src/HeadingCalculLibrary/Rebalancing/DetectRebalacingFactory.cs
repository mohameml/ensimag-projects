using PricingLibrary.RebalancingOracleDescriptions;

namespace HeadingCalculLibrary.Rebalancing
{
    public abstract class DetectRebalacingFactory
    {
        public abstract bool IsRebalancing(DateTime date);


        public static  DetectRebalacingFactory GetRebalacing(IRebalancingOracleDescription rebalacing) {



            switch(rebalacing.Type)
            {
                case RebalancingOracleType.Regular:
                    RegularOracleDescription? regularDescription = rebalacing as RegularOracleDescription;

                    if (regularDescription != null)
                    {

                        return new DetectRebalacingRegular(regularDescription.Period);

                    }
                    else
                    {
                        throw new InvalidCastException("Erreur lors du casting de IRebalancingOracleDescription vers RegularOracleDescription");

                    }
                
                case RebalancingOracleType.Weekly:

                    WeeklyOracleDescription? weeklyDescription = rebalacing as WeeklyOracleDescription;

                    if (weeklyDescription != null)
                    {

                        return new DetectRebalacingWeekly(weeklyDescription.RebalancingDay);

                    }
                    else
                    {
                        throw new InvalidCastException("Erreur lors du casting de IRebalancingOracleDescription vers WeeklyOracleDescription");
                    }
                default:
                    throw new ArgumentException("Argument Exception :  rebalacing.Type ");
            }


        }
    }
}
