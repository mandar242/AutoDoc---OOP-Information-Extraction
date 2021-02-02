import java.util.ArrayList;
import java.util.List;

public class PrintTest {
	class Test {
		int a; String name; List<String> nickName;
		Test() {a=5; name="Mayank"; nickName=new ArrayList<>();nickName.add("1");nickName.add("3");nickName.add("2");}
		@Override
		public String toString() {
			return a+" "+name+ " " + nickName;
		}
	}
	
	public static void main(String[] args) {
		PrintTest pt = new PrintTest();
		Test t = pt.new Test();
		
		System.out.println(t);
	}
}
