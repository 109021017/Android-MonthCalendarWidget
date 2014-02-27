/**
 * Based on Microsoft Open Source C# Code.
 */

package com.example.android.monthcalendarwidget;

import java.util.Calendar;
import java.util.GregorianCalendar;

 /*
 **  Calendar support range: 
 **      Calendar			Minimum			Maximum 
 **      ==========			==========		==========
 **      Gregorian			1901/02/19		2101/01/28 
 **      ChineseLunisolar	1901/01/01		2100/12/29
 */
public class ChineseLunisolarCalendar {

    private final static int MIN_LUNISOLAR_YEAR = 1901; 
    private final static int MAX_LUNISOLAR_YEAR = 2100; 

    private final static int MIN_GREGORIAN_YEAR = 1901; 
    private final static int MIN_GREGORIAN_MONTH = 2;
    private final static int MIN_GREGORIAN_DAY = 19;

    private final static int MAX_GREGORIAN_YEAR = 2101; 
    private final static int MAX_GREGORIAN_MONTH = 1;
    private final static int MAX_GREGORIAN_DAY = 28; 

    private final static Calendar MIN_DATE = new GregorianCalendar(MIN_GREGORIAN_YEAR, MIN_GREGORIAN_MONTH, MIN_GREGORIAN_DAY);
    private final static Calendar MAX_DATE = new GregorianCalendar(MAX_GREGORIAN_YEAR, MAX_GREGORIAN_MONTH, MAX_GREGORIAN_DAY, 23, 59, 999);


    /**
     * # of days so far in the solar year
     * 公历. 平年里该月前的天数.
     */
    private final static int[] DAYS_TO_MONTH_365 = 
    {
        0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334 
    };
    /**
     * 公历. 闰年里该月前的天数.
     */
    private final static int[] DAYS_TO_MONTH_366 =
    { 
        0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335
    }; 
    
    private static final int INDEX_LEAP_MONTH = 0;
    private static final int INDEX_JAN1_MONTH = 1;
    private static final int INDEX_JAN1_DATE = 2;
    private static final int INDEX_NUM_OF_DAYS_PER_MONTH = 3; 
    
