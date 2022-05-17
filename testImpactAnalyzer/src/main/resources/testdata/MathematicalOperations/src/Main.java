import operations.Add;
import operations.Subtract;
import operations.Multiply;
import operations.Divide;


public class Main {
		public static void main(String[] args) {
			System.out.println(Add.add(2,3));
			System.out.println(Add.add(Add.add(2,3),Multiply.multiply(3,4)));

		}
}
