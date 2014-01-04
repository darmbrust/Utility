package utility.sort;

/**
 *    Copyright 2014 Daniel Armbrust
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
import java.util.Arrays;

/**
 * Testcases for the AlphanumComparator
 * 
 * @author <A HREF="mailto:daniel.armbrust@gmail.com">Dan Armbrust</A>
 * 
 *         See http://armbrust.dyndns.org/programs/index.php?page=3
 */

public class AlphanumComparatorTest
{
	public void test1()
	{
		String[] in = {
				"site1-26-0",      
				"site3-26-120", 
				"site1-26-180",    
				"site1-26-270",    
				"site1-26-90",     
				"site2-26-0",      
				"site2-26-180",    
				"site3-26-270",
				"site3-26-60"
				};
		String[] out = {
				"site1-26-0",      
				"site1-26-90",
				"site1-26-180",    
				"site1-26-270",    
				"site2-26-0",      
				"site2-26-180",    
				"site3-26-60",
				"site3-26-120",   
				"site3-26-270",
				};
		
		test(in, out);
	}
	
	public void test2()
	{
		String[] in = {
				"192.168.26.4",    
				"192.168.26.3",    
				"192.168.26.203",  
				"192.168.26.202",
				"192.168.26.201",  
				"192.168.26.2",
				"192.168.26.102",  
				"192.168.26.101",  
				"192.168.26.1"
				};
		String[] out = {
				"192.168.26.1",
				"192.168.26.2",
				"192.168.26.3",
				"192.168.26.4",    
				"192.168.26.101",
				"192.168.26.102", 
				"192.168.26.201",
				"192.168.26.202",
				"192.168.26.203",  
				};
		
		test(in, out);
	}
	
	public void test3()
	{
		String[] in = {
				"ab1",    
				"ab20",
				"ab2",    
				"ab10",  
				"ab0001",
				"ab50",  
				"ab0005",
				"ab00050"
				};
		String[] out = {
				"ab1",    
				"ab0001",
				"ab2",  
				"ab0005",
				"ab10",
				"ab20",
				"ab50",  
				"ab00050" 
				};
		
		test(in, out);
	}
	
	public void test4()
	{
		String[] in = {
				"00000000000000000000000000000000000000005",    
				"50000000000000000000000000000000000000000",
				"123",    
				"0005",  
				"10000000000000000000000000000000000000005",
				"10000000000000000000000000000000000000006",
				"100000000000000000000000000000000000000050",  
				"5",
				""
				};
		String[] out = {
				"",
				"5",
				"0005",
				"00000000000000000000000000000000000000005",
				"123",
				"10000000000000000000000000000000000000005",
				"10000000000000000000000000000000000000006",
				"100000000000000000000000000000000000000050",
				"50000000000000000000000000000000000000000",
				};
		
		test(in, out);
	}
	public void test5()
	{
		String[] in = {
				"z1.doc",
				"z10.doc",
				"z100.doc",
				"z101.doc",
				"z102.doc",
				"z11.doc",
				"z12.doc",
				"z13.doc",
				"z14.doc",
				"z15.doc",
				"z16.doc",
				"z17.doc",
				"z18.doc",
				"z19.doc",
				"z2.doc",
				"z20.doc",
				"z3.doc",
				"z4.doc",
				"z5.doc",
				"z6.doc",
				"z7.doc",
				"z8.doc",
				"z9.doc"
				};		
		String[] out = {
				"z1.doc",
				"z2.doc",
				"z3.doc",
				"z4.doc",
				"z5.doc",
				"z6.doc",
				"z7.doc",
				"z8.doc",
				"z9.doc",
				"z10.doc",
				"z11.doc",
				"z12.doc",
				"z13.doc",
				"z14.doc",
				"z15.doc",
				"z16.doc",
				"z17.doc",
				"z18.doc",
				"z19.doc",
				"z20.doc",
				"z100.doc",
				"z101.doc",
				"z102.doc"
				};
		
		test(in, out);
	}
	