	private static final int[][] YEAR_INFO ={
		 /* Y:		年份
		  * LM: 	闰月
		  * Lmon:	元旦对应农历日期的月
		  * Lday:	元旦对应农历日期的日
		  * DaysPerMonth：每个月的天数
		  * 
		  * Y		LM		Lmon	Lday	DaysPerMonth	D1	D2	D3	D4	D5	D6	D7	D8	D9	D10	D11	D12	D13	#Days
		1901	*/{	0,		2,		19,		19168	},/*	29	30	29	29	30	29	30	29	30	30	30	29	0	354 
		1902	*/{	0,		2,		8,		42352	},/*	30	29	30	29	29	30	29	30	29	30	30	30	0	355
		1903	*/{	5,		1,		29,		21096	},/*	29	30	29	30	29	29	30	29	29	30	30	29	30	383 
		1904	*/{	0,		2,		16,		53856	},/*	30	30	29	30	29	29	30	29	29	30	30	29	0	354 
		1905	*/{	0,		2,		4,		55632	},/*	30	30	29	30	30	29	29	30	29	30	29	30	0	355
		1906	*/{	4,		1,		25,		27304	},/*	29	30	30	29	30	29	30	29	30	29	30	29	30	384 
		1907	*/{	0,		2,		13,		22176	},/*	29	30	29	30	29	30	30	29	30	29	30	29	0	354
		1908	*/{	0,		2,		2,		39632	},/*	30	29	29	30	30	29	30	29	30	30	29	30	0	355
		1909	*/{	2,		1,		22,		19176	},/*	29	30	29	29	30	29	30	29	30	30	30	29	30	384
		1910	*/{	0,		2,		10,		19168	},/*	29	30	29	29	30	29	30	29	30	30	30	29	0	354 
		1911	*/{	6,		1,		30,		42200	},/*	30	29	30	29	29	30	29	29	30	30	29	30	30	384
		1912	*/{	0,		2,		18,		42192	},/*	30	29	30	29	29	30	29	29	30	30	29	30	0	354 
		1913	*/{	0,		2,		6,		53840	},/*	30	30	29	30	29	29	30	29	29	30	29	30	0	354 
		1914	*/{	5,		1,		26,		54568	},/*	30	30	29	30	29	30	29	30	29	29	30	29	30	384
		1915	*/{	0,		2,		14,		46400	},/*	30	29	30	30	29	30	29	30	29	30	29	29	0	354 
		1916	*/{	0,		2,		3,		54944	},/*	30	30	29	30	29	30	30	29	30	29	30	29	0	355
		1917	*/{	2,		1,		23,		38608	},/*	30	29	29	30	29	30	30	29	30	30	29	30	29	384
		1918	*/{	0,		2,		11,		38320	},/*	30	29	29	30	29	30	29	30	30	29	30	30	0	355
		1919	*/{	7,		2,		1,		18872	},/*	29	30	29	29	30	29	29	30	30	29	30	30	30	384 
		1920	*/{	0,		2,		20,		18800	},/*	29	30	29	29	30	29	29	30	29	30	30	30	0	354
		1921	*/{	0,		2,		8,		42160	},/*	30	29	30	29	29	30	29	29	30	29	30	30	0	354 
		1922	*/{	5,		1,		28,		45656	},/*	30	29	30	30	29	29	30	29	29	30	29	30	30	384 
		1923	*/{	0,		2,		16,		27216	},/*	29	30	30	29	30	29	30	29	29	30	29	30	0	354
		1924	*/{	0,		2,		5,		27968	},/*	29	30	30	29	30	30	29	30	29	30	29	29	0	354 
		1925	*/{	4,		1,		24,		44456	},/*	30	29	30	29	30	30	29	30	30	29	30	29	30	385
		1926	*/{	0,		2,		13,		11104	},/*	29	29	30	29	30	29	30	30	29	30	30	29	0	354
		1927	*/{	0,		2,		2,		38256	},/*	30	29	29	30	29	30	29	30	29	30	30	30	0	355
		1928	*/{	2,		1,		23,		18808	},/*	29	30	29	29	30	29	29	30	29	30	30	30	30	384 
		1929	*/{	0,		2,		10,		18800	},/*	29	30	29	29	30	29	29	30	29	30	30	30	0	354
		1930	*/{	6,		1,		30,		25776	},/*	29	30	30	29	29	30	29	29	30	29	30	30	29	383 
		1931	*/{	0,		2,		17,		54432	},/*	30	30	29	30	29	30	29	29	30	29	30	29	0	354 
		1932	*/{	0,		2,		6,		59984	},/*	30	30	30	29	30	29	30	29	29	30	29	30	0	355
		1933	*/{	5,		1,		26,		27976	},/*	29	30	30	29	30	30	29	30	29	30	29	29	30	384 
		1934	*/{	0,		2,		14,		23248	},/*	29	30	29	30	30	29	30	29	30	30	29	30	0	355
		1935	*/{	0,		2,		4,		11104	},/*	29	29	30	29	30	29	30	30	29	30	30	29	0	354
		1936	*/{	3,		1,		24,		37744	},/*	30	29	29	30	29	29	30	30	29	30	30	30	29	384
		1937	*/{	0,		2,		11,		37600	},/*	30	29	29	30	29	29	30	29	30	30	30	29	0	354 
		1938	*/{	7,		1,		31,		51560	},/*	30	30	29	29	30	29	29	30	29	30	30	29	30	384
		1939	*/{	0,		2,		19,		51536	},/*	30	30	29	29	30	29	29	30	29	30	29	30	0	354 
		1940	*/{	0,		2,		8,		54432	},/*	30	30	29	30	29	30	29	29	30	29	30	29	0	354 
		1941	*/{	6,		1,		27,		55888	},/*	30	30	29	30	30	29	30	29	29	30	29	30	29	384
		1942	*/{	0,		2,		15,		46416	},/*	30	29	30	30	29	30	29	30	29	30	29	30	0	355 
		1943	*/{	0,		2,		5,		22176	},/*	29	30	29	30	29	30	30	29	30	29	30	29	0	354
		1944	*/{	4,		1,		25,		43736	},/*	30	29	30	29	30	29	30	29	30	30	29	30	30	385
		1945	*/{	0,		2,		13,		9680	},/*	29	29	30	29	29	30	29	30	30	30	29	30	0	354
		1946	*/{	0,		2,		2,		37584	},/*	30	29	29	30	29	29	30	29	30	30	29	30	0	354 
		1947	*/{	2,		1,		22,		51544	},/*	30	30	29	29	30	29	29	30	29	30	29	30	30	384
		1948	*/{	0,		2,		10,		43344	},/*	30	29	30	29	30	29	29	30	29	30	29	30	0	354 
		1949	*/{	7,		1,		29,		46248	},/*	30	29	30	30	29	30	29	29	30	29	30	29	30	384 
		1950	*/{	0,		2,		17,		27808	},/*	29	30	30	29	30	30	29	29	30	29	30	29	0	354
		1951	*/{	0,		2,		6,		46416	},/*	30	29	30	30	29	30	29	30	29	30	29	30	0	355 
		1952	*/{	5,		1,		27,		21928	},/*	29	30	29	30	29	30	29	30	30	29	30	29	30	384
		1953	*/{	0,		2,		14,		19872	},/*	29	30	29	29	30	30	29	30	30	29	30	29	0	354
		1954	*/{	0,		2,		3,		42416	},/*	30	29	30	29	29	30	29	30	30	29	30	30	0	355
		1955	*/{	3,		1,		24,		21176	},/*	29	30	29	30	29	29	30	29	30	29	30	30	30	384 
		1956	*/{	0,		2,		12,		21168	},/*	29	30	29	30	29	29	30	29	30	29	30	30	0	354
		1957	*/{	8,		1,		31,		43344	},/*	30	29	30	29	30	29	29	30	29	30	29	30	29	383 
		1958	*/{	0,		2,		18,		59728	},/*	30	30	30	29	30	29	29	30	29	30	29	30	0	355 
		1959	*/{	0,		2,		8,		27296	},/*	29	30	30	29	30	29	30	29	30	29	30	29	0	354
		1960	*/{	6,		1,		28,		44368	},/*	30	29	30	29	30	30	29	30	29	30	29	30	29	384 
		1961	*/{	0,		2,		15,		43856	},/*	30	29	30	29	30	29	30	30	29	30	29	30	0	355
		1962	*/{	0,		2,		5,		19296	},/*	29	30	29	29	30	29	30	30	29	30	30	29	0	354
		1963	*/{	4,		1,		25,		42352	},/*	30	29	30	29	29	30	29	30	29	30	30	30	29	384
		1964	*/{	0,		2,		13,		42352	},/*	30	29	30	29	29	30	29	30	29	30	30	30	0	355 
		1965	*/{	0,		2,		2,		21088	},/*	29	30	29	30	29	29	30	29	29	30	30	29	0	353
		1966	*/{	3,		1,		21,		59696	},/*	30	30	30	29	30	29	29	30	29	29	30	30	29	384 
		1967	*/{	0,		2,		9,		55632	},/*	30	30	29	30	30	29	29	30	29	30	29	30	0	355 
		1968	*/{	7,		1,		30,		23208	},/*	29	30	29	30	30	29	30	29	30	29	30	29	30	384
		1969	*/{	0,		2,		17,		22176	},/*	29	30	29	30	29	30	30	29	30	29	30	29	0	354 
		1970	*/{	0,		2,		6,		38608	},/*	30	29	29	30	29	30	30	29	30	30	29	30	0	355
		1971	*/{	5,		1,		27,		19176	},/*	29	30	29	29	30	29	30	29	30	30	30	29	30	384
		1972	*/{	0,		2,		15,		19152	},/*	29	30	29	29	30	29	30	29	30	30	29	30	0	354
		1973	*/{	0,		2,		3,		42192	},/*	30	29	30	29	29	30	29	29	30	30	29	30	0	354 
		1974	*/{	4,		1,		23,		53864	},/*	30	30	29	30	29	29	30	29	29	30	30	29	30	384
		1975	*/{	0,		2,		11,		53840	},/*	30	30	29	30	29	29	30	29	29	30	29	30	0	354 
		1976	*/{	8,		1,		31,		54568	},/*	30	30	29	30	29	30	29	30	29	29	30	29	30	384 
		1977	*/{	0,		2,		18,		46400	},/*	30	29	30	30	29	30	29	30	29	30	29	29	0	354
		1978	*/{	0,		2,		7,		46752	},/*	30	29	30	30	29	30	30	29	30	29	30	29	0	355 
		1979	*/{	6,		1,		28,		38608	},/*	30	29	29	30	29	30	30	29	30	30	29	30	29	384
		1980	*/{	0,		2,		16,		38320	},/*	30	29	29	30	29	30	29	30	30	29	30	30	0	355
		1981	*/{	0,		2,		5,		18864	},/*	29	30	29	29	30	29	29	30	30	29	30	30	0	354
		1982	*/{	4,		1,		25,		42168	},/*	30	29	30	29	29	30	29	29	30	29	30	30	30	384 
		1983	*/{	0,		2,		13,		42160	},/*	30	29	30	29	29	30	29	29	30	29	30	30	0	354
		1984	*/{	10,		2,		2,		45656	},/*	30	29	30	30	29	29	30	29	29	30	29	30	30	384 
		1985	*/{	0,		2,		20,		27216	},/*	29	30	30	29	30	29	30	29	29	30	29	30	0	354 
		1986	*/{	0,		2,		9,		27968	},/*	29	30	30	29	30	30	29	30	29	30	29	29	0	354
		1987	*/{	6,		1,		29,		44448	},/*	30	29	30	29	30	30	29	30	30	29	30	29	29	384 
		1988	*/{	0,		2,		17,		43872	},/*	30	29	30	29	30	29	30	30	29	30	30	29	0	355
		1989	*/{	0,		2,		6,		38256	},/*	30	29	29	30	29	30	29	30	29	30	30	30	0	355
		1990	*/{	5,		1,		27,		18808	},/*	29	30	29	29	30	29	29	30	29	30	30	30	30	384
		1991	*/{	0,		2,		15,		18800	},/*	29	30	29	29	30	29	29	30	29	30	30	30	0	354 
		1992	*/{	0,		2,		4,		25776	},/*	29	30	30	29	29	30	29	29	30	29	30	30	0	354
		1993	*/{	3,		1,		23,		27216	},/*	29	30	30	29	30	29	30	29	29	30	29	30	29	383 
		1994	*/{	0,		2,		10,		59984	},/*	30	30	30	29	30	29	30	29	29	30	29	30	0	355 
		1995	*/{	8,		1,		31,		27432	},/*	29	30	30	29	30	29	30	30	29	29	30	29	30	384
		1996	*/{	0,		2,		19,		23232	},/*	29	30	29	30	30	29	30	29	30	30	29	29	0	354 
		1997	*/{	0,		2,		7,		43872	},/*	30	29	30	29	30	29	30	30	29	30	30	29	0	355
		1998	*/{	5,		1,		28,		37736	},/*	30	29	29	30	29	29	30	30	29	30	30	29	30	384
		1999	*/{	0,		2,		16,		37600	},/*	30	29	29	30	29	29	30	29	30	30	30	29	0	354
		2000	*/{	0,		2,		5,		51552	},/*	30	30	29	29	30	29	29	30	29	30	30	29	0	354 
		2001	*/{	4,		1,		24,		54440	},/*	30	30	29	30	29	30	29	29	30	29	30	29	30	384
		2002	*/{	0,		2,		12,		54432	},/*	30	30	29	30	29	30	29	29	30	29	30	29	0	354 
		2003	*/{	0,		2,		1,		55888	},/*	30	30	29	30	30	29	30	29	29	30	29	30	0	355 
		2004	*/{	2,		1,		22,		23208	},/*	29	30	29	30	30	29	30	29	30	29	30	29	30	384
		2005	*/{	0,		2,		9,		22176	},/*	29	30	29	30	29	30	30	29	30	29	30	29	0	354 
		2006	*/{	7,		1,		29,		43736	},/*	30	29	30	29	30	29	30	29	30	30	29	30	30	385
		2007	*/{	0,		2,		18,		9680	},/*	29	29	30	29	29	30	29	30	30	30	29	30	0	354
		2008	*/{	0,		2,		7,		37584	},/*	30	29	29	30	29	29	30	29	30	30	29	30	0	354
		2009	*/{	5,		1,		26,		51544	},/*	30	30	29	29	30	29	29	30	29	30	29	30	30	384 
		2010	*/{	0,		2,		14,		43344	},/*	30	29	30	29	30	29	29	30	29	30	29	30	0	354
		2011	*/{	0,		2,		3,		46240	},/*	30	29	30	30	29	30	29	29	30	29	30	29	0	354 
		2012	*/{	4,		1,		23,		46416	},/*	30	29	30	30	29	30	29	30	29	30	29	30	29	384 
		2013	*/{	0,		2,		10,		44368	},/*	30	29	30	29	30	30	29	30	29	30	29	30	0	355
		2014	*/{	9,		1,		31,		21928	},/*	29	30	29	30	29	30	29	30	30	29	30	29	30	384 
		2015	*/{	0,		2,		19,		19360	},/*	29	30	29	29	30	29	30	30	30	29	30	29	0	354
		2016	*/{	0,		2,		8,		42416	},/*	30	29	30	29	29	30	29	30	30	29	30	30	0	355
		2017	*/{	6,		1,		28,		21176	},/*	29	30	29	30	29	29	30	29	30	29	30	30	30	384
		2018	*/{	0,		2,		16,		21168	},/*	29	30	29	30	29	29	30	29	30	29	30	30	0	354 
		2019	*/{	0,		2,		5,		43312	},/*	30	29	30	29	30	29	29	30	29	29	30	30	0	354
		2020	*/{	4,		1,		25,		29864	},/*	29	30	30	30	29	30	29	29	30	29	30	29	30	384 
		2021	*/{	0,		2,		12,		27296	},/*	29	30	30	29	30	29	30	29	30	29	30	29	0	354 
		2022	*/{	0,		2,		1,		44368	},/*	30	29	30	29	30	30	29	30	29	30	29	30	0	355
		2023	*/{	2,		1,		22,		19880	},/*	29	30	29	29	30	30	29	30	30	29	30	29	30	384 
		2024	*/{	0,		2,		10,		19296	},/*	29	30	29	29	30	29	30	30	29	30	30	29	0	354
		2025	*/{	6,		1,		29,		42352	},/*	30	29	30	29	29	30	29	30	29	30	30	30	29	384
		2026	*/{	0,		2,		17,		42208	},/*	30	29	30	29	29	30	29	29	30	30	30	29	0	354
		2027	*/{	0,		2,		6,		53856	},/*	30	30	29	30	29	29	30	29	29	30	30	29	0	354 
		2028	*/{	5,		1,		26,		59696	},/*	30	30	30	29	30	29	29	30	29	29	30	30	29	384
		2029	*/{	0,		2,		13,		54576	},/*	30	30	29	30	29	30	29	30	29	29	30	30	0	355 
		2030	*/{	0,		2,		3,		23200	},/*	29	30	29	30	30	29	30	29	30	29	30	29	0	354 
		2031	*/{	3,		1,		23,		27472	},/*	29	30	30	29	30	29	30	30	29	30	29	30	29	384
		2032	*/{	0,		2,		11,		38608	},/*	30	29	29	30	29	30	30	29	30	30	29	30	0	355 
		2033	*/{	11,		1,		31,		19176	},/*	29	30	29	29	30	29	30	29	30	30	30	29	30	384
		2034	*/{	0,		2,		19,		19152	},/*	29	30	29	29	30	29	30	29	30	30	29	30	0	354
		2035	*/{	0,		2,		8,		42192	},/*	30	29	30	29	29	30	29	29	30	30	29	30	0	354
		2036	*/{	6,		1,		28,		53848	},/*	30	30	29	30	29	29	30	29	29	30	29	30	30	384 
		2037	*/{	0,		2,		15,		53840	},/*	30	30	29	30	29	29	30	29	29	30	29	30	0	354
		2038	*/{	0,		2,		4,		54560	},/*	30	30	29	30	29	30	29	30	29	29	30	29	0	354 
		2039	*/{	5,		1,		24,		55968	},/*	30	30	29	30	30	29	30	29	30	29	30	29	29	384 
		2040	*/{	0,		2,		12,		46496	},/*	30	29	30	30	29	30	29	30	30	29	30	29	0	355
		2041	*/{	0,		2,		1,		22224	},/*	29	30	29	30	29	30	30	29	30	30	29	30	0	355 
		2042	*/{	2,		1,		22,		19160	},/*	29	30	29	29	30	29	30	29	30	30	29	30	30	384
		2043	*/{	0,		2,		10,		18864	},/*	29	30	29	29	30	29	29	30	30	29	30	30	0	354
		2044	*/{	7,		1,		30,		42168	},/*	30	29	30	29	29	30	29	29	30	29	30	30	30	384
		2045	*/{	0,		2,		17,		42160	},/*	30	29	30	29	29	30	29	29	30	29	30	30	0	354 
		2046	*/{	0,		2,		6,		43600	},/*	30	29	30	29	30	29	30	29	29	30	29	30	0	354
		2047	*/{	5,		1,		26,		46376	},/*	30	29	30	30	29	30	29	30	29	29	30	29	30	384 
		2048	*/{	0,		2,		14,		27936	},/*	29	30	30	29	30	30	29	30	29	29	30	29	0	354 
		2049	*/{	0,		2,		2,		44448	},/*	30	29	30	29	30	30	29	30	30	29	30	29	0	355
		2050	*/{	3,		1,		23,		21936	},/*	29	30	29	30	29	30	29	30	30	29	30	30	29	384 
		2051	*/{	0,		2,		11,		37744	},/*	30	29	29	30	29	29	30	30	29	30	30	30	0	355
		2052	*/{	8,		2,		1,		18808	},/*	29	30	29	29	30	29	29	30	29	30	30	30	30	384
		2053	*/{	0,		2,		19,		18800	},/*	29	30	29	29	30	29	29	30	29	30	30	30	0	354
		2054	*/{	0,		2,		8,		25776	},/*	29	30	30	29	29	30	29	29	30	29	30	30	0	354 
		2055	*/{	6,		1,		28,		27216	},/*	29	30	30	29	30	29	30	29	29	30	29	30	29	383
		2056	*/{	0,		2,		15,		59984	},/*	30	30	30	29	30	29	30	29	29	30	29	30	0	355 
		2057	*/{	0,		2,		4,		27424	},/*	29	30	30	29	30	29	30	30	29	29	30	29	0	354 
		2058	*/{	4,		1,		24,		43872	},/*	30	29	30	29	30	29	30	30	29	30	30	29	29	384
		2059	*/{	0,		2,		12,		43744	},/*	30	29	30	29	30	29	30	29	30	30	30	29	0	355 
		2060	*/{	0,		2,		2,		37600	},/*	30	29	29	30	29	29	30	29	30	30	30	29	0	354
		2061	*/{	3,		1,		21,		51568	},/*	30	30	29	29	30	29	29	30	29	30	30	30	29	384
		2062	*/{	0,		2,		9,		51552	},/*	30	30	29	29	30	29	29	30	29	30	30	29	0	354
		2063	*/{	7,		1,		29,		54440	},/*	30	30	29	30	29	30	29	29	30	29	30	29	30	384 
		2064	*/{	0,		2,		17,		54432	},/*	30	30	29	30	29	30	29	29	30	29	30	29	0	354
		2065	*/{	0,		2,		5,		55888	},/*	30	30	29	30	30	29	30	29	29	30	29	30	0	355 
		2066	*/{	5,		1,		26,		23208	},/*	29	30	29	30	30	29	30	29	30	29	30	29	30	384 
		2067	*/{	0,		2,		14,		22176	},/*	29	30	29	30	29	30	30	29	30	29	30	29	0	354
		2068	*/{	0,		2,		3,		42704	},/*	30	29	30	29	29	30	30	29	30	30	29	30	0	355 
		2069	*/{	4,		1,		23,		21224	},/*	29	30	29	30	29	29	30	29	30	30	30	29	30	384
		2070	*/{	0,		2,		11,		21200	},/*	29	30	29	30	29	29	30	29	30	30	29	30	0	354
		2071	*/{	8,		1,		31,		43352	},/*	30	29	30	29	30	29	29	30	29	30	29	30	30	384
		2072	*/{	0,		2,		19,		43344	},/*	30	29	30	29	30	29	29	30	29	30	29	30	0	354 
		2073	*/{	0,		2,		7,		46240	},/*	30	29	30	30	29	30	29	29	30	29	30	29	0	354
		2074	*/{	6,		1,		27,		46416	},/*	30	29	30	30	29	30	29	30	29	30	29	30	29	384 
		2075	*/{	0,		2,		15,		44368	},/*	30	29	30	29	30	30	29	30	29	30	29	30	0	355 
		2076	*/{	0,		2,		5,		21920	},/*	29	30	29	30	29	30	29	30	30	29	30	29	0	354
		2077	*/{	4,		1,		24,		42448	},/*	30	29	30	29	29	30	29	30	30	30	29	30	29	384 
		2078	*/{	0,		2,		12,		42416	},/*	30	29	30	29	29	30	29	30	30	29	30	30	0	355
		2079	*/{	0,		2,		2,		21168	},/*	29	30	29	30	29	29	30	29	30	29	30	30	0	354
		2080	*/{	3,		1,		22,		43320	},/*	30	29	30	29	30	29	29	30	29	29	30	30	30	384
		2081	*/{	0,		2,		9,		26928	},/*	29	30	30	29	30	29	29	30	29	29	30	30	0	354 
		2082	*/{	7,		1,		29,		29336	},/*	29	30	30	30	29	29	30	29	30	29	29	30	30	384
		2083	*/{	0,		2,		17,		27296	},/*	29	30	30	29	30	29	30	29	30	29	30	29	0	354 
		2084	*/{	0,		2,		6,		44368	},/*	30	29	30	29	30	30	29	30	29	30	29	30	0	355 
		2085	*/{	5,		1,		26,		19880	},/*	29	30	29	29	30	30	29	30	30	29	30	29	30	384
		2086	*/{	0,		2,		14,		19296	},/*	29	30	29	29	30	29	30	30	29	30	30	29	0	354 
		2087	*/{	0,		2,		3,		42352	},/*	30	29	30	29	29	30	29	30	29	30	30	30	0	355
		2088	*/{	4,		1,		24,		21104	},/*	29	30	29	30	29	29	30	29	29	30	30	30	29	383
		2089	*/{	0,		2,		10,		53856	},/*	30	30	29	30	29	29	30	29	29	30	30	29	0	354
		2090	*/{	8,		1,		30,		59696	},/*	30	30	30	29	30	29	29	30	29	29	30	30	29	384 
		2091	*/{	0,		2,		18,		54560	},/*	30	30	29	30	29	30	29	30	29	29	30	29	0	354
		2092	*/{	0,		2,		7,		55968	},/*	30	30	29	30	30	29	30	29	30	29	30	29	0	355 
		2093	*/{	6,		1,		27,		27472	},/*	29	30	30	29	30	29	30	30	29	30	29	30	29	384 
		2094	*/{	0,		2,		15,		22224	},/*	29	30	29	30	29	30	30	29	30	30	29	30	0	355
		2095	*/{	0,		2,		5,		19168	},/*	29	30	29	29	30	29	30	29	30	30	30	29	0	354 
		2096	*/{	4,		1,		25,		42216	},/*	30	29	30	29	29	30	29	29	30	30	30	29	30	384
		2097	*/{	0,		2,		12,		42192	},/*	30	29	30	29	29	30	29	29	30	30	29	30	0	354
		2098	*/{	0,		2,		1,		53584	},/*	30	30	29	30	29	29	29	30	29	30	29	30	0	354
		2099	*/{	2,		1,		21,		55592	},/*	30	30	29	30	30	29	29	30	29	29	30	29	30	384 
		2100	*/{	0,		2,		9,		54560	},/*	30	30	29	30	29	30	29	30	29	29	30	29	0	354
				*/
	}; 
    
