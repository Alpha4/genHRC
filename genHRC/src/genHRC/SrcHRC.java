package genHRC;

public class SrcHRC
{
	private String src;
	private String srcFileName;
	private String hrcString;
	private HRC hrc;
	
	/** SrcHRC constructor
	 * @param src
	 * @param hrcString
	 */
	public SrcHRC(String src,String hrcString)
	{
		this.src=src;
		this.hrcString=hrcString;
		getSrcFileName();
	}
	
	
	/**	 Creates the bashFile requested after creating the HRC using its real value (not an abbreviation)
	 * @return true on success false on fail
	 */
	public boolean create()
	{
		hrc=new HRC(this.getRealHRC());
		return writeBashFile();
	}
	
	
	/** Calls the writeBashFile method of hrc
	 * @return true on success false on fail
	 */
	public boolean writeBashFile()
	{
		boolean result = false;
		if (hrc.writeBashFile(srcFileName,src,hrcString))
		{
			result = true;
		}
		return result;
	}
	
	
	/** Checks if the hrcString is an abbreviation and if it's the case retrieves the corresponding value
	 * @return String realHRC
	 */
	private String getRealHRC()
	{
		if (hrcString.charAt(0) == 'Z' && hrcString.length() == 8)
		{
			return Connection.getInstance().getRealHRC(hrcString);
		}
		else
		{
			return hrcString;
		}
	}
	
	/**
	 * Retrieves the srcFileName corresponding to the src abbreviation
	 */
	private void getSrcFileName()
	{
		srcFileName=Connection.getInstance().getSrcFileName(src);
	}
	
	/**	Retrieves the hash value for the given src HRC 
	 * @return hash
	 */
	public String getHash()
	{
		return Connection.getInstance().getHash(src, getRealHRC());
	}
	
	/** Creates a new line in database for the given Src HRC and hash value
	 * @param hash
	 */
	public void setHash(String hash)
	{
		Connection.getInstance().createLine(src, getRealHRC(), hash);
	}
}