	public void test6()
	{
		String[] in = {
				"1000X Radonius Maximus",
				"10X Radonius",
				"200X Radonius",
				"20X Radonius",
				"20X Radonius Prime",
				"30X Radonius",
				"40X Radonius",
				"Allegia 50 Clasteron",
				"Allegia 500 Clasteron",
				"Allegia 50B Clasteron",
				"Allegia 51 Clasteron",
				"Allegia 6R Clasteron",
				"Alpha 100",
				"Alpha 2",
				"Alpha 200",
				"Alpha 2A",
				"Alpha 2A-8000",
				"Alpha 2A-900",
				"Callisto Morphamax",
				"Callisto Morphamax 500",
				"Callisto Morphamax 5000",
				"Callisto Morphamax 600",
				"Callisto Morphamax 6000 SE",
				"Callisto Morphamax 6000 SE2",
				"Callisto Morphamax 700",
				"Callisto Morphamax 7000",
				"Xiph Xlater 10000",
				"Xiph Xlater 2000",
				"Xiph Xlater 300",
				"Xiph Xlater 40",
				"Xiph Xlater 5",
				"Xiph Xlater 50",
				"Xiph Xlater 500",
				"Xiph Xlater 5000",
				"Xiph Xlater 58"
				};		
		String[] out = {
				"10X Radonius",
				"20X Radonius",
				"20X Radonius Prime",
				"30X Radonius",
				"40X Radonius",
				"200X Radonius",
				"1000X Radonius Maximus",
				"Allegia 6R Clasteron",
				"Allegia 50 Clasteron",
				"Allegia 50B Clasteron",
				"Allegia 51 Clasteron",
				"Allegia 500 Clasteron",
				"Alpha 2",
				"Alpha 2A",
				"Alpha 2A-900",
				"Alpha 2A-8000",
				"Alpha 100",
				"Alpha 200",
				"Callisto Morphamax",
				"Callisto Morphamax 500",
				"Callisto Morphamax 600",
				"Callisto Morphamax 700",
				"Callisto Morphamax 5000",
				"Callisto Morphamax 6000 SE",
				"Callisto Morphamax 6000 SE2",
				"Callisto Morphamax 7000",
				"Xiph Xlater 5",
				"Xiph Xlater 40",
				"Xiph Xlater 50",
				"Xiph Xlater 58",
				"Xiph Xlater 300",
				"Xiph Xlater 500",
				"Xiph Xlater 2000",
				"Xiph Xlater 5000",
				"Xiph Xlater 10000"
				};
		
		test(in, out);
	}
	
	public void test7()
	{
		String[] in = {
				"a 1",    
				"a 2",
				"a 20",
				"a 10",    
				"beta",  
				"alpha",
				"Alpha"
				};
		String[] out = {
				"a 1",
				"a 2",
				"a 10",
				"a 20",
				"alpha",
				"Alpha",
				"beta"
				};
		
		test(in, out);
	}
	
	private void test(String[] in, String[] out)
	{
		if (Arrays.equals(in, out))
		{
			throw new RuntimeException("Bad test, precheck fails");
		}
		Arrays.sort(in, new AlphanumComparator(true));
		if (!Arrays.equals(in, out))
		{
			throw new RuntimeException("Output sort order wrong - " + failUtil(out,in));
		}
	}

	private String failUtil(String[] expected, String[] actual)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Expected: ");
		for (String s : expected)
		{
			sb.append(s);
			sb.append(",");
		}
		sb.setLength(sb.length() - 1);
		
		sb.append(" Actual: ");
		for (String s : actual)
		{
			sb.append(s);
			sb.append(",");
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}
	
	public static void main(String[] args)
	{
		AlphanumComparatorTest act = new AlphanumComparatorTest();
		act.test1();
		act.test2();
		act.test3();
		act.test4();
		act.test5();
		act.test6();
		act.test7();
		System.out.println("All tests pass.");
	}
}