    public static int getMinCalendarYear(){
    	return MIN_LUNISOLAR_YEAR;
    }
    
    public static int getMaxCalendarYear(){
    	return MAX_LUNISOLAR_YEAR;
    }
	
    public static Calendar getMinSupportedDateTime(){
    	return MIN_DATE;
    }
    
    public static Calendar getMaxSupportedDateTime(){
    	return MAX_DATE;
    }
    
    private static int getYearInfo(int lunarYear, int index){
    	checkYearRange(lunarYear);
    	return YEAR_INFO[lunarYear - MIN_LUNISOLAR_YEAR][index];
    }
    
	private static int checkYearRange(int year){

    	if((year < MIN_LUNISOLAR_YEAR) || (year > MAX_LUNISOLAR_YEAR)){
    		throw new IllegalArgumentException("ArgumentOutOfRange. year must in ["+MIN_LUNISOLAR_YEAR+","+MAX_LUNISOLAR_YEAR+"].");
    	}
    	return year;
	}
	
	private static int checkYearMonthRange(int year, int month){
		year = checkYearRange(year);
		
		if(month < 1 || month > 13){
			throw new IllegalArgumentException("Argument 'month' out of range");
		}
		
		if(month == 13 && !isLeapYear(year)){
			throw new IllegalArgumentException("Argument 'month' out of range");
		}
		
		return year;
	}

