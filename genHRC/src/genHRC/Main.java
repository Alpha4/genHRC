package genHRC;

import java.util.Iterator;
import java.util.List;

public class Main {


	/** Checks if the given arguments are correct
	 * @param args
	 * @return boolean
	 */
	private static boolean checkArgs(String[] args) 
	{
		boolean result = true ;
		int size = args.length;
		if (size >= 1){
			switch (args[0])
			{
			case "create" :
				if (size == 3){
					if (args[1].length() != 8 || ((args[2].length()!=8 || args[2].charAt(0)!='Z') && (args[2].length()%8)!=0))
					{
						result = false;
					}
				}
				else{
					result = false;
					if (size<3){
						System.out.println("\nError : Missing argument");
					}
					else{
						System.out.println("\nError : Too many arguments");
					}
				}
				break;
				
			case "putHash" : 
				if (size == 4){
					if (args[1].length() != 8 || ((args[2].length()!=8 || args[2].charAt(0)!='Z') && (args[2].length()%8)!=0) || args[3].length()!=256)
					{
						result = false;
					}
				}
				else{
					result = false;
					if (size<4){
						System.out.println("\nError : Missing argument");
					}
					else{
						System.out.println("\nError : Too many arguments");
					}
				}
				break;
				
			case "getHash" :
				if (size == 3){
					if (args[1].length() != 8 || ((args[2].length()!=8 || args[2].charAt(0)!='Z') && (args[2].length()%8)!=0))
					{
						result = false;
					}
				}
				else{
					result = false;
					if(size<3){
						System.out.println("\nError : Missing argument");
					}
					else{
						System.out.println("\nError : Too many arguments");
					}
				}
				break;
				
			case "getSrcHRC" :
				if (size == 2){
					if (args[1].length() != 256)
					{
						result = false;
					}
				}
				else{
					result = false;
					if (size<2)
					{
						System.out.println("\nError : Missing argument");
					}
					else{
						System.out.println("\nError : Too many arguments");
					}
				}
				break;
				
			case "deleteSrcHRC" :	
				if (size==3){
					if (args[1].length() != 8 || ((args[2].length()!=8 || args[2].charAt(0)!='Z') && (args[2].length()%8)!=0))
					{
						result = false;
					}
				}
				else{
					result = false;
					if (size<3){
						System.out.println("\nError : Missing argument");
					}
					else{
						System.out.println("\nError : Too many arguments");
					}
				}
				break;
				
			}
		}
		else{
			System.out.println("Error : no command given");
			result = false;
		}
		return result;
	}

	public static void main(String[] args) {
		if (checkArgs(args))
		{
			Connection connection = Connection.getInstance();
			SrcHRC srcHrc;
			
			switch(args[0])
			{
				case "create" :
			
					srcHrc = new SrcHRC(args[1], args[2]);
					boolean result = srcHrc.create();
					if (result)
					{
						System.out.println("Done!");
					}
					else 
					{
						System.out.println("An error occurred.");
					}
				break;
				
				case "putHash" :
				
					srcHrc = new SrcHRC(args[1], args[2]);
					if(srcHrc.getHash()=="")
					{
						srcHrc.setHash(args[3]);
						System.out.println(args[1]+" "+args[2]+" has been added to the database.");
					}
					else
					{
						System.out.println(args[1]+" "+args[2]+" already exists.");
					}
				break;
				
				case "getHash" :
				
					srcHrc = new SrcHRC(args[1], args[2]);
					String hash = srcHrc.getHash();
					if (hash!="")
					{
						System.out.println(hash);
					}
					else
					{
						System.out.println(args[1]+" "+args[2]+" doesn't exist.");
					}
					
				break;
					
				case "getSrcHRC" :
				
					connection.open();
					List<String> src = connection.getSrc(args[1]);
					List<String> hrc = connection.getHRC(args[1]);
					
					Iterator <String> i = src.iterator();
					Iterator <String> j = hrc.iterator();
					String s="";
					
					while (i.hasNext())
					{
						String src_tmp = i.next();
						String hrc_tmp = j.next();
						s+=src_tmp+" "+hrc_tmp+" ; ";
					}
					s.substring(0, s.length()-3);
					connection.close();
					if (s=="")
					{
						System.out.println("No pair of (src , HRC) corresponds to this hash value."); 
					}
					System.out.println(s);
				break;
				
				case "deleteSrcHRC" :
				
					srcHrc = new SrcHRC(args[1], args[2]);
					if (srcHrc.getHash()!="")
					{
						connection.open();
						connection.delete(args[1], args[2]);
						connection.close();
						System.out.println(args[1]+" "+args[2]+" isn’t referenced by a hash value anymore.");
					}
					else
					{
						System.out.println(args[1]+" "+args[2]+" doesn't exist.");
					}
				break;
			}
		}
		else{
			System.out.println("Error in the given arguments\n");			
		}

	}

}
