package genHRC;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class HRC
{
	private List<Process> processList;
	
	/** HRC constructor
	 * @param hrc
	 */
	public HRC(String hrc)
	{
		processList=new ArrayList<Process>();
		List<String>splittedHRC=splitHRC(hrc);
		getProcessList(splittedHRC);
	}
	
	/** Writes the Bash file corresponding to the given srcFileName using the HRC processList
	 * @param srcFileName
	 * @param src
	 * @param hrcString
	 * @return true on success false on fail
	 */
	public boolean writeBashFile(String srcFileName, String src, String hrcString)
	{
		System.out.println("Generating Bash script …");
		File fichier = new File("./bashfile.sh") ;
		try {
			PrintWriter out = new PrintWriter(new FileWriter(fichier));
			out.write("#!/bin/bash");
			out.println();
			
			String inFile=srcFileName;
			String outFile="file1";
			String lastExt="";
			int nb=1;
			
			ListIterator<Process> i = processList.listIterator();
			Process currentProcess;
			while (i.hasNext())
			{
				currentProcess = i.next();
				
				// Comment on the current process
				out.write("#Proccess "+nb+":\n#"+currentProcess.getDescription());
				out.println();
				
				// Extracting necessary config files for the given process
				if (currentProcess.getConfigFile() != null && currentProcess.getConfigFile() != "")
				{
					//Creates the config file
					File confFile = new File("./configFile.tar.gz");
					//Writing the String retrieved from the database
					PrintWriter outConfFile = new PrintWriter(new FileWriter(confFile));
					outConfFile.write(currentProcess.getConfigFile());
					outConfFile.close();
					//Adding the line to extract the config file to the bash script
					out.write("tar -xzvf"+ confFile.toString());
				}
				//Next line
				out.println();
				
				// I/O
				out.write("$in="+inFile);
				out.println();
				out.write("$out="+outFile+currentProcess.getOutputExt());
				out.println();
				
				
				//Command using variables $in and $out as I/O 
				out.write(currentProcess.getCommand());
				out.println();
				
				// Deleting unused files
				if (inFile!=srcFileName)
				{
					out.write("rm "+inFile);
					out.println();
				}
				if (currentProcess.getConfigFile() != null && currentProcess.getConfigFile() != "")
				{
					out.write("rm configFile.tar.gz");
					out.println();
					// Extracting a file seems to overwrite it so no need to destroy the leftover decompressed config file
				}
				
				// I/O
				inFile=outFile+currentProcess.getOutputExt();
				outFile="file"+nb++;
				
				//last extension
				lastExt=currentProcess.getOutputExt();
				
			}
			
			out.write("genHRC puthash "+src+" "+hrcString+" `shasum -a 512 "+outFile+lastExt+"`");
			out.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
	}
	
	/** Verify that the processing chain is possible
	 * @return boolean
	 */
	public boolean verifyProcessingChain()
	{
		boolean result = true;
		Iterator <Process> i = processList.iterator();
		Process j=i.next();
		while(i.hasNext())
		{
			if (j.getOutputExt() != (j=i.next()).getInputExt())
			{
					result=false;
			}
		}
		
		// Try factoring to test the description and the extension at the same time
		i=processList.iterator();
		j=i.next();
		while(i.hasNext())
		{
			if (j.getOutput() != (j=i.next()).getInput())
			{
					result=false;
			}
		}
		
		return result;
	}
	
	/** Splits the hrc into 8characters strings each corresponding to a process
	 * @param hrc
	 * @return List<String> the processes for this hrc
	 */
	private List<String> splitHRC(String hrc)
	{
		List<String> list=new ArrayList<String>();
		int i=0;
		while (i<hrc.length())
		{
			list.add(hrc.substring(i,i+=8));
		}
		return list;
	}
	
	/** Getting the list of process corresponding to the strings in splittedHRC
	 * @param splittedHRC
	 */
	private void getProcessList(List<String> splittedHRC)
	{
		for (String s : splittedHRC)
		{
			processList.add(new Process(s));
		}
	}
}