	private static void checkTimeRange(long timeInMillis){
		if(timeInMillis < MIN_DATE.getTimeInMillis() || timeInMillis > MAX_DATE.getTimeInMillis()){
			throw new IllegalArgumentException("ArgumentOutOfRange. time must in ["+MIN_DATE+","+MAX_DATE+"].");
		}
	}
	
	/**
	 * GregorianToLunar calculates lunar calendar info for the given gregorian year, month, date.
	 * The input date should be validated before calling this method.
	 * 公历转农历
	 */
	static YearMonthDay gregorianToLunar(int nSYear, int nSMonth, int nSDate){
		
		YearMonthDay lunarDate = new YearMonthDay();
		
        int nSolarDay;        // day # in solar year 
        int nLunarDay;        // day # in lunar year
        boolean fLeap;                    // is it a solar leap year? 
        int LDpM;        // lunar days/month bitfield
        int mask;        // mask for extracting bits
        int nDays;        // # days this lunar month
        int nJan1Month, nJan1Date; 

        // calc the solar day of year 
        fLeap = isGregorianLeapYear(nSYear); 
        nSolarDay = (fLeap) ? DAYS_TO_MONTH_366[nSMonth-1] : DAYS_TO_MONTH_365[nSMonth-1];
        nSolarDay += nSDate;
        
        // init lunar year info
        nLunarDay = nSolarDay;
        lunarDate.year = nSYear; 
        if (lunarDate.year == (MAX_LUNISOLAR_YEAR + 1)) {
        	lunarDate.year--; 
            nLunarDay += (isGregorianLeapYear(lunarDate.year) ? 366 : 365); 
            nJan1Month = getYearInfo(lunarDate.year, INDEX_JAN1_MONTH);
            nJan1Date = getYearInfo(lunarDate.year, INDEX_JAN1_DATE); 
        }else{

            nJan1Month = getYearInfo(lunarDate.year, INDEX_JAN1_MONTH);
            nJan1Date = getYearInfo(lunarDate.year, INDEX_JAN1_DATE); 
            // check if this solar date is actually part of the previous 
            // lunar year 
            if ((nSMonth < nJan1Month) ||
                (nSMonth == nJan1Month && nSDate < nJan1Date)) { 
                // the corresponding lunar day is actually part of the previous
                // lunar year
            	lunarDate.year--;
 
                // add a solar year to the lunar day #
                nLunarDay += (isGregorianLeapYear(lunarDate.year) ? 366 : 365); 
 
                // update the new start of year
                nJan1Month = getYearInfo(lunarDate.year, INDEX_JAN1_MONTH);
                nJan1Date = getYearInfo(lunarDate.year, INDEX_JAN1_DATE); 
            }
        }
        
        // convert solar day into lunar day.
        // subtract off the beginning part of the solar year which is not 
        // part of the lunar year.  since this part is always in Jan or Feb, 
        // we don't need to handle Leap Year (LY only affects [....]
        // and later). 
        nLunarDay -= DAYS_TO_MONTH_365[nJan1Month-1];
        nLunarDay -= (nJan1Date - 1);

        // convert the lunar day into a lunar month/date 
        mask = 0x8000;
        LDpM = getYearInfo(lunarDate.year, INDEX_NUM_OF_DAYS_PER_MONTH); 
        nDays = ((LDpM & mask) != 0) ? 30 : 29; 
        lunarDate.month = 1;
        while (nLunarDay > nDays) { 
            nLunarDay -= nDays;
            lunarDate.month++;
            mask >>= 1;
            nDays = ((LDpM & mask) != 0) ? 30 : 29; 
        }
        lunarDate.day = nLunarDay;
        return lunarDate;
	}
	
