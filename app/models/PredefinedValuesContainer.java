package models;

public class PredefinedValuesContainer {

    public enum ItemCode {
        S200, //Total Services
        S205, //Transportation
        S236, //Travel
        S245, //Communication services
        S249, //Construction
        S253, //Insurance services
        S260, //Financial services
        S262, //Computer and information services
        S266, //Royalties and license fees
        S268, //Other business services
        S287, //Personal, cultural and recreational services
        S291 //Government services
    }

    public enum FlowType {
        M, //Imports
        X //Exports
    }

    public static int years[] = {
            1995, 1996, 1997,
            1998, 1999, 2000,
            2001, 2002, 2003,
            2004, 2005, 2006,
            2007, 2008, 2009,
            2010, 2011, 2012
    };

    public enum FinalValueMethodology {
        E1, //Simple derivation from existing items
        E1_2, //EBOPS item derived when total services is available and only one main category is missing.
        E10, //Estimate based on (national) non-official sources.
        E11, //Estimated breakdown of ‘other business services’ across EBOPS categories, using structure from other years.
        E13, //Reported zeros replaced based on values of S200.
        E2, //Conversion of BPM6 data to BPM5 presentation.
        E3, //Calculation through national BOP growth rate. In these cases, the growth rate of the national BOP is applied item by item to the relevant primary source (IMF, EURO, OECD, UNSD).
        E4, //Derived from regional growth rates. In these cases, a regional growth rate applied to S205, S236, S291, S981 (S200 derived). Only used if nothing else is available. Regions are defined as North America, Central and South America, Europe, CIS, Asia. It could only be used for the last 3 years, and with partner world. Sub-items (eg. S245) are filled in based on the item's share in the last year available and have source code E8.
        E6, //Correction of mistakes in source data, such as implausible negative values, definition not in line with international recommendations, etc.
        E7, //Derived to be negligible/zero.
        E7_3, //Derived as zero, as partner World is zero.
        E7_4, //Derived as zero, as S200 is zero.
        E8, //Estimated using past or future structure (interpolation, backcasting, nowcasting). Partner World only.
        E9, //Estimated based on mirror data.
        M1_1, //Estimated as zero using interpolation.
        M1_2, //Estimated as zero using backcasting.
        M2_1, //Estimated value using interpolation.
        M2_2, //Estimated value using back/nowcasting.
        M3_1, //Total services model: merchandise trade, tourist arrivals/departures, basic gravity variables, partner FE.
        M3_2, //Total services model: merchandise trade, basic gravity variables, partner FE.
        M3_3, //Total services model: tourist arrivals/departures, basic gravity variables, partner FE.
        M3_4, //Total services model: basic gravity variables, partner FE.
        M3_5, //Total services model: basic gravity variables.
        M4_1, //Sectoral model: merchandise trade, trade of relevant item with world, basic gravity variables, partner FE.
        M4_2, //Sectoral model: trade of relevant item with world, basic gravity variables, partner FE.
        M4_3, //Sectoral model: trade of relevant item with world, basic gravity variables.
        M4_4, //Sectoral model: merchandise trade, tourist arrivals, trade of relevant item with world, basic gravity variables, partner FE (exports of S236 only).
        M5_1, //Interpolation of gravity model estimates.
        M5_2 //Back/now casting of gravity model estimates.
    }

}
