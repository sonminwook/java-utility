
public class SubClass extends SuperClass{
	static {
		 System.out.println("Static Block - SubClass");	 
	 }
	 {
		 System.out.println("Non Static Block - Subclass - 1");
	 }
	 {
		 System.out.println("Non Static Block - Subclass - 2");
	 }
	 public static String sub_static_str = "sub_static_str";
	 private String sub_instanct_str = "sub_instance_str";
	 
	 public SubClass(){
		 super();
		 System.out.println("Constructor - Superclass ");
		 System.out.println(sub_instanct_str);
		 System.out.println(sub_static_str);
	 }
	 
	 public static void main(){
		 new SubClass();
	 }
}


class SuperClass {
 static {
	 System.out.println("Static Block - SuperClass");	 
 }
 {
	 System.out.println("Non Static Block - Superclass - 1");
 }
 {
	 System.out.println("Non Static Block - Superclass - 2");
 }
 
 public static String super_static_str = "super_static_str";
 private String super_instanct_str = "super_instance_str";
 
 public SuperClass(){
	 super();
	 System.out.println("Constructor - Superclass ");
	 System.out.println(super_static_str);
	 System.out.println(super_instanct_str);
		 	 
 }
}