	/**
	 * Convert from Lunar to Gregorian
	 * Highly inefficient, but it works based on the forward conversion
	 * 农历转公历
	 * @param nLYear
	 * @param nLMonth
	 * @param nLDate
	 * @param nSolarYear
	 * @param nSolarMonth
	 * @param nSolarDay
	 * @return
	 */
	static YearMonthDay lunarToGregorian(int nLYear, int nLMonth, int nLDate){
		int numLunarDays;
		 
        if (nLDate < 1 || nLDate > 30) 
            return null;

        YearMonthDay solarDate = new YearMonthDay();
        
        numLunarDays = nLDate-1;
        //Add previous months days to form the total num of days from the first of the month.
        for (int i = 1; i < nLMonth; i++) { 
            numLunarDays += getDaysInMonth(nLYear, i);
        } 
        
        //Get Gregorian First of year
        int nJan1Month = getYearInfo(nLYear, INDEX_JAN1_MONTH); 
        int nJan1Date = getYearInfo(nLYear, INDEX_JAN1_DATE);

        // calc the solar day of year of 1 Lunar day
        boolean fLeap = isGregorianLeapYear(nLYear); 
        int[] days = fLeap? DAYS_TO_MONTH_366: DAYS_TO_MONTH_365;
        
        solarDate.day  = nJan1Date; 

        if (nJan1Month > 1) 
        	solarDate.day += days [nJan1Month-1];
        // Add the actual lunar day to get the solar day we want
        solarDate.day = solarDate.day + numLunarDays;// - 1; 

        if ( solarDate.day > (fLeap? 366: 365)) { 
        	solarDate.year = nLYear + 1; 
            solarDate.day -= (fLeap? 366: 365);
        } else { 
        	solarDate.year = nLYear;
        }

        for (solarDate.month = 1; solarDate.month < 12; solarDate.month++) { 
            if (days[solarDate.month] >= solarDate.day)
                break; 
        } 

        solarDate.day -= days[solarDate.month-1]; 
        return solarDate;
	}

	/**
	 * 将农历日期转换成公历
	 * @param calendar
	 * @param year
	 * @param month
	 * @param day
	 */
	static void lunarToGregorianCalendar(GregorianCalendar calendar, int year, int month, int day){
		YearMonthDay solarDate = lunarToGregorian(year, month, day);
		calendar.set(Calendar.YEAR, solarDate.year);
		calendar.set(Calendar.MONTH, solarDate.month-1);
		calendar.set(Calendar.DATE, solarDate.day);
	}
	
	/**
	 * 
	 * @param calendar
	 * @param year
	 * @param month
	 * @param day
	 */
	static YearMonthDay gregorianCalendarToLunar(GregorianCalendar calendar) {
        int gy=0; int gm=0; int gd=0;
        gy = calendar.get(Calendar.YEAR);
        gm = calendar.get(Calendar.MONTH) + 1;
        gd = calendar.get(Calendar.DAY_OF_MONTH);

        return gregorianToLunar(gy, gm, gd); 
    }
	
	/**
	 * Return the year number in the 60-year cycle.
	 * 返回在天干地支纪年周期中的位置.
	 * 如"甲子年"返回0.
	 * @param date
	 * @return 
	 */
	public static int getSexagenaryYear(GregorianCalendar date){
		checkTimeRange(date.getTimeInMillis());
		YearMonthDay ymd = gregorianCalendarToLunar(date);
		return (ymd.year - 4) % 60;
	}
	
	/**
	 * Returns the number of months in the specified year. 
	 * 返回指定年有几个月. 
	 * @param year
	 * @return
	 */
	public static int getMonthsInYear(int year){
		return isLeapYear(year)?13:12;
	}
	/**
	 * Returns the number of days in the month given by the year and month arguments.
	 * 返回指定年月的天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysInMonth(int year, int month){
		year = checkYearMonthRange(year, month);
		
        int mask = 0x8000 >> (month-1);		// mask for extracting bits
        
        if((getYearInfo(year, INDEX_NUM_OF_DAYS_PER_MONTH) & mask) == 0){
        	return 29;	
        }else{
        	return 30;
        }
	}
	
	/**
	 * Return leapMonth of a given year. 
	 * return 0 if there's no leap month this year. 
	 * 返回指定农历年的闰月是第几个月，若概念无闰月则返回0. 
	 * 如闰四月返回五. 
	 * @param year
	 * @return leap month
	 */
	public static int getLeapMonth(int year) {
		int month = getYearInfo(year, INDEX_LEAP_MONTH);
		if(month >0){
			return(month + 1);
		}
		return 0;
	}
	
	/**
	 * Checks whether a given year is a leap year. This method returns true if 
	 * year is a leap year, or false if not.
	 * 返回某一年是否是闰年
	 * @param year
	 * @return is leap year
	 */
	public static boolean isLeapYear(int year){
		return getLeapMonth(year) > 0;
	}

	/**
	 * Checks whether a given year is a leap year in gregorian calendar. 
	 * 返回是否公历闰年
	 * @param year
	 * @return
	 */
	private static boolean isGregorianLeapYear(int year) {
		return ((((year)%4)!=0)?false:((((year)%100)!=0)?true:((((year)%400)!=0)?false:true)));
	}
	
	/**
	 * Checks whether a given month is a leap month. This method returns true if 
	 * month is a leap month, or false if not.
	 * 返回某个月是否是闰月. 
	 * @param year
	 * @param month
	 * @return
	 */
	public static boolean isLeapMonth(int year, int month){
		year = checkYearMonthRange(year, month);
		return month == getLeapMonth(year);
	}
}
